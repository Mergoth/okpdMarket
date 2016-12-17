import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {ClassificatorItem, Classificator} from "../../domain/classificator";
import {ClassificatorService} from "../../service/classificator.service";
import {TabsModel} from "../../tab-model";

@Component({
  selector: 'classificator-search',
  styleUrls: ['./classificator-search.css'],
  templateUrl: './classificator-search.html',
  providers: [ClassificatorService]
})
export class ClassificatorSearchComponent extends OnInit {

  tabModel: TabsModel = new TabsModel();

  classificatorTypes: Classificator[];

  query: string = "";

    searchResult: ClassificatorItem[];

  searching:boolean = false;

  constructor(private router: Router, private classificatorService: ClassificatorService) {
    super();
  }

  ngOnInit():void {
    this.classificatorService.classificatorTypes().then(res => {
      this.classificatorTypes = res;
      this.tabModel.clear();
      this.classificatorTypes.forEach(clsfType => this.tabModel.push({
          type: clsfType.id,
        title: clsfType.name,
        selected: false
      }));
    }).then(_ => {
        this.tabModel.selectedType = this.classificatorTypes[0].id;
    });
  }

  highlight(text: string) {
    let res = text.replace(new RegExp(this.query, 'gi'), '<b>$&</b>');
    return res;
  }

  onSelectChange(tab: any) {
    // console.log('>>>>>>', tab);
    this.tabModel.selectedIndex = tab.index;
  }

  toTree(type: string, code: string) {
    code = code.replace(/\./g, '');
    this.router.navigate([`/tree/${type}/${encodeURIComponent(code)}`])
  }

  search() {
    this.searching = true;
    this.classificatorService.getList(this.query, this.tabModel.selectedType).then(res => {
      this.searchResult = res;
    }).then(_ => this.searching = false);
  }

}
