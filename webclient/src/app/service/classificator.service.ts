import {Injectable} from "@angular/core";
import {BackAPI} from "./back-api.service";
import {ClassificatorItem, Classificator} from "../domain/classificator";

@Injectable()
export class ClassificatorService {

  constructor(private backApi: BackAPI) {
  }

  classificatorTypes(): Promise<Classificator[]> {
    return this.backApi.classificatorTypes().then(response => response as Classificator[]);
    ;
  }

  getList(query: string, type: string): Promise<ClassificatorItem[]> {
    return this.backApi.classificatorsBy(query, type).then(response => response as ClassificatorItem[]);
  }

  classificatorTree(classificator: string, code: string, params: Object): Promise<ClassificatorItem> {
    return code ?
        this.backApi.classificatorItem(classificator, prepareCode(code), params)
        :
        (this.backApi.classificatorTopItems(classificator).then(response => {
          const items = response as ClassificatorItem[];
          items.forEach(item => item.parentCode = classificator);
          return {code: classificator, hasChildren: true, children: items, path: []};
        }));
  }

}

function prepareCode(code: string) {
  return code ? code.replace(/\./g, '') : null;
}
