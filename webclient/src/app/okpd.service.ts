import {Injectable} from "@angular/core";
import {BackAPI} from "./back-api.service";
import {ClassificatorUnited, Classificator} from "./classificator";

@Injectable()
export class OkpdService {

  constructor(private backApi: BackAPI) {
  }

  getList(query: string): Promise<ClassificatorUnited[]> {
    return this.backApi.okpdBy(query).then(response => response as ClassificatorUnited[]);
  }

  classificatorTree(classificator: string, code: string, params: Object): Promise<Classificator> {
    return this.backApi.classificatorTree(classificator, prepareCode(code), params).then(response => response as Classificator);
  }

}

function prepareCode(code: string) {
  return code ? code.replace(/\./g, '') : null;
}
