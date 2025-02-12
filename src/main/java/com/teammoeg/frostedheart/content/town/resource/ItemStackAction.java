package com.teammoeg.frostedheart.content.town.resource;

import net.minecraft.world.item.ItemStack;

public record ItemStackAction(ItemStack itemToModify, ResourceActionType actionType,
                              ResourceActionMode actionMode) implements IResourceAction {

    public boolean isAdd() {
        return actionType == ResourceActionType.ADD;
    }

    @Override
    public ItemStackActionResult apply(TownResourceHolder townResourceHolder) {
        int amount = itemToModify.getCount();
        double availableAmount;
        if (isAdd()) availableAmount = townResourceHolder.getCapacityLeft();
        else availableAmount = townResourceHolder.get(itemToModify);
        if (availableAmount < amount) {
            if (actionMode == ResourceActionMode.ATTEMPT || availableAmount <= TownResourceHolder.DELTA) {
                return new ItemStackActionResult(this, false, ItemStack.EMPTY, itemToModify.copy());
            }
            int toModify = (int) Math.floor(availableAmount);
            if (isAdd()) {
                townResourceHolder.addUnsafe(itemToModify, toModify);
            } else {
                townResourceHolder.costUnsafe(itemToModify, toModify);
            }
            return new ItemStackActionResult(this, false, itemToModify.copyWithCount(toModify), itemToModify.copyWithCount(amount - toModify));
        } else {
            if (isAdd()) {
                townResourceHolder.addUnsafe(itemToModify, amount);
            } else {
                townResourceHolder.costUnsafe(itemToModify, amount);
            }
            return new ItemStackActionResult(this, true, itemToModify.copy(), ItemStack.EMPTY);
        }
    }
}
