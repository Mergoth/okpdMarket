import {Component, Input} from "@angular/core";
import {Tree} from "../tree.model";
import {EventService} from "../../../service/event.service";
import {EVENT_NODE_EXPAND} from '../consts';

@Component({
  selector: 'tree-view',
  templateUrl: './tree-view.html'
})
export class TreeViewComponent {

  @Input() model: Tree;

  constructor(private eventService: EventService) {
  }

  expand(treeNode: Tree) {
    if(treeNode.hasNodes) {
      treeNode.expanded = !treeNode.expanded;
      this.eventService.publish(EVENT_NODE_EXPAND, treeNode.id);
    }
  }

}
