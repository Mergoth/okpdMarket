export interface Classificator {
  code: string;
  parentCode: string;
  name: string;
  notes?: string;
  hasChildren: boolean;
  path?:[string, string][];
  children?: Classificator[];
}

export interface ClassificatorUnited {
  okpd?: Classificator;
  okpd2?: Classificator;
  tnved?: Classificator;
}
