import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ClassificatorService} from "../../service/classificator.service";
import {ClassificatorTreeModel} from "./classificator-tree.model";
import {Tree} from "./tree.model";
import {Classificator, ClassificatorItem} from "../../domain/classificator";
import {TabsModel} from "../../tab-model";
import {EventService} from "../../service/event.service";
import {EVENT_NODE_DETAIL, EVENT_NODE_EXPAND, EVENT_PATH_CLICK} from './consts';

const COMPONENT_NAME = 'ClassificatorsTreeComponent';

@Component({
  selector: 'classificators-tree',
  templateUrl: './classificators-tree.html'
})
export class ClassificatorsTreeComponent implements OnInit, OnDestroy {

  private maxLevel: number = 3;

  classificatorTypes: Classificator[];

  tabModel: TabsModel = new TabsModel();

  classificatorTree: ClassificatorTreeModel;

  private routeParams: RouteParams;

  constructor(private route: ActivatedRoute, private router: Router, private classificatorService: ClassificatorService, private eventService: EventService) {
    route.params.subscribe(params => {
      this.routeParams = params as RouteParams;
    });

    eventService.subscribeFor(COMPONENT_NAME, EVENT_NODE_DETAIL, (nodeId) => {
      console.debug('EVENT_NODE_DETAIL:', nodeId);
      this.detailNode(nodeId);
    });

    eventService.subscribeFor(COMPONENT_NAME, EVENT_NODE_EXPAND, (nodeId) => {
      console.debug('EVENT_NODE_EXPAND:', nodeId);
      this.onNodeExpand(nodeId);
    });

    eventService.subscribeFor(COMPONENT_NAME, EVENT_PATH_CLICK, (nodeId) => {
      console.debug('EVENT_PATH_CLICK:', nodeId);
      this.onPathClick(nodeId);
    });

  }

  updateTree(nodeId: string = null) {
    this.classificatorTree = null;
    const rootId = this.tabModel.selectedType;
    nodeId = nodeId ? nodeId : (this.routeParams.code ? this.routeParams.code : rootId);
    const detailed = rootId != nodeId;
    if(detailed) {
      this.tabModel.disableAllExcept(rootId);
    } else {
      this.tabModel.enableAll();
    }
    const params = detailed ? {path: true} : {};
    this.treeClassificatorBy(nodeId, params).then(classificator => {
      //console.log('>>parentId:', parentId);
      this.classificatorTree = new ClassificatorTreeModel();
      this.classificatorTree.detailed = detailed;
      this.classificatorTree.tree = new Tree();
      fillTree(this.classificatorTree.tree, classificator);
    });
  }

  ngOnInit() {
    this.classificatorService.classificatorTypes().then(res => {
      this.classificatorTypes = res;
      this.tabModel.clear();
      this.classificatorTypes.forEach(clsfType => this.tabModel.push({
          type: clsfType.code,
        title: clsfType.name,
        selected: false
      }));
    }).then(_ => {
        this.tabModel.selectedType = this.routeParams.type ? this.routeParams.type : this.classificatorTypes[0].code;
    });
  }

  ngOnDestroy() {
    this.eventService.unsubscribeAllFor(COMPONENT_NAME);
  }

  onPathClick(nodeId: string) {
    // console.log('onPathClick', nodeId);
    this.router.navigate([`/tree/${this.tabModel.selectedType}/${nodeId}`]);
    this.updateTree(nodeId);
  }

  onSelectChange(tab: any) {
    console.log('onSelectChange:', tab)
    this.tabModel.selectedIndex = tab.index;
    this.updateTree();
  }

  onNodeExpand(rootId: string) {
    let subTree = this.classificatorTree.treeBy(rootId);
    //console.log('subTree:onNodeClick:', subTree);
    if (subTree.nodes == null) {
      if (this.needDetail()) {
       this.detailNode(subTree.id);
      } else {
        this.treeClassificatorBy(subTree.id).then(classificators => {
          fillTree(subTree, classificators);
        })
      }
    }
  }

  //todo: оставить?
  needDetail() {
    return false; // (subTree.level % this.maxLevel) == 0
  }

  detailNode(nodeId: string) {
    this.router.navigate([`/tree/${this.tabModel.selectedType}/${nodeId}`]);
  }

  treeClassificatorBy(rootId: string, params: Object = {}): Promise<ClassificatorItem> {
    const nodeId = (this.tabModel.selectedType == rootId) ? null : rootId;
    return this.classificatorService.classificatorTree(this.tabModel.selectedType, nodeId, params);
  }
}

function fillTree(model: Tree, classificator: ClassificatorItem) {
  if (classificator == null) return new Tree();
  model = model ? model : new Tree();
  model.classificator = classificator;
  model.parentId = classificator.parentCode;
  model.nodes = [];
  model.path = classificator.path;
  if (classificator.hasChildren) {
    for (let child of classificator.children) {
      const node = new Tree();
      node.classificator = child;
      node.parentId = child.parentCode;
      model.nodes.push(node);
    }
  }
  // console.log('model2:', model);
  return model;
}

class RouteParams {
  type: string;
  code: string;
}


