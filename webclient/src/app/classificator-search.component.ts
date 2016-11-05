import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Classificator} from "./classificator";
import {OkpdService} from "./okpd.service";

@Component({
  selector: 'classificator-search',
  styleUrls: ['classificator-search.css'],
  templateUrl: 'classificator-search.html',
  providers: [OkpdService]
})
export class ClassificatorSearchComponent extends OnInit {

  classificatorTypes: Classificator[];
  query: string = "";
  searchResult: Classificator[];

  constructor(private router: Router, private okpdService: OkpdService) {
    super();
  }

  ngOnInit():void {
    this.okpdService.classificatorTypes().then(res => this.classificatorTypes = res);

  }

  toTree(type: string, code: string) {
    code = code.replace(/\./g, '');
    this.router.navigate([`/tree/${type}/${encodeURIComponent(code)}`])
  }

  search() {
    this.okpdService.getList(this.query).then(res => {
      this.searchResult = res;
    });
  }

}
