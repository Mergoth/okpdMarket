import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {ClassificatorTreeModel} from "../classificator-tree.model";
import {EventService} from "../../../service/event.service";
import {EVENT_PATH_CLICK} from '../consts';


@Component({
    selector: 'classificator-tree',
    templateUrl: './classificator-tree.html',
    styleUrls: ['./classificator-tree.css']

})
export class ClassificatorTreeComponent {

    @Input() type:string;

    @Input() model:ClassificatorTreeModel;

    constructor(private eventService:EventService) {
    }

    onPathClick(nodeId:string) {
        this.eventService.publish(EVENT_PATH_CLICK, nodeId);
    }

}


