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

  private maxLevel:number = 3;

  tabs:[{
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

  classificatorTree:ClassificatorTreeModel;
  activeTabIndex:number = 0;
  routeParams:RouteParams;

  constructor(private route:ActivatedRoute, private router:Router, private okpdService:OkpdService) {
    this.route.params.subscribe(params => {
      this.routeParams = params as RouteParams;
    });
  }

  get selectedType() {
    let selectedTab = this.tabs.find(t => t.selected);
    return selectedTab ? selectedTab.type : null;
  }

  set selectedType(code:string) {
    // console.log('selectedType:' + code);
    let needLoad:string = null;
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
    this.classificatorTree = new ClassificatorTreeModel();
    const  nodeId = this.routeParams.code ? this.routeParams.code : rootId;
    this.loadTree(nodeId).then(tree => {
        if (this.routeParams.code) {
          const nodeId = this.routeParams.code;
          this.classificatorTree.detail(nodeId);
        } else {
          this.classificatorTree.setTree(tree);
        }
      })
      .catch(err => console.error('Error in classificator load', err));
  }

  ngOnInit():void {
    // console.log('ngOnInit', this.routeParams);
    this.selectedType = this.routeParams.type ? this.routeParams.type : 'okpd';
  }

  onPathClick(nodeId:string) {
   // console.log('onPathClick', nodeId);
    this.updateTree();
  }

  onSelectChange(tab:any) {
    // console.log('>>>>>>', tab);
    this.activeTabIndex = tab.index;
    this.selectedType = this.tabs[tab.index].type;
    this.router.navigate([`/tree/${this.selectedType}`]);
  }

  onNodeClick(nodeId:string) {
   // console.log('ROOT:onNodeClick:' + nodeId);
    this.loadTree(nodeId).then(node => {
      if (node.level == this.maxLevel) {
        this.classificatorTree.detail(nodeId);
      }
    }).catch(err => console.error('Error in classificator load', err));

  }

  loadTree(rootId:string):Promise<Tree> {
    let node = this.classificatorTree.treeBy(rootId);
    if (node == null) {
      return this.treeClassificatorBy(rootId).then(classificator => {
        if(classificator.length == 0) return null;
        const parentId = classificator.parentCode ? classificator.parentCode : this.selectedType;
          //console.log('>>parentId:', parentId);
        return this.loadTree(parentId).then(node => {
          //const tree = this.classificatorTree.treeBy(rootId);
          console.log('node>>>:', node);
          fillTree(node, classificator);
          return node;
        });
      })
    } else {
      return Promise.resolve(node);
    }
    // console.log('node1:', node);
    // if (node.nodes == null) {
    //   return this.treeClassificatorBy(node.id).then(classificators => {
    //     //const tree = this.classificatorTree.treeBy(node.id);
    //     console.log('fillNodes2')
    //     fillTree(node, classificators);
    //     return node;
    //   })
    // } else {
    //   return Promise.resolve(node);
    // }
  }


  treeClassificatorBy(rootId:string):Promise<ClassificatorTree> {
    const nodeId = (this.selectedType == rootId) ? null : rootId;
    switch (this.selectedType) {
      case 'okpd':
        return this.okpdService.okpdTreeBy(nodeId);
      case 'okpd2':
        return this.okpdService.okpd2TreeBy(nodeId);
      case 'tnved':
        return this.okpdService.tnvedTreeBy(nodeId);
      default:
        throw new Error(`Type ${this.selectedType} don't support`);
    }
  }

}

function fillTree(model:Tree, classificator:Classificator) {
  if (classificator == null) return model;
  console.log('model2:', model);
  model.name = classificator.name;
  model.id = classificator.code;
  model.parent = classificator.parentCode;
  model.nodes = [];
  for (let classificator of classificator.children) {
    const node = new Tree();
    node.name = classificator.name;
    node.id = classificator.code;
    node.parentId = classificator.parentCode;
    node.notes = classificator.notes;
    node.hasNodes = classificator.hasChildren;
    model.nodes.push(node);
  }
  return model;
}

class RouteParams {
  type:string;
  code:string;
}
