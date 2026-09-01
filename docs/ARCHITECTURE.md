# Architecture

## System shape

LeerSprong NL is a monorepo with shared curriculum and learning logic.

### Apps

- `apps/web`: responsive web platform for child, parent, teacher and admin experiences.
- `apps/mobile`: child-first mobile application with offline session support.

### Shared packages

- `packages/curriculum`: groups, domains, skills, prerequisites, SLO mappings, source/licence metadata.
- `packages/learning`: learner mastery state, evidence processing, review scheduling and daily-path ranking.
- `packages/shared`: contracts used by apps and backend services.

## Backend direction

The production backend should provide:

- authentication and family/class relationships;
- learner profiles and consent state;
- curriculum/content catalogue;
- attempt/evidence ingestion;
- mastery and recommendation state;
- assignments;
- parent/teacher analytics;
- content provenance and licence registry;
- AI tutor gateway with policy enforcement;
- notification scheduling;
- audit logs.

## Core data entities

```text
User
LearnerProfile
FamilyMembership
School
Classroom
ClassMembership
CurriculumFramework
CurriculumGoal
Skill
SkillPrerequisite
LearningResource
ResourceSource
Activity
Lesson
Assignment
Attempt
SkillEvidence
LearnerSkillState
LearningSession
DailyPath
TutorConversation
ConsentRecord
NotificationPreference
AuditEvent
```

## Learning loop

1. Resolve learner group, active goals and accessible skills.
2. Load current mastery state and review-due skills.
3. Rank a short daily path.
4. Select activities appropriate to mastery and recent mistakes.
5. Capture evidence per answer/activity, not only lesson completion.
6. Update mastery and confidence.
7. Schedule review.
8. Explain progress in child-friendly language and expose parent/teacher summaries separately.

## AI tutor boundary

The tutor may:

- explain age-appropriate educational concepts;
- rephrase an explanation using the learner's supported home language;
- provide hints and guided practice;
- create bounded practice variants from approved skill definitions;
- encourage the learner without deceptive emotional dependency.

The tutor must not:

- replace curriculum truth with unverified generated claims;
- expose private parent/teacher/admin data to a child;
- produce unrestricted web-chat experiences for minors;
- make high-stakes psychological, medical or legal judgments;
- bypass content moderation, consent or age policy.

## Offline model

Mobile downloads a signed learning pack containing skill/activity resources and an immutable version identifier. Attempts are queued locally with stable IDs and synced idempotently. Server reconciliation treats attempt events as append-only evidence.

## Design constraint

UI may change independently. Curriculum IDs, evidence contracts and learner state are stable platform contracts and must never be encoded only inside screen components.
