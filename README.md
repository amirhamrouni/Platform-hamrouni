# LeerSprong NL

Open-first Dutch curriculum learning platform for children in **Groep 1–8**.

## Product vision

LeerSprong NL combines a Dutch-curriculum skill graph, adaptive practice, interactive activities, child-safe AI tutoring, parent insights, teacher tools, and offline-friendly mobile learning.

The platform is designed around four roles:

- **Child** — guided daily learning path, practice, stories, games, speaking and review.
- **Parent** — progress, strengths, weak skills, study time and recommended support.
- **Teacher** — classes, assignments, curriculum coverage and learner insights.
- **Admin** — curriculum, content sources, moderation, analytics and platform configuration.

## Curriculum scope

Primary structure: `Groep 1` through `Groep 8`.

Initial learning domains:

- Nederlands
- Rekenen & Wiskunde
- Wereldoriëntatie
- Engels
- Burgerschap
- Digitale geletterdheid
- Natuur & Techniek
- Geschiedenis
- Aardrijkskunde
- Kunst & Cultuur
- NT2 / Nederlands als tweede taal

Every internal skill can be mapped to an external curriculum reference such as SLO kerndoelen. External educational content must retain source and licence metadata; free-to-access does **not** automatically mean unrestricted reuse.

## Architecture

```text
apps/
  web/          Parent + teacher + child web platform
  mobile/       Android/iOS child-first app
packages/
  curriculum/   Groep/domain/skill graph and source mappings
  learning/     mastery, review, recommendation and session engine
  shared/       shared contracts and utilities
docs/
  PRODUCT.md
  ARCHITECTURE.md
  CONTENT_POLICY.md
```

## Core principles

1. Curriculum-first, not random quiz-first.
2. One learner model shared by web and mobile.
3. Adapt difficulty from evidence, not from one score.
4. Keep AI inside strict educational and child-safety boundaries.
5. Separate authored content, open licensed content and generated support material.
6. Store provenance and licence for every imported resource.
7. Offline learning should sync safely when connectivity returns.
8. No production mock data disguised as real learner data.

## Phase 1

- Foundation and shared contracts
- Groep 1–8 curriculum model
- Diagnostic + mastery model
- Daily adaptive learning path
- Parent/teacher/admin information architecture
- Content provenance/licensing model

## Planned open ecosystem integrations

- SLO curriculum references / open data
- Wikiwijs / open learning-material references where licensing permits
- H5P-compatible interactive activity types
- Offline-capable content packaging inspired by proven open learning platforms

## Status

**Foundation started — 2026-09-02**
