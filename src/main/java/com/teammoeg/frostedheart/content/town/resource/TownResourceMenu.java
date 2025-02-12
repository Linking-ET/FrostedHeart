package com.teammoeg.frostedheart.content.town.resource;

import com.teammoeg.chorda.menu.CBaseMenu;
import com.teammoeg.frostedheart.bootstrap.common.FHMenuTypes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TownResourceMenu extends CBaseMenu {

    public static final int INV_START = 16;//todo: 这是个啥玩意？
    TownResourceManager resourceManager = TownResourceManager.EMPTY;

    public TownResourceMenu(int pContainerId, Inventory playerInventory, FriendlyByteBuf byteBuf){
        super(FHMenuTypes.TOWN_RESOURCE.get(), pContainerId, playerInventory.player, INV_START);
    }

    public TownResourceMenu(int pContainerId, Inventory playerInventory, TownResourceManager resourceManager){
        super(FHMenuTypes.TOWN_RESOURCE.get(), pContainerId, playerInventory.player, INV_START);
        //this.resourceManager = resourceManager;
    }

    public void update(){
        //todo: 应该要干些什么
    }

    public void setResourceManager(TownResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.update();
    }

}
