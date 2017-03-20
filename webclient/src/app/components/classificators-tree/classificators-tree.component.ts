import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ClassificatorService} from "../../service/classificator.service";
import {ClassificatorTreeModel} from "./classificator-tree/classificator-tree";
import {Tree} from "./tree";
import {Classificator, ClassificatorItem} from "../../domain/classificator";
import {TabsModel} from "../../tab-model";


@Component({
  selector: 'classificators-tree',
  templateUrl: './classificators-tree.html'
})
export class ClassificatorsTreeComponent implements OnInit {

  private maxLevel: number = 3;

  classificatorTypes: Classificator[];

  tabModel: TabsModel = new TabsModel();

  classificatorTree: ClassificatorTreeModel;

  private routeParams: RouteParams;

  constructor(private route: ActivatedRoute, private router: Router, private classificatorService: ClassificatorService) {
    this.route.params.subscribe(params => {
      this.routeParams = params as RouteParams;
    });
  }

  updateTree(nodeId: string = null) {
    this.classificatorTree = null;
    const rootId = this.tabModel.selectedType;
    nodeId =  nodeId ? nodeId : (this.routeParams.code ? this.routeParams.code : rootId);
    const detailed = rootId != nodeId;
    const params = detailed ? {path: true} : {};
    this.treeClassificatorBy(nodeId, params).then(classificator => {
      //console.log('>>parentId:', parentId);
      this.classificatorTree = new ClassificatorTreeModel();
      this.classificatorTree.detailed = detailed;
      this.classificatorTree.tree = new Tree();
      fillTree(this.classificatorTree.tree, classificator);

    });
  }

  ngOnInit(): void {
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
      this.updateTree();
    });
  }

  onPathClick(nodeId: string) {
    // console.log('onPathClick', nodeId);
    this.updateTree(nodeId);
  }

  onSelectChange(tab: any) {
    this.tabModel.selectedIndex = tab.index;
    this.updateTree();
  }

  onNodeClick(rootId: string) {
    //console.log('ROOT:onNodeClick:' + rootId);
    let subTree = this.classificatorTree.treeBy(rootId);
    //console.log('subTree:onNodeClick:', subTree);
    if (subTree.nodes == null) {
      if ((subTree.level % this.maxLevel) == 0) {
       this.detailNode(subTree.id);
      } else {
        this.treeClassificatorBy(subTree.id).then(classificators => {
          fillTree(subTree, classificators);
        })
      }
    }
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
  model.name = classificator.name;
  model.parentId = classificator.parentCode;
  model.nodes = [];
  model.path = classificator.path;
  if (classificator.hasChildren) {
    for (let child of classificator.children) {
      const node = new Tree();
      node.name = child.name;
      node.classificator = child;
      node.parentId = child.parentCode;
      node.notes = child.notes;
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


