import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ClassificatorService} from "../service/classificator.service";
import {Classificator} from "../domain/classificator";
import {Subscription} from "rxjs/Subscription";

@Component({
    selector: 'clsfs-tabs',
    template: `
 <nav md-tab-nav-bar>
    <a md-tab-link
       *ngFor="let tab of tabs; let i = index" 
       [active]="activeLinkIndex === i"
       (click)="activeLinkIndex = i"
       [routerLink]="tab.link"
       >
      {{tab.title}}
    </a>
  </nav>
  <router-outlet></router-outlet>
`,
    providers: [ClassificatorService]
})
export class ClsfTabsComponent implements OnInit, OnDestroy {


    tabs:Tab[] = [];

    classificatorTypes:Classificator[];

    activeLinkIndex = 0;

    activeLink;

    childSubscriptions:Subscription[];

    constructor(private route:ActivatedRoute,
                private router:Router,
                private classificatorService:ClassificatorService) {
        console.log('Tabs component!!', this.route);
        console.log('router', this.router);


        this.childSubscriptions = [];
        this.route.children.forEach(childRoute => {
            this.childSubscriptions.push(
                childRoute.params.subscribe(params => {
                    console.log('TAbs: child route change ::', params);
                    if(params['type']) {
                        this.selectTab(params['type']);
                    }
                })
            );
        });

        console.log('this.route.children:', this.route.children);
        console.log('this.childSubscriptions:', this.childSubscriptions);

    }

    selectTab(link: string) {
        console.log('selectTab:', link, this.tabs);
        this.activeLinkIndex =  this.tabs.findIndex(tab => tab.link == link);
        this.activeLink = link;
    }

    ngOnInit() {
        this.classificatorService.classificatorTypes().then(res => {
            this.classificatorTypes = res;
            this.tabs = [];
            this.classificatorTypes.forEach(clsfType => this.tabs.push({
                link: clsfType.code,
                title: clsfType.name
            }));
        }).then(_ => {
            const defaultType = this.classificatorTypes[0].code;
            console.log('defaultType navigate:', `../${defaultType}`);
            if(!this.activeLink) {
                this.router.navigate(['./', defaultType], { relativeTo: this.route });
            } else {
                this.activeLinkIndex =  this.tabs.findIndex(tab => tab.link == this.activeLink);
            }
        });
    }

    ngOnDestroy() {
        this.childSubscriptions.forEach(subscribe => subscribe.unsubscribe());
    }

}

interface Tab {
    link:string;
    title:string;
}