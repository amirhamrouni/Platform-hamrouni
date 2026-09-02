# Curriculum sources and sync workflow

LeerSprong NL separates **curriculum references** from **learning content**.

## Official curriculum source

Primary source: SLO Open Data / SLO's official GitHub organization.

- Portal: https://opendata.slo.nl/
- Current foundational-curriculum dataset: https://github.com/slonl/curriculum-fo
- Core UUID/data model: https://github.com/slonl/curriculum-basis
- SLO JavaScript tooling: https://github.com/slonl/curriculum-js

SLO Open Data contains curriculum goals and related curriculum structures. It does **not** provide ready-made lessons or assessments. LeerSprong lesson content must therefore be authored or sourced separately under compatible licenses.

## Sync command

```bash
python3 scripts/sync_slo_curriculum.py --out curriculum/slo/raw
```

The script:

1. Resolves `slonl/curriculum-fo@main` to an immutable commit SHA.
2. Downloads only the default mapping collections (`kernzinnen`, `doelzinnen`, `domeinen`, `subdomeinen`, `sets`).
3. Validates that every downloaded source is valid JSON.
4. Writes a manifest with the upstream commit, source URLs, byte sizes and SHA-256 hashes.
5. Never creates or rewrites SLO UUIDs.

Large collections such as `deprecated.json` and `illustraties.json` are excluded by default. Pass explicit names with `--collections` when they are genuinely needed.

## Repository policy

Raw SLO snapshots are generated working data and should not be committed automatically. Before redistributing a snapshot, confirm the current SLO/data-overheid attribution and licensing terms. Application content must reference only verified released identifiers; unreleased or dirty editor-branch identifiers must not be shipped.

## Lesson mapping policy

A future curriculum mapping record should carry at minimum:

- LeerSprong `skillId`
- SLO released UUID/URL
- source dataset commit SHA
- subject/domain
- mapping confidence/review status
- reviewer/date

Do not infer a SLO UUID from a title. If no verified identifier exists, keep the lesson unmapped rather than inventing one.
