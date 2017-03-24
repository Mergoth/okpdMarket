import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {ClassificatorTreeModel} from "./classificator-tree.model";
import {EventService} from "../../service/event.service";
import {EVENT_PATH_CLICK} from './consts';
import {Tree} from "./tree.model";

@Component({
    selector: 'classificator-tree-detailed',
    template: `
<div>
  <h2>{{tree.classificator.code}} {{tree.classificator.name}}</h2>
  {{tree.classificator.notes}}
  <h3>Связи</h3>
  <div *ngIf="tree.classificator.links" class="links">
    <div *ngFor="let links of tree.classificator.links | mapIterable" class="section">
      <div class="title">{{links.key}}:</div>
      <span *ngFor="let link of links.val" class="link">
         <a [routerLink]="['/tree', links.key, link.code]">{{link.code}}</a>
        <span class="name">{{link.name}}</span>
      </span>
    </div>
  </div>

</div>`
})
export class ClassificatorTreeDetailedComponent {

    @Input() type:string;

    @Input() tree:Tree;

    constructor(private eventService:EventService) {
    }

    onPathClick(nodeId:string) {
        this.eventService.publish(EVENT_PATH_CLICK, nodeId);
    }

}
