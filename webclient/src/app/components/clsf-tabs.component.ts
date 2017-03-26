import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router, Event, NavigationEnd} from "@angular/router";
import {ClassificatorService} from "../service/classificator.service";
import {Classificator} from "../domain/classificator";

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
export class ClsfTabsComponent implements OnInit {


    tabs: Tab[] = [];

    classificatorTypes: Classificator[];

    activeLinkIndex = 0;

    activeLink;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private classificatorService: ClassificatorService) {
    }

    selectTab(link: string) {
        console.log('selectTab:', link, this.tabs);
        this.activeLinkIndex = this.tabs.findIndex(tab => tab.link == link);
        this.activeLink = link;
    }

    ngOnInit() {
        //todo: супер мега хак и-за неправильной работы роутера Ангуляра. При изменении роута переключает таб.
        this.router.events.subscribe((event: Event) => {
            if (event instanceof NavigationEnd) {
                const urlPath = event.url.split("/");
                let routeMap = {};
                (this.router.config as Object[]).forEach((route) => {
                    const componentName = route['component'] ? route['component']['name'] : '';
                    routeMap[componentName] = route['path'];
                });
                let currentPathName = routeMap['ClsfTabsComponent'];
                const currentUrlIndex = urlPath.indexOf(currentPathName);
                if (currentUrlIndex < urlPath.length) {
                    const childPath = urlPath[currentUrlIndex + 1];
                    this.selectTab(childPath);
                }
            }
        });

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
                this.activeLinkIndex = this.tabs.findIndex(tab => tab.link == this.activeLink);
            }
        });
    }


}

interface Tab {
    link:string;
    title:string;
}