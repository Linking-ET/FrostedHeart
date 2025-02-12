package com.teammoeg.frostedheart.content.town.resource;

/**
 * 这个Result只记录数量，对应的ItemR
 * @param action 对应的ResourceKeyAction
 * @param allModified 是否全部修改成功
 * @param modifiedAmount 实际添加/消耗的资源数量
 * @param residualAmount 应修改但未修改的资源数量
 */
public record VirtualResourceKeyActionResult(VirtualResourceKeyAction action, boolean allModified, double modifiedAmount, double residualAmount ) implements ITownResourceKeyActionResult {

    @Override
    public IResourceAction getAction() {
        return action;
    }

    @Override
    public void applyForce(TownResourceHolder resourceHolder) {
        VirtualResourceKey resourceKey = action.resourceToModify();
        if(action.isAdd()){
            resourceHolder.addUnsafe(resourceKey,modifiedAmount);
        } else{
            resourceHolder.costUnsafe(resourceKey,modifiedAmount);
        }
    }

    @Override
    public double getAmount() {
        return action.amount();
    }

    @Override
    public int getLevel() {
        return action.resourceToModify().getLevel();
    }

    @Override
    public VirtualResourceKey getTownResourceKey() {
        return action.resourceToModify();
    }

    public double totalModifiedAmount(){
        return modifiedAmount;
    }
}
