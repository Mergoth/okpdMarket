import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ClassificatorService} from "../service/classificator.service";
import {Classificator} from "../domain/classificator";
import {TabsModel} from "../tab-model";

@Component({
    selector: 'clsfs-tabs',
    template: `
 <nav md-tab-nav-bar>
    <a md-tab-link
       *ngFor="let tab of tabs; let i = index"
      [active]="activeLinkIndex === i"
       (click)="activeLinkIndex = i"
       [routerLink]="tab.type"
       >
      {{tab.title}}
    </a>
  </nav>
  <router-outlet></router-outlet>
`,
    providers: [ClassificatorService]
})
export class TabsComponent implements OnInit {

    tabs:TabModel[] = [];

    classificatorTypes:Classificator[];

    private routeParams:RouteParams;

    activeLinkIndex = 0;

    constructor(private route:ActivatedRoute, private router:Router, private classificatorService:ClassificatorService) {
        route.params.subscribe(params => {
            console.log('ROUTE change:', params);
            console.log('this:', this);
            this.routeParams = params as RouteParams;
        });
    }

    ngOnInit() {
        console.debug('<<<ngInit', this.routeParams.type);
        this.classificatorService.classificatorTypes().then(res => {
            this.classificatorTypes = res;
            this.tabs = [];
            this.classificatorTypes.forEach(clsfType => this.tabs.push({
                type: clsfType.code,
                title: clsfType.name,
                selected: false
            }));
        }).then(_ => {
            const defaultType = this.classificatorTypes[0].code;
            console.log('navigate:', `/tree/${defaultType}`);
            this.router.navigate(['tree', defaultType]);
        });
    }

    onSelectChange(index) {
        console.debug('tab switched:', index);
        this.selectedIndex = index;
        //this.router.navigate(['tree/', this.tabModel.selectedType]);
    }

    disableAllExcept(type:string) {
        this.tabs.forEach(t => t.disabled = t.type != type);
    }

    enableAll() {
        this.tabs.forEach(t => t.disabled = false);
    }

    get selectedIndex():number {
        return this.tabs.findIndex(t => t.selected);
    }

    set selectedIndex(index:number) {
        this.tabs.forEach(t => t.selected = false);
        this.tabs[index].selected = true;
    }

    get selectedType() {
        let selectedTab = this.tabs.find(t => t.selected);
        return selectedTab ? selectedTab.type : null;
    }


}

export class TabModel {
    type:string;
    title:string;
    selected:boolean;
    disabled?:boolean;
}

class RouteParams {
    type:string;
    code:string;
}