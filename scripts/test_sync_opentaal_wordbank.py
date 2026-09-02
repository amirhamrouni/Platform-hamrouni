import pathlib
import tempfile
import unittest
from unittest.mock import patch

import sync_opentaal_wordbank as wordbank


class OpenTaalWordBankTest(unittest.TestCase):
    def test_normalize_filters_to_simple_words_and_length(self):
        source = "fiets\nFiets\nik\nheel-lang\ntwee woorden\nBLOEM\n123\nwereld\n"
        self.assertEqual(
            ["bloem", "fiets", "wereld"],
            wordbank.normalize_candidates(source, 4, 8),
        )

    def test_group_by_length_is_deterministic(self):
        grouped = wordbank.group_by_length(["boom", "fiets", "maan"])
        self.assertEqual({"4": ["boom", "maan"], "5": ["fiets"]}, grouped)

    @patch.object(wordbank, "resolve_ref", return_value="abc123456789")
    @patch.object(wordbank, "fetch_bytes", return_value=b"fiets\nbloem\nwereld\n")
    def test_sync_writes_reviewable_manifest(self, _fetch, _resolve):
        with tempfile.TemporaryDirectory() as tmp:
            manifest = wordbank.sync(pathlib.Path(tmp), min_length=4, max_length=8)
            self.assertEqual("abc123456789", manifest["commitSha"])
            self.assertEqual(3, manifest["candidateCount"])
            self.assertTrue((pathlib.Path(tmp) / "candidates-by-length.json").exists())
            self.assertTrue((pathlib.Path(tmp) / "manifest.json").exists())

    def test_invalid_length_range_is_rejected(self):
        with tempfile.TemporaryDirectory() as tmp:
            with self.assertRaises(ValueError):
                wordbank.sync(pathlib.Path(tmp), min_length=8, max_length=4)


if __name__ == "__main__":
    unittest.main()
