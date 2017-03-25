import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Tree} from "./tree.model";
import {ClsfTreeService} from "./clsf-tree.service";
import {EventService} from "../../service/event.service";
import {EVENT_NODE_DETAIL, EVENT_NODE_EXPAND, EVENT_PATH_CLICK} from './consts';

const COMPONENT_NAME = 'ClsfTreeComponent';

@Component({
  selector: 'clsf-tree',
  providers: [
    ClsfTreeService
  ],
  template: `
      <clsf-tree-view *ngIf="tree" [model]="tree"></clsf-tree-view>
      <div class="loading" *ngIf="!tree"></div>
  `
})
export class ClsfTreeComponent implements OnInit, OnDestroy {

  clsfType: string;

  tree: Tree;

  paramsSubscription;

  constructor(private route: ActivatedRoute,
              private router: Router ,
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
    this.eventService.subscribeFor(COMPONENT_NAME, EVENT_NODE_DETAIL, this.detailNode);
    this.eventService.subscribeFor(COMPONENT_NAME, EVENT_NODE_EXPAND, this.expandNode);
    this.eventService.subscribeFor(COMPONENT_NAME, EVENT_PATH_CLICK,  this.detailNode);
  }

  ngOnDestroy() {
    this.eventService.unsubscribeAllFor(COMPONENT_NAME);
    this.paramsSubscription.unsubscribe();
  }

  expandNode = (rootId: string) => {
    let subTree = this.tree.subTree(rootId);
    if (subTree.nodes == null) {
      this.treeService.updateTree(subTree, this.clsfType).then(tree =>    subTree = tree);
    }
  };

  detailNode = (nodeId: string) => {
    console.log('navigate to:',`/tree/${this.clsfType}/${nodeId}` );
   this.router.navigate([`/tree/${this.clsfType}/${nodeId}`]);
  };

}




