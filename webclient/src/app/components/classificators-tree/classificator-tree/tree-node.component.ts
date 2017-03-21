import {Component, Input} from "@angular/core";
import {Tree} from "../tree.model";
import {EventService} from "../../../service/event.service";
import {EVENT_NODE_DETAIL} from '../consts';

@Component({
    selector: 'tree-node',
    templateUrl: './tree-node.html',
    styleUrls: ['./tree-node.css']
})
export class TreeNodeComponent {

    @Input() model: Tree;

    @Input() level: number;

    constructor(private eventService: EventService) {
    }

    detailNode(nodeId: string) {
        this.eventService.publish(EVENT_NODE_DETAIL, nodeId);
    }

}