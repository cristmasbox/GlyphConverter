package com.blueapps.glpyhconverter.tomdc.items;

public class SpaceItem extends Item {

    boolean isGap;

    public SpaceItem(boolean isGap){
        this.isGap = isGap;
    }

    @Override
    public String getMdC() {
        if (isGap){
            return "..";
        } else {
            return ".";
        }
    }

}
