export interface Classificator {
  code: string;
  name: string;
  parentCode?: string;
  notes?: string;
  hasChildren?: boolean;
  path?:[string, string][];
  children?: Classificator[];
  matched?: Classificator[];
  type?: Classificator[];
}
