package com.teammoeg.frostedheart.content.town.resource.action;

import com.teammoeg.frostedheart.content.town.resource.TownResourceHolder;
import com.teammoeg.frostedheart.content.town.resource.TownResourceManager;
import net.minecraft.world.item.ItemStack;

public record ItemStackAction(ItemStack itemToModify, ResourceActionType actionType,
                              ResourceActionMode actionMode) implements IResourceAction {

    public boolean isAdd() {
        return actionType == ResourceActionType.ADD;
    }

    @Override
    public IResourceActionResult apply(TownResourceManager resourceManager) {
        int amount = itemToModify.getCount();
        double availableAmount;
        if (isAdd()) availableAmount = resourceManager.getCapacityLeft();
        else availableAmount = resourceManager.get(itemToModify);
        if (availableAmount < amount) {
            if (actionMode == ResourceActionMode.ATTEMPT || availableAmount <= TownResourceHolder.DELTA) {
                return new ItemStackActionResult(this, false, ItemStack.EMPTY, itemToModify.copy());
            }
            int toModify = (int) Math.floor(availableAmount);
            if (isAdd()) {
                resourceManager.addIfHaveCapacity(itemToModify, toModify);
            } else {
                resourceManager.costIfHaveEnough(itemToModify, toModify);
            }
            return new ItemStackActionResult(this, false, itemToModify.copyWithCount(toModify), itemToModify.copyWithCount(amount - toModify));
        } else {
            if (isAdd()) {
                resourceManager.addIfHaveCapacity(itemToModify, amount);
            } else {
                resourceManager.costIfHaveEnough(itemToModify, amount);
            }
            return new ItemStackActionResult(this, true, itemToModify.copy(), ItemStack.EMPTY);
        }
    }
}
