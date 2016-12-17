//todo вынести tabs-classificator в компонент
export class TabsModel {

  tabs: TabModel[] = [];

  clear() {
    this.tabs = [];
  }

  push(tab: TabModel) {
    this.tabs.push(tab);
  }

  get selectedIndex(): number {
    return this.tabs.findIndex(t => t.selected);
  }

  set selectedIndex(index: number) {
    this.tabs.forEach(t => t.selected = false);
    this.tabs[index].selected = true;
  }

  get selectedType() {
    let selectedTab = this.tabs.find(t => t.selected);
    return selectedTab ? selectedTab.type : null;
  }

  /*
   * @return if changed
   */
  set selectedType(code: string) {
    let changed: boolean = false;
    this.tabs.forEach(t => {
      if (t.type == code) {
        if (!t.selected) changed = true;
        t.selected = true;
      } else {
        t.selected = false;
      }
    });
  }
}

export class TabModel {
  type: string;
  title: string;
  selected: boolean;
}
