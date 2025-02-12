package com.teammoeg.frostedheart.content.town.network;

import com.teammoeg.chorda.network.CMessage;
import com.teammoeg.chorda.util.client.ClientUtils;
import com.teammoeg.frostedheart.content.town.resource.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class TownResourcePacket implements CMessage {
    public TownResourcePacket(TownResourceHolder resourceHolder) {
        this.resourceHolder = resourceHolder;
    }

    public TownResourceHolder resourceHolder;
    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeMap(resourceHolder.getAllItems(), FriendlyByteBuf::writeItem, FriendlyByteBuf::writeDouble);
        buffer.writeMap(resourceHolder.getAllVirtualResources(), (innerBuffer, virtualResourceKey) -> {
            innerBuffer.writeUtf(virtualResourceKey.getType().getKey());
            innerBuffer.writeInt(virtualResourceKey.getLevel());
        }, FriendlyByteBuf::writeDouble);
    }

    public static TownResourcePacket decode(FriendlyByteBuf buffer){
        Map<ItemStackWrapper, Double> items = buffer.readMap((innerBuffer) -> ItemStackWrapper.of(innerBuffer.readItem()), FriendlyByteBuf::readDouble);
        Map<VirtualResourceKey, Double> virtualResources = buffer.readMap((innerBuffer) -> {
            VirtualResourceType type = VirtualResourceType.from(innerBuffer.readUtf());
            int level = innerBuffer.readInt();
            return VirtualResourceKey.of(type, level);
        }, FriendlyByteBuf::readDouble);
        return new TownResourcePacket(new TownResourceHolder(items, virtualResources));
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player player = DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> ClientUtils::getPlayer);
            AbstractContainerMenu abstractContainerMenu = player.containerMenu;
            if(abstractContainerMenu instanceof TownResourceMenu menu){
                menu.setResourceManager(TownResourceManager.of(resourceHolder));
            }
        });
    }
}
