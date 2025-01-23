package com.teammoeg.frostedheart.content.town.resource;

import com.teammoeg.chorda.menu.CBaseMenu;
import com.teammoeg.frostedheart.bootstrap.common.FHMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.HashMap;
import java.util.Map;

public class TownResourceMenu extends CBaseMenu {
    public static final int INV_START = 16;//todo: 待定

    TownResourceManager resourceManager = TownResourceManager.EMPTY;
    Map<ItemStackWrapper, Double> items = new HashMap<>();

    public TownResourceMenu(int pContainerId, Inventory playerInventory, FriendlyByteBuf byteBuf){
        super(FHMenuTypes.TOWN_RESOURCE.get(), pContainerId, playerInventory.player, INV_START);
    }

    public TownResourceMenu(int pContainerId, Inventory playerInventory, TownResourceManager resourceManager){
        super(FHMenuTypes.TOWN_RESOURCE.get(), pContainerId, playerInventory.player, INV_START);
        this.resourceManager = resourceManager;
    }
}
