import {Component, Input, Output, EventEmitter} from "@angular/core";
import {Tree} from "../tree";

@Component({
    selector: 'tree-node',
    templateUrl: './tree-node.html',
    styleUrls: ['./tree-node.css']
})
export class TreeNodeComponent {

    @Input() model: Tree;

    @Input() level: number;

    @Output() nodeDetail: EventEmitter<string> = new EventEmitter<string>();

    onNodeDetail(nodeId: string) {
        this.nodeDetail.emit(nodeId);
    }

}