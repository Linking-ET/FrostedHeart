package com.teammoeg.frostedheart.content.town.resource;

public record VirtualResourceKeyAction(VirtualResourceKey resourceToModify, double amount, ResourceActionType actionType, ResourceActionMode actionMode) implements ITownResourceKeyAction{
    public boolean isAdd(){
        return actionType == ResourceActionType.ADD;
    }
    @Override
    public VirtualResourceKeyActionResult apply(TownResourceHolder resourceHolder) {
        double availableAmount;
        if(isAdd()) availableAmount = resourceHolder.getCapacityLeft();
        else availableAmount = resourceHolder.get(resourceToModify);
        double toModify = amount;
        if(availableAmount < amount){
            if(actionMode == ResourceActionMode.ATTEMPT || availableAmount <= TownResourceHolder.DELTA){
                return new VirtualResourceKeyActionResult(this, false, 0, amount);
            } else if(actionMode == ResourceActionMode.MAXIMIZE){
                toModify = availableAmount;
            }
        }
        if(isAdd()){
            resourceHolder.addUnsafe(resourceToModify, toModify);
        } else{
            resourceHolder.costUnsafe(resourceToModify, toModify);
        }
        if(availableAmount < amount){
            return new VirtualResourceKeyActionResult(this, false, toModify, amount - toModify);
        } else{
            return new VirtualResourceKeyActionResult(this, true, toModify, 0);
        }
    }
}
