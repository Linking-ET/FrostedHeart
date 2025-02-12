package com.teammoeg.frostedheart.content.town.client;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * 你是Slot吗？我觉得我是。
 * 仅用于在客户端展示物品，不应在服务端中存在。
 */
public class ClientFakeSlot extends Slot {
    public ClientTempItemHolder clientTempItemHolder;

    public ClientFakeSlot(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
    }


    @Override
    public final boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public final void set(@NotNull ItemStack stack) {
    }

    @Override
    public final int getMaxStackSize() {
        return 0;
    }

    @Override
    public final @NotNull ItemStack remove(int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public final boolean mayPickup(@NotNull Player player) {
        return false;
    }
}
