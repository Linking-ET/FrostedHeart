package com.teammoeg.frostedheart.content.town.resource;

import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 不可直接添加ItemResourceKey，只能添加对应的物品
 */
public record ItemResourceKeyCostAction(ItemResourceKey resourceToModify, double amount, ResourceActionMode actionMode) implements ITownResourceKeyAction{

    @Override
    public ItemResourceKeyCostActionResult apply(TownResourceHolder resourceHolder) {
        double availableAmount;
        availableAmount = resourceHolder.get(resourceToModify);
        double toCost = amount;
        if(availableAmount<amount){
            if(actionMode==ResourceActionMode.ATTEMPT || availableAmount<=TownResourceHolder.DELTA){
                return new ItemResourceKeyCostActionResult(this, false, 0, amount, Collections.emptyMap());
            } else if(actionMode==ResourceActionMode.MAXIMIZE){
                toCost = availableAmount;
            }
        }
        double toCostCopy = toCost;//toCost接下来会修改，复制一份用于记录数量
        Map<ItemStackWrapper, Double> costDetail = new HashMap<>();
        Map<ItemStackWrapper, Double> items = resourceHolder.getAllItemsByWrapper(resourceToModify);
        for(ItemStackWrapper itemStackWrapper : items.keySet()){
            double itemResourceAmount = TownResourceHolder.getResourceAmount(itemStackWrapper, resourceToModify);
            double itemLeft = resourceHolder.get(itemStackWrapper);
            double itemAmountToCost = Math.min(toCost/itemResourceAmount, itemLeft);
            costDetail.put(itemStackWrapper, itemAmountToCost);
            resourceHolder.costUnsafe(itemStackWrapper, itemAmountToCost);
            toCost -= itemAmountToCost * itemResourceAmount;
            if(toCost<=TownResourceHolder.DELTA) break;
        }
        return new ItemResourceKeyCostActionResult(this, true, toCostCopy, amount - toCost, costDetail);
    }
}
