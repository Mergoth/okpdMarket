import {Injectable} from "@angular/core";
import {ClassificatorService} from "../../service/classificator.service";
import {ClassificatorItem, Classificator} from "../../domain/classificator";
import {Tree} from "./tree.model";


@Injectable()
export class ClassificatorTreeService {

    constructor(private classificatorService: ClassificatorService) {
    }

    updateTree(tree: Tree, clsfType:string) {
        const treeId = tree ? tree.id : null;
        return this.treeClassificatorBy(treeId, clsfType).then(classificator => {
            return fillTree(tree, classificator);
        });
    }

    treeClassificatorBy(rootId: string, clsfType: string): Promise<ClassificatorItem> {
        const nodeId = (clsfType == rootId) ? null : rootId;
        return this.classificatorService.classificatorTree(clsfType, nodeId, {});
    }


}

function fillTree(tree: Tree, classificator: ClassificatorItem) {
    if (classificator == null) return new Tree();
    tree = tree ? tree : new Tree();
    tree.classificator = classificator;
    tree.parentId = classificator.parentCode;
    tree.nodes = [];
    tree.path = classificator.path;
    if (classificator.hasChildren) {
        for (let child of classificator.children) {
            const node = new Tree();
            node.classificator = child;
            node.parentId = child.parentCode;
            tree.nodes.push(node);
        }
    }
    // console.log('model2:', model);
    return tree;
}