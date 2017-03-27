import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ClassificatorItem, Classificator} from "../../domain/classificator";
import {ClassificatorService} from "../../service/classificator.service";

@Component({
  selector: 'clsf-search',
  styleUrls: ['./clsf-search.css'],
  templateUrl: './clsf-search.html',
  providers: [ClassificatorService]
})
export class ClsfSearchComponent implements OnInit {

  clsf: Classificator;

  query: string = "";

  searchResult: ClassificatorItem[];

  searching:boolean = false;

  constructor(private route: ActivatedRoute, private classificatorService: ClassificatorService) { }

  ngOnInit():void {
    this.route.params.subscribe(params => {
      let clsfTypeCode = params['type'];
      this.classificatorService.classificatorType(clsfTypeCode).then(clsf => this.clsf = clsf);
    });
  }

  highlight(text: string) {
    return text.replace(new RegExp(this.query, 'gi'), '<b>$&</b>');
  }

  search() {
    if(!this.query) {
      return
    }
    this.searchResult = null;
    this.searching = true;
    this.classificatorService.getList(this.query, this.clsf.code).then(res => {
      this.searchResult = res;
    }).then(_ => this.searching = false);
  }

}
