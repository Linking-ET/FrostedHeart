package com.teammoeg.frostedheart.content.town.client;

import com.teammoeg.frostedheart.content.town.resource.ItemStackWrapper;
import com.teammoeg.frostedheart.content.town.resource.TownResourceHolder;
import com.teammoeg.frostedheart.content.town.resource.ItemStackActionResult;
import com.teammoeg.frostedheart.content.town.resource.ResourceActionType;

import java.util.List;

/**
 * 打开城镇仓库的gui时，在客户端临时存储物品
 */
public class ClientTempItemHolder {
    public List<ClientSlotInformation> items;
    public void updateAll(List<ClientSlotInformation> items) {
        this.items = items;
    }

    public void update(ItemStackActionResult actionResult){
        ItemStackWrapper modifiedItem = ItemStackWrapper.of(actionResult.itemStackModified());
        int amount = actionResult.itemStackModified().getCount();
        if(actionResult.action().actionType() == ResourceActionType.COST){
            amount = -amount;
        }
        boolean needRemove = false;
        boolean isInList = false;
        for(ClientSlotInformation item : items){
            if(item.item.equals(modifiedItem)){
                isInList = true;
                item.amount += amount;
                if(item.amount<= TownResourceHolder.DELTA){
                    needRemove = true;
                }
                break;
            }
        }
        if(needRemove){
            items.removeIf(item -> item.item.equals(modifiedItem));
        }
        if(!isInList){
            items.add(new ClientSlotInformation(modifiedItem, amount));
        }
    }
}
