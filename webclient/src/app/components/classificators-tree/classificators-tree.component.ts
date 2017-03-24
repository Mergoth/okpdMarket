import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Tree} from "./tree.model";
import {ClassificatorTreeService} from "./classificator-tree.service";
import {Classificator, ClassificatorItem} from "../../domain/classificator";
import {TabsModel} from "../../tab-model";
import {EventService} from "../../service/event.service";
import {EVENT_NODE_DETAIL, EVENT_NODE_EXPAND, EVENT_PATH_CLICK} from './consts';

const COMPONENT_NAME = 'ClassificatorsTreeComponent';

@Component({
  selector: 'classificators-tree',
  template: `
<classificator-tree *ngIf="tree"
        [tree]="tree"
        [type]="clsfType">
</classificator-tree>
<div class="loading" *ngIf="!classificatorTree"></div>
`
})
export class ClassificatorsTreeComponent implements OnInit, OnDestroy {

  private maxLevel: number = 3;

  clsfType: string;

  tree: Tree;

  paramsSub;


  constructor(private route: ActivatedRoute, private treeService: ClassificatorTreeService, private eventService: EventService) {
    this.paramsSub = this.route.params.subscribe(params => {
      console.log('TREE>>>>>>>>>>>>', params);
      this.clsfType = params['type'];
      //this.id = parseInt(params['id'], 10)
      this.updateTree(this.clsfType);

    });
  }

  updateTree(nodeId: string = null) {
    console.log('updateTree nodeId', nodeId);
    const rootId = this.clsfType;
    console.log('rootId', rootId);
    nodeId = nodeId ? nodeId : rootId;
    //const detailed = rootId != nodeId;
    //if(detailed) {
    //  this.tabModel.disableAllExcept(rootId);
    //} else {
    //  this.tabModel.enableAll();
    //}
    this.treeService.updateTree(new Tree(), this.clsfType).then(tree => {
      //console.log('>>parentId:', parentId);
      this.tree = tree;
    });
  }

  ngOnInit() {
    console.log('!!!!! ON INIT');

    this.eventService.subscribeFor(COMPONENT_NAME, EVENT_NODE_DETAIL, (nodeId) => {
      console.debug('EVENT_NODE_DETAIL:', nodeId);
      this.detailNode(nodeId);
    });

    this.eventService.subscribeFor(COMPONENT_NAME, EVENT_NODE_EXPAND, (nodeId) => {
      console.debug('EVENT_NODE_EXPAND:', nodeId);
      this.onNodeExpand(nodeId);
    });

    this.eventService.subscribeFor(COMPONENT_NAME, EVENT_PATH_CLICK, (nodeId) => {
      console.debug('EVENT_PATH_CLICK:', nodeId);
      this.onPathClick(nodeId);
    });
  }

  ngOnDestroy() {
    this.eventService.unsubscribeAllFor(COMPONENT_NAME);
    this.paramsSub.unsubscribe();
  }

  onPathClick(nodeId: string) {
    // console.log('onPathClick', nodeId);
    //this.router.navigate([`/tree/${this.clsfType}/${nodeId}`]);
    this.updateTree(nodeId);
  }

  onNodeExpand(rootId: string) {
    let subTree = this.tree.subTree(rootId);
    //console.log('subTree:onNodeClick:', subTree);
    if (subTree.nodes == null) {
      if (this.needDetail()) {
       this.detailNode(subTree.id);
      } else {
        this.treeService.updateTree(subTree, this.clsfType).then(tree => {
          subTree = tree;
        })
      }
    }
  }

  //todo: оставить?
  needDetail() {
    return false; // (subTree.level % this.maxLevel) == 0
  }

  detailNode(nodeId: string) {
    console.log('navigate to:',`/tree/${this.clsfType}/${nodeId}` );
   // this.router.navigate([`/tree/${this.clsfType}/${nodeId}`]);
    this.updateTree(nodeId);
  }


}




