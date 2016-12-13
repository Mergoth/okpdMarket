import {Injectable} from "@angular/core";
import {BackAPI} from "./back-api.service";
import {ClassificatorItem, Classificator} from "../domain/classificator";

@Injectable()
export class ClassificatorService {

  constructor(private backApi: BackAPI) {
  }

  classificatorTypes(): Promise<Classificator[]> {
    return this.backApi.classificatorTypes().then(response => response as Classificator[]);;
  }

  getList(query: string, type: string): Promise<ClassificatorItem[]> {
    return this.backApi.classificatorsBy(query, type).then(response => response as ClassificatorItem[]);
  }

  classificatorTree(classificator: string, code: string, params: Object): Promise<ClassificatorItem> {
    return this.backApi.classificatorTree(classificator, prepareCode(code), params).then(response => response as ClassificatorItem);
  }

}

function prepareCode(code: string) {
  return code ? code.replace(/\./g, '') : null;
}
