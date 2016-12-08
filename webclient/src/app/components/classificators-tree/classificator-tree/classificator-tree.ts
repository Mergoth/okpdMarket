import {Tree} from "../tree";

export class ClassificatorTreeModel {

  detailed:boolean;

  tree:Tree;

  detail(nodeId:string) {
    this.detailed = true;
    this.tree = this.treeBy(nodeId);
  }

  treeBy(rootId:string):Tree {
    return this.tree.subTree(rootId);
  }

}
