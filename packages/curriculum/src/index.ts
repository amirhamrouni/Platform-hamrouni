export type GroupLevel = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8;

export type CurriculumDomain =
  | 'nederlands'
  | 'rekenen-wiskunde'
  | 'wereldorientatie'
  | 'engels'
  | 'burgerschap'
  | 'digitale-geletterdheid'
  | 'natuur-techniek'
  | 'geschiedenis'
  | 'aardrijkskunde'
  | 'kunst-cultuur'
  | 'nt2';

export type LicenceKind =
  | 'original'
  | 'public-domain'
  | 'cc0'
  | 'cc-by'
  | 'cc-by-sa'
  | 'external-link-only'
  | 'unknown';

export interface SourceReference {
  provider: string;
  title: string;
  url: string;
  licence: LicenceKind;
  licenceUrl?: string;
  attribution?: string;
  importedAt?: string;
}

export interface CurriculumReference {
  framework: 'SLO' | 'internal';
  externalId?: string;
  title: string;
  version?: string;
  url?: string;
  verification: 'verified-source' | 'verified-goal' | 'internal';
}

export interface SkillDefinition {
  id: string;
  group: GroupLevel;
  domain: CurriculumDomain;
  strand: string;
  titleNl: string;
  descriptionNl: string;
  prerequisites: string[];
  curriculumRefs: CurriculumReference[];
  contentSources: SourceReference[];
  tags: string[];
}

export const GROUPS: GroupLevel[] = [1, 2, 3, 4, 5, 6, 7, 8];

export const DOMAINS: { id: CurriculumDomain; titleNl: string }[] = [
  { id: 'nederlands', titleNl: 'Nederlands' },
  { id: 'rekenen-wiskunde', titleNl: 'Rekenen & Wiskunde' },
  { id: 'wereldorientatie', titleNl: 'Wereldoriëntatie' },
  { id: 'engels', titleNl: 'Engels' },
  { id: 'burgerschap', titleNl: 'Burgerschap' },
  { id: 'digitale-geletterdheid', titleNl: 'Digitale geletterdheid' },
  { id: 'natuur-techniek', titleNl: 'Natuur & Techniek' },
  { id: 'geschiedenis', titleNl: 'Geschiedenis' },
  { id: 'aardrijkskunde', titleNl: 'Aardrijkskunde' },
  { id: 'kunst-cultuur', titleNl: 'Kunst & Cultuur' },
  { id: 'nt2', titleNl: 'NT2' },
];

const SLO_NEDERLANDS_2025: CurriculumReference = {
  framework: 'SLO',
  title: 'Kerndoelen Nederlands – primair onderwijs',
  version: '2025 (in werking sinds augustus 2026)',
  url: 'https://www.slo.nl/thema/meer/actualisatie-kerndoelen-examenprogramma/actualisatie-kerndoelen/definitieve-conceptkerndoelen-nederlands/',
  verification: 'verified-source',
};

const SLO_REKENEN_2025: CurriculumReference = {
  framework: 'SLO',
  title: 'Kerndoelen rekenen en wiskunde – primair onderwijs',
  version: '2025 (in werking sinds augustus 2026)',
  url: 'https://www.slo.nl/thema/meer/actualisatie-kerndoelen-examenprogramma/actualisatie-kerndoelen/definitieve-conceptkerndoelen-rekenen/',
  verification: 'verified-source',
};

function internalRef(title: string): CurriculumReference {
  return { framework: 'internal', title, verification: 'internal' };
}

export const CORE_SKILLS: SkillDefinition[] = [
  {
    id: 'g1-nl-listening-vocabulary', group: 1, domain: 'nederlands', strand: 'Mondelinge taal',
    titleNl: 'Luisteren en woorden begrijpen', descriptionNl: 'Begrijpt veelgebruikte woorden en korte mondelinge instructies.',
    prerequisites: [], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 1 – mondelinge taal')], contentSources: [], tags: ['luisteren', 'woordenschat'],
  },
  {
    id: 'g1-math-counting-20', group: 1, domain: 'rekenen-wiskunde', strand: 'Getallen',
    titleNl: 'Tellen en hoeveelheden tot 20', descriptionNl: 'Telt objecten en koppelt kleine hoeveelheden aan getallen.',
    prerequisites: [], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 1 – getalbegrip')], contentSources: [], tags: ['tellen', 'hoeveelheden'],
  },
  {
    id: 'g2-nl-rhyme-sounds', group: 2, domain: 'nederlands', strand: 'Beginnende geletterdheid',
    titleNl: 'Rijm en klanken herkennen', descriptionNl: 'Herkent rijm, begin- en eindklanken in eenvoudige woorden.',
    prerequisites: ['g1-nl-listening-vocabulary'], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 2 – fonologisch bewustzijn')], contentSources: [], tags: ['rijm', 'klanken'],
  },
  {
    id: 'g2-math-number-order-20', group: 2, domain: 'rekenen-wiskunde', strand: 'Getallen',
    titleNl: 'Getallen ordenen tot 20', descriptionNl: 'Vergelijkt en ordent getallen en hoeveelheden tot 20.',
    prerequisites: ['g1-math-counting-20'], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 2 – getalrelaties')], contentSources: [], tags: ['ordenen', 'meer-minder'],
  },
  {
    id: 'g3-nl-beginning-reading-phonemes', group: 3, domain: 'nederlands', strand: 'Lezen',
    titleNl: 'Klanken verbinden aan letters', descriptionNl: 'Koppelt klanken aan letters en leest eenvoudige woorden.',
    prerequisites: ['g2-nl-rhyme-sounds'], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 3 – aanvankelijk lezen')], contentSources: [], tags: ['lezen', 'fonemen', 'letters'],
  },
  {
    id: 'g3-math-add-sub-20', group: 3, domain: 'rekenen-wiskunde', strand: 'Bewerkingen',
    titleNl: 'Optellen en aftrekken tot 20', descriptionNl: 'Lost eenvoudige optel- en aftreksituaties tot 20 op.',
    prerequisites: ['g2-math-number-order-20'], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 3 – optellen en aftrekken')], contentSources: [], tags: ['optellen', 'aftrekken'],
  },
  {
    id: 'g4-nl-fluent-reading', group: 4, domain: 'nederlands', strand: 'Lezen',
    titleNl: 'Vloeiender lezen', descriptionNl: 'Leest korte passende teksten met toenemende nauwkeurigheid en tempo.',
    prerequisites: ['g3-nl-beginning-reading-phonemes'], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 4 – technisch lezen')], contentSources: [], tags: ['technisch-lezen', 'vloeiend'],
  },
  {
    id: 'g4-math-multiplication-foundations', group: 4, domain: 'rekenen-wiskunde', strand: 'Bewerkingen',
    titleNl: 'Vermenigvuldigen begrijpen', descriptionNl: 'Begrijpt herhaald optellen en eenvoudige tafels in concrete situaties.',
    prerequisites: ['g3-math-add-sub-20'], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 4 – vermenigvuldigen')], contentSources: [], tags: ['tafels', 'vermenigvuldigen'],
  },
  {
    id: 'g5-nl-reading-comprehension-main-idea', group: 5, domain: 'nederlands', strand: 'Begrijpend lezen',
    titleNl: 'Hoofdgedachte herkennen', descriptionNl: 'Bepaalt waar een korte informatieve tekst vooral over gaat.',
    prerequisites: ['g4-nl-fluent-reading'], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 5 – tekstbegrip')], contentSources: [], tags: ['begrijpend-lezen', 'hoofdgedachte'],
  },
  {
    id: 'g5-math-division-relations', group: 5, domain: 'rekenen-wiskunde', strand: 'Bewerkingen',
    titleNl: 'Delen en tafelrelaties', descriptionNl: 'Gebruikt tafelkennis om eenvoudige deelsommen en verhoudingen op te lossen.',
    prerequisites: ['g4-math-multiplication-foundations'], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 5 – delen')], contentSources: [], tags: ['delen', 'tafels'],
  },
  {
    id: 'g6-nl-text-structure', group: 6, domain: 'nederlands', strand: 'Begrijpend lezen',
    titleNl: 'Tekststructuur en verbanden', descriptionNl: 'Herkent eenvoudige tekstverbanden, alinea-opbouw en signaalwoorden.',
    prerequisites: ['g5-nl-reading-comprehension-main-idea'], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 6 – tekststructuur')], contentSources: [], tags: ['tekstverbanden', 'signaalwoorden'],
  },
  {
    id: 'g6-math-fractions-basics', group: 6, domain: 'rekenen-wiskunde', strand: 'Getallen',
    titleNl: 'Breuken begrijpen', descriptionNl: 'Vergelijkt eenvoudige breuken en koppelt breuken aan delen van een geheel.',
    prerequisites: ['g5-math-division-relations'], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 6 – breuken')], contentSources: [], tags: ['breuken', 'vergelijken'],
  },
  {
    id: 'g7-nl-inference-source', group: 7, domain: 'nederlands', strand: 'Begrijpend lezen',
    titleNl: 'Afleiden en informatie beoordelen', descriptionNl: 'Trekt conclusies uit tekstinformatie en vergelijkt relevante bronnen.',
    prerequisites: ['g6-nl-text-structure'], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 7 – inferentie en bronnen')], contentSources: [], tags: ['inferentie', 'bronnen'],
  },
  {
    id: 'g7-math-percentages', group: 7, domain: 'rekenen-wiskunde', strand: 'Verhoudingen',
    titleNl: 'Procenten en verhoudingen', descriptionNl: 'Verbindt eenvoudige procenten met breuken, verhoudingen en praktische situaties.',
    prerequisites: ['g6-math-fractions-basics'], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 7 – procenten')], contentSources: [], tags: ['procenten', 'verhoudingen'],
  },
  {
    id: 'g8-nl-argumentation', group: 8, domain: 'nederlands', strand: 'Communicatie',
    titleNl: 'Argumenten begrijpen en formuleren', descriptionNl: 'Herkent standpunten en argumenten en formuleert een onderbouwde reactie.',
    prerequisites: ['g7-nl-inference-source'], curriculumRefs: [SLO_NEDERLANDS_2025, internalRef('Groep 8 – argumentatie')], contentSources: [], tags: ['argumentatie', 'standpunt'],
  },
  {
    id: 'g8-math-multi-step-problems', group: 8, domain: 'rekenen-wiskunde', strand: 'Probleemoplossen',
    titleNl: 'Meer-staps rekenproblemen', descriptionNl: 'Kiest passende bewerkingen en controleert een oplossing in meer-staps situaties.',
    prerequisites: ['g7-math-percentages'], curriculumRefs: [SLO_REKENEN_2025, internalRef('Groep 8 – probleemoplossen')], contentSources: [], tags: ['probleemoplossen', 'meer-staps'],
  },
];

export const FOUNDATION_SKILLS = CORE_SKILLS;

export function skillsForGroup(group: GroupLevel) {
  return CORE_SKILLS.filter((skill) => skill.group === group);
}

export function skillById(id: string) {
  return CORE_SKILLS.find((skill) => skill.id === id);
}
