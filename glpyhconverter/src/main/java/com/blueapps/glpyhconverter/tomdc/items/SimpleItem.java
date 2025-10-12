package com.blueapps.glpyhconverter.tomdc.items;

public class SimpleItem extends Item {

    private String id;

    public SimpleItem(String id){
        this.id = id;
    }

    @Override
    public String getMdC() {
        return id;
    }
}
