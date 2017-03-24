import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {TabsComponent} from "./components/tabs.component";
import {ClassificatorsTreeComponent} from "./components/classificators-tree/classificators-tree.component";
import {ClassificatorTreeDetailedComponent} from "./components/classificators-tree/classificator-tree-detailed.component";
import {ClassificatorSearchComponent} from "./components/classificator-search/classificator-search.component";

const routes:Routes = [
    {
        path: 'search',
        component: ClassificatorSearchComponent
    },
    {
        path: 'tree',
        component: TabsComponent,
        children: [
            {path: ':type', component: ClassificatorsTreeComponent},
            {path: ':code', component: ClassificatorTreeDetailedComponent}


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

