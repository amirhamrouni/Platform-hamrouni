# Language data sources

LeerSprong NL keeps language datasets separate from child-facing lesson content. A source word list is a candidate pool, not a pedagogical sequence and not automatically a spell checker.

## OpenTaal Dutch word list

- Repository: https://github.com/OpenTaal/opentaal-wordlist
- Creator: Stichting OpenTaal
- Upstream rights: Revised BSD License and/or CC BY 3.0 (see upstream `LICENSE.txt`)
- Upstream README: the list contains more than 400,000 Dutch words and should not be treated as a self-made spelling checker.

LeerSprong uses the BSD-licensed path for generated candidate-bank tooling and retains attribution. The app does not currently bundle the complete OpenTaal list.

## Candidate sync

```bash
python3 scripts/sync_opentaal_wordbank.py --out language/opentaal/raw
```

The script resolves the upstream branch to a commit SHA, downloads `wordlist.txt`, stores its SHA-256, filters to lowercase single alphabetic words in a configurable length range, groups candidates by length, and writes a reproducibility/attribution manifest.

Default filters are deliberately conservative: 3–12 letters, alphabetic single words only. This removes spaces, punctuation and numeric entries but is **not** enough to make a word child-appropriate.

## Publishing policy

Before a candidate enters a learner-facing lesson it must be reviewed for:

- age/group appropriateness;
- contemporary and non-offensive usage;
- pedagogical purpose (spelling pattern, vocabulary, reading, NT2, etc.);
- ambiguity and context;
- licensing/attribution requirements for any added definition, sentence, audio or image.

Definitions and example sentences must not be copied from a dictionary merely because the word itself appears in OpenTaal. LeerSprong-authored definitions or separately licensed sources are required.
