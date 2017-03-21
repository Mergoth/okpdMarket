import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule, JsonpModule} from "@angular/http";
import {MaterialModule} from "@angular/material";
import {AppComponent} from "./app.component";
import {ClassificatorSearchComponent} from "./components/classificator-search/classificator-search.component";
import {ClassificatorsTreeComponent} from "./components/classificators-tree/classificators-tree.component";
import {ClassificatorTreeComponent} from "./components/classificators-tree/classificator-tree/classificator-tree.component";
import {ClassificatorService} from "./service/classificator.service";
import {BackAPI} from "./service/back-api.service";
import {AppRoutingModule} from "./app-routing.module";
import {TreeViewComponent} from "./components/classificators-tree/classificator-tree/tree-veiw.component";
import {TreeNodeComponent} from "./components/classificators-tree/classificator-tree/tree-node.component";
import {MapIterable} from "./components/map-iterable.pipe";
import {EventService} from "./service/event.service";


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
    ClassificatorSearchComponent,
    ClassificatorsTreeComponent,
    ClassificatorTreeComponent,
    TreeViewComponent,
    TreeNodeComponent,
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
