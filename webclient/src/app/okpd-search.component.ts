import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {ClassificatorUnited} from "./classificator";
import {OkpdService} from "./okpd.service";

@Component({
  selector: 'okpd-search',
  styleUrls: ['okpd-search.css'],
  templateUrl: 'okpd-search.html',
  providers: [OkpdService]
})
export class OkpdSearchComponent {

  model: {
    query: string,
    result: ClassificatorUnited[]
  } = {
    query: "",
    result: null
  };

  constructor(private router: Router, private okpdService: OkpdService) {
  }

  toOkpd(code: string) {
    code = code.replace(/\./g, '');
    this.router.navigate([`/tree/okpd/${encodeURIComponent(code)}`])
  }

  search() {
    this.okpdService.getList(this.model.query).then(res => {
      this.model.result = res
    });
  }

}
