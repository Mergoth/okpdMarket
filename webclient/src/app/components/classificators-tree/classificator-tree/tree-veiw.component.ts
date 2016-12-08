import {Component, Input, Output, EventEmitter} from "@angular/core";
import {Tree} from "../tree";

@Component({
  selector: 'tree-view',
  templateUrl: 'tree-view.html',
  styleUrls: ['tree-view.css']
})
export class TreeViewComponent {

  @Input() model: Tree;

  @Output() nodeClick: EventEmitter<string> = new EventEmitter<string>();

  onNodeClick(nodeId: string) {
    console.log('onNodeClick:' + nodeId);
    this.nodeClick.emit(nodeId);
  }

  expand(treeNode: Tree) {
    if(treeNode.hasNodes) {
      treeNode.expanded = !treeNode.expanded;
      this.onNodeClick(treeNode.id);
    }
  }

}
