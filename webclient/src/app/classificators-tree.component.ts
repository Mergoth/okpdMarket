import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {OkpdService} from "./okpd.service";
import {ClassificatorTreeModel} from "./components/classificator-tree/classificator-tree";
import {Tree} from "./components/classificator-tree/tree";
import {Classificator} from "./classificator";


@Component({
  selector: 'classificators-tree',
  templateUrl: 'classificators-tree.html'
})
export class ClassificatorsTreeComponent implements OnInit {

  private maxLevel: number = 3;

  tabs: [{
    type: string;
    title: string;
    selected: boolean;
  }] = [
    {
      type: 'okpd',
      title: 'ОКПД',
      selected: false
    },
    {
      type: 'okpd2',
      title: 'ОКПД2',
      selected: false
    },
    {
      type: 'tnved',
      title: 'ТНВЭД',
      selected: false
    }
  ];

  classificatorTree: ClassificatorTreeModel;
  activeTabIndex: number = 0;
  routeParams: RouteParams;

  constructor(private route: ActivatedRoute, private router: Router, private okpdService: OkpdService) {
    this.route.params.subscribe(params => {
      this.routeParams = params as RouteParams;
    });
  }

  get selectedType() {
    let selectedTab = this.tabs.find(t => t.selected);
    return selectedTab ? selectedTab.type : null;
  }

  set selectedType(code: string) {
    // console.log('selectedType:' + code);
    let needLoad: string = null;
    this.tabs.forEach(t => {
      if (t.type == code) {
        if (!t.selected) needLoad = t.type;
        t.selected = true;
      } else {
        t.selected = false;
      }
    });
    if (needLoad) {
      this.updateTree();
    }
  }

  updateTree() {
    const rootId = this.selectedType;
    const nodeId = this.routeParams.code ? this.routeParams.code : rootId;
    const detailed = rootId != nodeId;
    const params = detailed ? {path:true} : {};
    this.treeClassificatorBy(nodeId, params).then(classificator => {
      //console.log('>>parentId:', parentId);
      this.classificatorTree = new ClassificatorTreeModel();
      this.classificatorTree.detailed = detailed;
      this.classificatorTree.tree = new Tree();
      this.classificatorTree.tree.id = nodeId;
      fillTree(this.classificatorTree.tree, classificator);

    });
  }

  ngOnInit(): void {
    // console.log('ngOnInit', this.routeParams);
    this.selectedType = this.routeParams.type ? this.routeParams.type : 'okpd';
  }

  onPathClick(nodeId: string) {
    // console.log('onPathClick', nodeId);
    this.updateTree();
  }

  onSelectChange(tab: any) {
    // console.log('>>>>>>', tab);
    this.activeTabIndex = tab.index;
    this.selectedType = this.tabs[tab.index].type;
    this.router.navigate([`/tree/${this.selectedType}`]);
  }

  onNodeClick(rootId: string) {
    //console.log('ROOT:onNodeClick:' + rootId);
    let subTree = this.classificatorTree.treeBy(rootId);
    //console.log('subTree:onNodeClick:', subTree);
    if (subTree.nodes == null) {
      if ((subTree.level % this.maxLevel) == 0) {
        //todo
        let code = subTree.id.replace(/\./g, '')
        this.router.navigate([`/tree/${this.selectedType}/${code}`]);
      } else {
        this.treeClassificatorBy(subTree.id).then(classificators => {
          fillTree(subTree, classificators);
        })
      }
    }
  }


  treeClassificatorBy(rootId: string, params: Object = {}): Promise<Classificator> {
    const nodeId = (this.selectedType == rootId) ? null : rootId;
    return this.okpdService.classificatorTree(this.selectedType, nodeId, params);
  }

}

function fillTree(model: Tree, classificator: Classificator) {
  if (classificator == null) return new Tree();
  model = model ? model : new Tree();
  model.name = classificator.name;
  model.id = classificator.code;
  model.parentId = classificator.parentCode;
  model.nodes = [];
  model.path = classificator.path;
  for (let child of classificator.children) {
    const node = new Tree();
    node.name = child.name;
    node.id = child.code;
    node.parentId = child.parentCode;
    node.notes = child.notes;
    node.hasNodes = child.hasChildren;
    model.nodes.push(node);
  }
 // console.log('model2:', model);
  return model;
}

class RouteParams {
  type: string;
  code: string;
}
