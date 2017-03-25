import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {ClsfTabsComponent} from "./components/clsf-tabs.component";
import {ClsfTreeComponent} from "./components/classificator-tree/clsf-tree.component";
import {ClsfTreeDetailedComponent} from "./components/classificator-tree/clsf-tree-detailed.component";
import {ClassificatorSearchComponent} from "./components/classificator-search/classificator-search.component";

const routes:Routes = [
    { path: '', redirectTo: '/search', pathMatch: 'full'},
    { path: 'search', component: ClassificatorSearchComponent},
    {
        path: 'tree', component: ClsfTabsComponent,
        children: [
            {path: ':type', component: ClsfTreeComponent},
            {path: ':type/:code', component: ClsfTreeDetailedComponent}
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

