import {Injectable} from "@angular/core";
import {BackAPI} from "./back-api.service";
import {Classificator} from "./classificator";

@Injectable()
export class OkpdService {

  constructor(private backApi: BackAPI) {
  }

  classificatorTypes(): Promise<Classificator[]> {
    return this.backApi.classificatorTypes().then(response => response as Classificator[]);;
  }

  getList(query: string): Promise<Classificator[]> {
    return this.backApi.okpdBy(query).then(response => response as Classificator[]);
  }

  classificatorTree(classificator: string, code: string, params: Object): Promise<Classificator> {
    return this.backApi.classificatorTree(classificator, prepareCode(code), params).then(response => response as Classificator);
  }

}

function prepareCode(code: string) {
  return code ? code.replace(/\./g, '') : null;
}
