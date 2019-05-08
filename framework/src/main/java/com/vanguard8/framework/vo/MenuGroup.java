package com.vanguard8.framework.vo;


import com.vanguard8.framework.entity.Function;

import java.util.List;

public class MenuGroup{
    Function titleItem;
    List<Function> items;

    public Function getTitleItem() {
        return titleItem;
    }

    public void setTitleItem(Function titleItem) {
        this.titleItem = titleItem;
    }

    public List<Function> getItems() {
        return items;
    }

    public void setItems(List<Function> items) {
        this.items = items;
    }
}
