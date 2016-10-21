import {Component, Input, Output, EventEmitter} from "@angular/core";
import {ClassificatorTreeModel} from "./classificator-tree";

@Component({
  moduleId: module.id,
  selector: 'classificator-tree',
  templateUrl: 'classificator-tree.html'
})
export class ClassificatorTreeComponent {

  @Input() type: string;

  @Output() nodeClick: EventEmitter<string> = new EventEmitter<string>();

  @Input() model: ClassificatorTreeModel;

  onNodeClick(nodeId: string) {
    this.nodeClick.emit(nodeId);
  }

}


