import {Component, OnDestroy, OnInit} from "@angular/core";
import {ClsfTreeService} from "./clsf-tree.service";
import {Tree} from "./tree.model";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'clsf-tree-detailed',
    styleUrls: ['./clsf-tree-detailed.css'],
    providers: [
        ClsfTreeService
    ],
    template: `        
        <div *ngIf="tree">
            <nav class="path" *ngIf="tree.path">
                <div *ngFor="let item of tree.path;let i=index">
                    <a *ngIf="item.code != tree.classificator.code" class="node" [style.margin-left.px]="i*10"  [routerLink]="['/tree', clsfType, item.code]">
                        <md-icon>keyboard_arrow_right</md-icon>&nbsp;<span class="code">{{item.code}}</span> {{item.name}}
                    </a>
                </div>
            </nav>
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
        </div>
    `
})
export class ClsfTreeDetailedComponent implements OnInit, OnDestroy {

    clsfType: string;

    clsfCode: string;

    tree:Tree;

    paramsSub;

    constructor(private route: ActivatedRoute,
                private treeService: ClsfTreeService) {
    }

    ngOnInit() {
        console.log('TREE DETAILED: init');
        this.paramsSub = this.route.params.subscribe(params => {
            console.log('TREE DETAILED: route change ::', params);
            this.clsfType = params['type'];
            this.clsfCode = params['code'];
            this.initTree();
        });
    }

    ngOnDestroy() {
        this.paramsSub.unsubscribe();
    }

    initTree() {
        this.treeService.updateTree(new Tree(this.clsfCode), this.clsfType).then(tree => this.tree = tree);
    }

    onPathClick(nodeId:string) {
    }

}
