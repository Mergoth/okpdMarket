import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Tree} from "./model/tree.model";
import {ClsfTreeService} from "./service/clsf-tree.service";
import {EventService} from "../../service/event.service";
import {EVENT_NODE_EXPAND} from './consts';

const COMPONENT_NAME = 'ClsfTreeComponent';

@Component({
  selector: 'clsf-tree',
  providers: [
    ClsfTreeService
  ],
  template: `
      <clsf-tree-view *ngIf="tree" [tree]="tree" [clsfType]="clsfType"></clsf-tree-view>
      <div class="loading" *ngIf="!tree"></div>
  `
})
export class ClsfTreeComponent implements OnInit, OnDestroy {

  clsfType: string;

  tree: Tree;

  paramsSubscription;

  constructor(private route: ActivatedRoute,
              private treeService: ClsfTreeService,
              private eventService: EventService
  ) {
    console.log('');
  }

  initTree(clsfType: string) {
    console.debug('Init tree', clsfType);
    this.clsfType = clsfType;
    this.treeService.updateTree(new Tree(), this.clsfType).then(tree => this.tree = tree);
  }

  ngOnInit() {
    this.paramsSubscription = this.route.params.subscribe(params => {
      console.log('TREE +: route change ::', params);
      this.initTree(params['type']);

    });
    this.eventService.subscribeFor(COMPONENT_NAME, EVENT_NODE_EXPAND, this.expandNode);
  }

  ngOnDestroy() {
    this.eventService.unsubscribeAllFor(COMPONENT_NAME);
    this.paramsSubscription.unsubscribe();
  }

  expandNode = (rootId: string) => {
    let subTree = this.tree.subTree(rootId);
    if (subTree.nodes == null) {
      this.treeService.updateTree(subTree, this.clsfType).then(tree =>  subTree = tree);
    }
  };

}




