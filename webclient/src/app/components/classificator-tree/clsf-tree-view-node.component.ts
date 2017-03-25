import {Component, Input} from "@angular/core";
import {Tree} from "./tree.model";

@Component({
    selector: 'clsf-tree-view-node',
    template: `
        <div *ngIf="tree.hasNodes" class="list-item">
            <span [style.margin-left.px]="level*20"></span>
            <md-icon *ngIf="!tree.expanded">expand_more</md-icon>
            <md-icon *ngIf="tree.expanded">expand_less</md-icon>
            <span class="code">{{tree.classificator.code}}</span> {{tree.classificator.name}}
            <a [routerLink]="['/tree', clsfType, tree.id]">
                <md-icon class="todetail">fullscreen</md-icon>
            </a>
        </div>
        <div *ngIf="!tree.hasNodes" class="list-item">
            <span [style.margin-left.px]="level*20"></span>
            <span class="no-icon"></span>
            <span class="code">{{tree.classificator.code}}</span> {{tree.classificator.name}}
            <a [routerLink]="['/tree', clsfType, tree.id]">
                <md-icon class="todetail">fullscreen</md-icon>
            </a>
        </div>
    `,
    styleUrls: ['./clsf-tree-view-node.css']
})
export class ClsfTreeViewNodeComponent {

    @Input() tree: Tree;

    @Input() clsfType: string;

    @Input() level: number;

}