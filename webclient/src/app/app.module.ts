import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule, JsonpModule} from "@angular/http";
import {MaterialModule} from "@angular/material";
import {AppComponent} from "./app.component";
import {ClassificatorSearchComponent} from "./components/classificator-search/classificator-search.component";
import {ClsfTreeComponent} from "./components/classificator-tree/clsf-tree.component";
import {ClassificatorService} from "./service/classificator.service";
import {BackAPI} from "./service/back-api.service";
import {AppRoutingModule} from "./app-routing.module";
import {ClsfTreeViewComponent} from "./components/classificator-tree/clsf-tree-veiw.component";
import {ClsfTreeViewNodeComponent} from "./components/classificator-tree/clsf-tree-view-node.component";
import {MapIterable} from "./components/map-iterable.pipe";
import {EventService} from "./service/event.service";
import {ClsfTabsComponent} from "./components/clsf-tabs.component";
import {ClsfTreeDetailedComponent} from "./components/classificator-tree/clsf-tree-detailed.component";


// Imports for loading & configuring the in-memory web api

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    //InMemoryWebApiModule.forRoot(MockDatabaseService, {
    //  delay: 100,  rootPath: 'api/'
    //}),
    AppRoutingModule,
    MaterialModule.forRoot()
  ],
  declarations: [
    AppComponent,
    ClsfTabsComponent,
    ClassificatorSearchComponent,
    ClsfTreeComponent,
    ClsfTreeDetailedComponent,
    ClsfTreeViewComponent,
    ClsfTreeViewNodeComponent,
    MapIterable
  ],
  bootstrap: [AppComponent],
  providers: [
    BackAPI,
    ClassificatorService,
    EventService
  ]
})
export class AppModule {
}
