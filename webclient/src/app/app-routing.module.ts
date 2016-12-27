import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {ClassificatorsTreeComponent} from "./components/classificators-tree/classificators-tree.component";
import {ClassificatorSearchComponent} from "./components/classificator-search/classificator-search.component";

const routes: Routes = [
  {
    path: 'search',
    component: ClassificatorSearchComponent
  },
  {
    path: 'tree',
    children: [
      {
        path: '',
        component: ClassificatorsTreeComponent
      },
      {
        path: ':type',
        children: [
          {
            path: '',
            component: ClassificatorsTreeComponent
          },
          {
            path: ':code',
            component: ClassificatorsTreeComponent
          }
        ]
      }
    ]
  },
  {path: '', redirectTo: '/search', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

