package com.teammoeg.frostedheart.content.town.resource;

import blusunrize.immersiveengineering.client.gui.IEContainerScreen;
import com.teammoeg.frostedheart.content.town.client.ClientTempItemHolder;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TownResourceScreen extends IEContainerScreen<TownResourceMenu> {
    public ClientTempItemHolder clientTempItemHolder = new ClientTempItemHolder();

    public TownResourceScreen(TownResourceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, null);//todo
    }

    @Override
    protected void init(){
        super.init();
        addRenderableWidget(new EditBox(font, leftPos + 8, topPos + 20, 150, 20, Component.translatable("gui.frostedheart.town_resource.search")));

        //todo: 添加搜索框、添加ScrollBar、添加Client Slots
    }
}
