import {Tree} from "./tree";

export class ClassificatorTreeModel {

  detailed:string;

  tree:Tree;

  cachedTree:Tree;

  treePath:Tree[];

  constructor(private rootId:string) {
    this.tree = new Tree();
    this.tree.id = rootId;
    this.cachedTree = this.tree;
    this.detailed = null;
    this.treePath = null;
  }

  detail(nodeId:string) {
    this.detailed = nodeId;
    this.tree = this.treeBy(nodeId);
    this.constructPath();
  }

  constructPath():Tree[] {
    let node = this.tree;
    if(this.tree == null) return [];
    const path:Tree[] = [];
    while (node.parent != null) {
      path.push(node.parent);
      node = node.parent;
    }
    this.treePath = path.reverse().splice(1);
    console.log('constructPath:', this.treePath);
  }

  treeBy(rootId:string):Tree {
    if (rootId == this.rootId) {
      return this.cachedTree;
    }
    return this.cachedTree.subTree(rootId);
  }

}
