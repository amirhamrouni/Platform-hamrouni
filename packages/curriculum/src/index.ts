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
  url?: string;
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
  { id: 'nt2', titleNl: 'NT2' }
];

export const FOUNDATION_SKILLS: SkillDefinition[] = [
  {
    id: 'g3-nl-beginning-reading-phonemes',
    group: 3,
    domain: 'nederlands',
    strand: 'Beginnende geletterdheid',
    titleNl: 'Klanken herkennen en verbinden',
    descriptionNl: 'Herkent klanken en koppelt deze aan letters en eenvoudige woorden.',
    prerequisites: [],
    curriculumRefs: [{ framework: 'internal', title: 'Nederlands – beginnende geletterdheid' }],
    contentSources: [],
    tags: ['lezen', 'fonemen', 'letters']
  },
  {
    id: 'g4-math-multiplication-foundations',
    group: 4,
    domain: 'rekenen-wiskunde',
    strand: 'Getallen en bewerkingen',
    titleNl: 'Vermenigvuldigen begrijpen',
    descriptionNl: 'Begrijpt herhaald optellen en eenvoudige tafels in concrete situaties.',
    prerequisites: [],
    curriculumRefs: [{ framework: 'internal', title: 'Rekenen – vermenigvuldigen' }],
    contentSources: [],
    tags: ['tafels', 'vermenigvuldigen']
  },
  {
    id: 'g5-nl-reading-comprehension-main-idea',
    group: 5,
    domain: 'nederlands',
    strand: 'Begrijpend lezen',
    titleNl: 'Hoofdgedachte herkennen',
    descriptionNl: 'Bepaalt waar een korte informatieve tekst vooral over gaat.',
    prerequisites: [],
    curriculumRefs: [{ framework: 'internal', title: 'Nederlands – begrijpend lezen' }],
    contentSources: [],
    tags: ['begrijpend-lezen', 'hoofdgedachte']
  }
];
