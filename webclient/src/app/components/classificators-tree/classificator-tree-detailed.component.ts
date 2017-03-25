import {Component, Input, OnDestroy, OnInit} from "@angular/core";
import {ClassificatorTreeService} from "./classificator-tree.service";
import {Tree} from "./tree.model";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'classificator-tree-detailed',
    template: `
<div *ngIf="tree">
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
export class ClassificatorTreeDetailedComponent implements OnInit, OnDestroy {

    clsfType: string;

    clsfCode: string;

    tree:Tree;

    paramsSub;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private treeService: ClassificatorTreeService) {
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

}
