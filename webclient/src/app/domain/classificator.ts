export interface Classificator {
    id: string;
    name: string;
}

export interface ClassificatorItem {
    code: string;
    name: string;
    parentCode?: string;
    notes?: string;
    hasChildren?: boolean;
    path?: [string, string][];
    children?: ClassificatorItem[];
    links?: Map<string, ClassificatorItem>;
}