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
    type?: string;
    path?: [string, string][];
    children?: ClassificatorItem[];
    matched?: ClassificatorItem[];
}