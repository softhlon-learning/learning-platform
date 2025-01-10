import {Chapter} from "./chapter";

export class Item {
    name: string = '';
    time: string = '';
    id: string = '';
    selected: boolean = false;
    processed: boolean = false;
    chapter: Chapter = new Chapter();
}
