#!/usr/bin/env python3
"""Create a reviewable Dutch word-bank candidate snapshot from OpenTaal.

This script is intentionally NOT a spell checker and does not automatically
publish words into learner lessons. It pins the upstream repository to a commit,
filters the source list into simple single-word candidates, and writes metadata
for attribution and reproducibility. Pedagogical review is still required.
"""

from __future__ import annotations

import argparse
import hashlib
import json
import pathlib
import urllib.request
from collections import defaultdict
from datetime import datetime, timezone
from typing import Any

GITHUB_API = "https://api.github.com"
RAW_GITHUB = "https://raw.githubusercontent.com"
DEFAULT_REPO = "OpenTaal/opentaal-wordlist"
DEFAULT_REF = "master"


def fetch_bytes(url: str, timeout: int = 30) -> bytes:
    request = urllib.request.Request(
        url,
        headers={
            "Accept": "application/vnd.github+json, text/plain",
            "User-Agent": "LeerSprong-NL-wordbank-sync/1.0",
        },
    )
    with urllib.request.urlopen(request, timeout=timeout) as response:
        return response.read()


def fetch_json(url: str, timeout: int = 30) -> Any:
    return json.loads(fetch_bytes(url, timeout).decode("utf-8"))


def resolve_ref(repo: str, ref: str, timeout: int = 30) -> str:
    payload = fetch_json(f"{GITHUB_API}/repos/{repo}/commits/{ref}", timeout)
    sha = payload.get("sha")
    if not isinstance(sha, str) or len(sha) < 7:
        raise RuntimeError(f"Could not resolve {repo}@{ref} to a commit SHA")
    return sha


def sha256(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def normalize_candidates(text: str, min_length: int, max_length: int) -> list[str]:
    words: set[str] = set()
    for raw in text.splitlines():
        word = raw.strip().lower()
        if not word or not (min_length <= len(word) <= max_length):
            continue
        if not word.isalpha():
            continue
        words.add(word)
    return sorted(words)


def group_by_length(words: list[str]) -> dict[str, list[str]]:
    grouped: dict[int, list[str]] = defaultdict(list)
    for word in words:
        grouped[len(word)].append(word)
    return {str(length): grouped[length] for length in sorted(grouped)}


def sync(
    output_dir: pathlib.Path,
    repo: str = DEFAULT_REPO,
    ref: str = DEFAULT_REF,
    min_length: int = 3,
    max_length: int = 12,
    timeout: int = 30,
) -> dict[str, Any]:
    if min_length < 1 or max_length < min_length:
        raise ValueError("Invalid word-length range")

    commit_sha = resolve_ref(repo, ref, timeout)
    source_url = f"{RAW_GITHUB}/{repo}/{commit_sha}/wordlist.txt"
    raw = fetch_bytes(source_url, timeout)
    decoded = raw.decode("utf-8")
    words = normalize_candidates(decoded, min_length, max_length)

    output_dir.mkdir(parents=True, exist_ok=True)
    candidate_path = output_dir / "candidates-by-length.json"
    candidate_path.write_text(
        json.dumps(group_by_length(words), ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )

    manifest = {
        "sourceRepository": f"https://github.com/{repo}",
        "requestedRef": ref,
        "commitSha": commit_sha,
        "sourceFile": source_url,
        "sourceSha256": sha256(raw),
        "fetchedAt": datetime.now(timezone.utc).isoformat(),
        "license": "Revised BSD-3-Clause and/or CC BY 3.0; see upstream LICENSE.txt",
        "filters": {
            "minLength": min_length,
            "maxLength": max_length,
            "singleAlphabeticWordsOnly": True,
            "lowercase": True,
        },
        "candidateCount": len(words),
        "usagePolicy": [
            "Candidate bank only; do not treat this list as a spell checker.",
            "A human/pedagogical review is required before words enter child-facing lessons.",
            "Retain OpenTaal attribution and applicable license notices when redistributing data.",
        ],
    }
    (output_dir / "manifest.json").write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )
    return manifest


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--out", default="language/opentaal/raw")
    parser.add_argument("--repo", default=DEFAULT_REPO)
    parser.add_argument("--ref", default=DEFAULT_REF)
    parser.add_argument("--min-length", type=int, default=3)
    parser.add_argument("--max-length", type=int, default=12)
    parser.add_argument("--timeout", type=int, default=30)
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    manifest = sync(
        pathlib.Path(args.out),
        repo=args.repo,
        ref=args.ref,
        min_length=args.min_length,
        max_length=args.max_length,
        timeout=args.timeout,
    )
    print(
        f"OpenTaal candidate bank pinned to {manifest['commitSha']}: "
        f"{manifest['candidateCount']} candidates -> {args.out}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
