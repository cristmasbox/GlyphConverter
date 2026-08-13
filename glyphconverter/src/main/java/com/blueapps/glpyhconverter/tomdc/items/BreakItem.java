package com.blueapps.glpyhconverter.tomdc.items;

public class BreakItem extends Item{

    boolean pageBreak;

    public BreakItem(boolean pageBreak){
        this.pageBreak = pageBreak;
    }

    @Override
    public String getMdC() {
        if (pageBreak){
            return "!!";
        } else {
            return "!";
        }
    }
}
