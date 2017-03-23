import {Component} from "@angular/core";

@Component({
  selector: 'app',
  template: `<h1></h1>
   <nav>
     <a routerLink="/tree">Дерево</a>
     <a routerLink="/search">Полнотекстовый поиск</a>
   </nav>
   <md-card>
       <router-outlet></router-outlet>
   </md-card>
`,
})
export class AppComponent {
}
