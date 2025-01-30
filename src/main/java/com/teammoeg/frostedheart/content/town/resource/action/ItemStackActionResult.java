package com.teammoeg.frostedheart.content.town.resource.action;

import net.minecraft.world.item.ItemStack;

/**
 * @param action 对应的ItemStackAction
 * @param allModified 应修改量是否等于实际修改量
 * @param itemStackModified 实际修改的ItemStack
 * @param itemStackLeft 未能成功修改时，剩余的ItemStack。
 *                      添加时，若应添加数量大于剩余容量，则为应添加量-实际添加量，消耗时，若应消耗量大于剩余物品数量，则为应消耗量-实际消耗量。
 *                      若全部成功添加/消耗，应为ItemStack.EMPTY
 */
public record ItemStackActionResult(ItemStackAction action, Boolean allModified, ItemStack itemStackModified, ItemStack itemStackLeft) implements IResourceActionResult{
    @Override
    public ItemStackAction getAction(){
        return action;
    }
}
