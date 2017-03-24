import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {Tree} from "../tree.model";
import {EventService} from "../../../service/event.service";
import {EVENT_PATH_CLICK} from '../consts';


@Component({
    selector: 'classificator-tree',
    styleUrls: ['./classificator-tree.css'],
    template: `
 <nav class="path" *ngIf="tree.path">
  <div *ngFor="let item of tree.path;let i=index">
    <a *ngIf="item.code != tree.classificator.code" class="node" [style.margin-left.px]="i*10"  (click)="onPathClick(item.code)">
      <md-icon>keyboard_arrow_right</md-icon>&nbsp;<span class="code">{{item.code}}</span> {{item.name}}
    </a>
  </div>
</nav>
<tree-view [model]="tree"></tree-view>
`,
})
export class ClassificatorTreeComponent {

    @Input() type:string;

    @Input() tree:Tree;

    constructor(private eventService:EventService) {
    }

    onPathClick(nodeId:string) {
        this.eventService.publish(EVENT_PATH_CLICK, nodeId);
    }

}


