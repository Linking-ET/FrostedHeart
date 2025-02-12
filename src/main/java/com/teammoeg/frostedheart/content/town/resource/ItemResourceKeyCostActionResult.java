package com.teammoeg.frostedheart.content.town.resource;

import java.util.Map;

/**
 *
 * @param action 对应的ItemResourceKeyAction
 * @param allModified 应修改量是否等于实际修改量
 * @param totalModifiedAmount 已修改资源总量
 * @param residualAmount 应修改但未修改的资源量
 * @param details 具体消耗的物品明细。Mao中Double均为正数，为消耗或添加量。action中可查看操作是添加还是消耗。
 */
public record ItemResourceKeyCostActionResult(ItemResourceKeyCostAction action, boolean allModified, double totalModifiedAmount, double residualAmount, Map<ItemStackWrapper, Double> details) implements ITownResourceKeyActionResult{
    @Override
    public IResourceAction getAction() {
        return action;
    }

    @Override
    public void applyForce(TownResourceHolder resourceHolder) {
        details.forEach(resourceHolder::costUnsafe);
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
    public ItemResourceKey getTownResourceKey() {
        return action.resourceToModify();
    }
}
