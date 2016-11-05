import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule, JsonpModule} from "@angular/http";
import {MaterialModule} from "@angular/material";
import {AppComponent} from "./app.component";
import {ClassificatorSearchComponent} from "./classificator-search.component";
import {ClassificatorsTreeComponent} from "./classificators-tree.component";
import {ClassificatorTreeComponent} from "./components/classificator-tree/classificator-tree.component";
import {OkpdService} from "./okpd.service";
import {BackAPI} from "./back-api.service";
import {AppRoutingModule} from "./app-routing.module";
import {TreeViewComponent} from "./components/classificator-tree/tree-veiw.component";


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
    TreeViewComponent
  ],
  bootstrap: [AppComponent],
  providers: [
    BackAPI,
    OkpdService
  ]
})
export class AppModule {
}
