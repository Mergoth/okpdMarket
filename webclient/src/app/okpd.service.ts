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

  okpdTreeBy(rootCode: string): Promise<Classificator> {
    return this.backApi.okpdTree(prepareCode(rootCode)).then(response => response as Classificator);
  }

  okpd2TreeBy(rootCode: string): Promise<Classificator> {
    return this.backApi.okpd2Tree(prepareCode(rootCode)).then(response => response as Classificator);
  }

  tnvedTreeBy(rootCode: string): Promise<Classificator> {
    return this.backApi.tnvedTree(prepareCode(rootCode)).then(response => response as Classificator);
  }

}

function prepareCode(code: string) {
  return code ? code.replace(/\./g, '')  : null;
}
