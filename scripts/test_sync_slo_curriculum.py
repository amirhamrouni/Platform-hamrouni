import json
import pathlib
import tempfile
import unittest
from unittest.mock import patch

import sync_slo_curriculum as slo


class SyncSloCurriculumTest(unittest.TestCase):
    def test_sha256_is_stable(self):
        self.assertEqual(
            slo.sha256(b"leersprong"),
            "ff95f47789cd3d1b9c4f9830898493d4fd12164224c5fd3d840f66464643ed82",
        )

    @patch.object(slo, "resolve_ref", return_value="abc123456789")
    @patch.object(slo, "fetch_bytes")
    def test_sync_writes_valid_files_and_manifest(self, fetch_bytes, _resolve_ref):
        payloads = {
            "https://raw.githubusercontent.com/slonl/curriculum-fo/abc123456789/data/kernzinnen.json": b'[{"id":"a"}]',
            "https://raw.githubusercontent.com/slonl/curriculum-fo/abc123456789/data/domeinen.json": b'[{"id":"b"}]',
        }
        fetch_bytes.side_effect = lambda url, timeout=30: payloads[url]
        with tempfile.TemporaryDirectory() as tmp:
            out = pathlib.Path(tmp)
            manifest = slo.sync(
                out,
                collections=("kernzinnen.json", "domeinen.json"),
            )
            self.assertEqual("abc123456789", manifest["commitSha"])
            self.assertEqual(2, len(manifest["files"]))
            self.assertTrue((out / "kernzinnen.json").exists())
            stored = json.loads((out / "manifest.json").read_text(encoding="utf-8"))
            self.assertEqual("abc123456789", stored["commitSha"])
            self.assertEqual(2, len(stored["files"]))

    @patch.object(slo, "resolve_ref", return_value="abc123456789")
    @patch.object(slo, "fetch_bytes", return_value=b"not-json")
    def test_sync_rejects_invalid_upstream_json(self, _fetch_bytes, _resolve_ref):
        with tempfile.TemporaryDirectory() as tmp:
            with self.assertRaises(json.JSONDecodeError):
                slo.sync(pathlib.Path(tmp), collections=("kernzinnen.json",))


if __name__ == "__main__":
    unittest.main()
