package com.teammoeg.frostedheart.content.town.resource;

public interface ITownResourceKeyAction extends IResourceAction{
    @Override
    ITownResourceKeyActionResult apply(TownResourceHolder resourceHolder);

    static ITownResourceKeyAction create(ITownResourceKey resourceKey, double amount, ResourceActionMode actionMode){
        if(resourceKey instanceof ItemResourceKey itemResourceKey){
            return new ItemResourceKeyCostAction(itemResourceKey, amount, actionMode);
        } else if(resourceKey instanceof VirtualResourceKey virtualResourceKey){
            return new VirtualResourceKeyAction(virtualResourceKey, amount, ResourceActionType.COST, actionMode);
        }
        else throw new IllegalArgumentException("resourceKey must be ItemResourceKey or VirtualResourceKey");//如果添加其它TownResourceKey的话，要加上
    }
}
