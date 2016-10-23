import {Component, Input, Output, EventEmitter} from "@angular/core";
import {Router} from "@angular/router";
import {ClassificatorTreeModel} from "./classificator-tree";

@Component({
  moduleId: module.id,
  selector: 'classificator-tree',
  templateUrl: 'classificator-tree.html',
  styles: [`.path .node { cursor: pointer; color: #039be5; text-decoration: underline;} .path {margin: 20px}`]

})
export class ClassificatorTreeComponent {

  @Input() type: string;

  @Output() nodeClick: EventEmitter<string> = new EventEmitter<string>();

  @Output() pathClick: EventEmitter<string> = new EventEmitter<string>();

  @Input() model: ClassificatorTreeModel;

  constructor(private router: Router) {
  }

  onNodeClick(nodeId: string) {
   // console.log('onNodeClick', nodeId);
    this.nodeClick.emit(nodeId);
  }

  onPathClick(nodeId: string) {
   // console.log('onPathClick', nodeId);
    this.pathClick.emit(nodeId);
    this.router.navigateByUrl(`/tree/okpd/${encodeURIComponent(nodeId)}`);
  }

}


