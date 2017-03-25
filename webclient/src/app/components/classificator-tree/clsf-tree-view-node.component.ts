import {Component, Input} from "@angular/core";
import {Tree} from "./tree.model";
import {EventService} from "../../service/event.service";
import {EVENT_NODE_DETAIL} from './consts';

@Component({
    selector: 'clsf-tree-view-node',
    template: `
<div *ngIf="model.hasNodes" class="list-item">
    <span [style.margin-left.px]="level*20"></span>
    <md-icon *ngIf="!model.expanded">expand_more</md-icon>
    <md-icon *ngIf="model.expanded">expand_less</md-icon>
    <span class="code">{{model.classificator.code}}</span> {{model.classificator.name}}
    <md-icon class="todetail" (click)="detailNode(model.id)">fullscreen</md-icon>
</div>
<div *ngIf="!model.hasNodes" class="list-item">
      <span [style.margin-left.px]="level*20"></span>
      <span class="no-icon"></span>
      <span class="code">{{model.classificator.code}}</span> {{model.classificator.name}}
      <md-icon class="todetail" (click)="detailNode(model.id)">fullscreen</md-icon>
</div>
`,
    styleUrls: ['./clsf-tree-view-node.css']
})
export class ClsfTreeViewNodeComponent {

    @Input() model: Tree;

    @Input() level: number;

    constructor(private eventService: EventService) { }

    detailNode(nodeId: string) {
        this.eventService.publish(EVENT_NODE_DETAIL, nodeId);
    }

}