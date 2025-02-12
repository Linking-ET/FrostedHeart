package com.teammoeg.frostedheart.content.town.client;

import com.teammoeg.frostedheart.content.town.resource.ItemStackWrapper;

/**
 * 储存单个的物品类型及其数量。
 */
public class ClientSlotInformation {
    public ItemStackWrapper item;
    public double amount;

    public ClientSlotInformation(ItemStackWrapper item, double amount) {
        this.item = item;
        this.amount = amount;
    }
}
