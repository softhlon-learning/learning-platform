import {Item} from "../course-content/item";

export class NavigationItems {
  previousItem?: Item;
  currentItem: Item = new Item();
  nextItem?: Item;
}
