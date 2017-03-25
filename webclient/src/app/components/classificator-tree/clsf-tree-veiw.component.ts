import {Component, Input} from "@angular/core";
import {Tree} from "./tree.model";
import {EventService} from "../../service/event.service";
import {EVENT_NODE_EXPAND} from './consts';

@Component({
  selector: 'clsf-tree-view',
  template: `
      <div *ngIf="model != null">
          <div *ngFor="let node of model.nodes">
              <clsf-tree-view-node (click)="expand(node)" [model]="node" [level]="model.level"></clsf-tree-view-node>
              <div *ngIf="node.expanded">
                  <clsf-tree-view [model]="node"></clsf-tree-view>
              </div>
          </div>
      </div>
      <div class="loading" style="float: right; position: relative; top: -25px"  *ngIf="model == null || model.nodes == null"></div>
  `
})
export class ClsfTreeViewComponent {

  @Input() model: Tree;

  constructor(private eventService: EventService) {
  }

  expand(treeNode: Tree) {
    if(treeNode.hasNodes) {
      treeNode.expanded = !treeNode.expanded;
      this.eventService.publish(EVENT_NODE_EXPAND, treeNode.id);
    }
  }

}
