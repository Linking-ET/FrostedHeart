package com.teammoeg.frostedheart.content.town.resource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teammoeg.chorda.util.io.CodecUtil;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Wrapper for ItemStack, added special hashCode and equals method, for saving ItemStack in HashMap.
 * The count of ItemStack will be ignored.
 * The count of ItemStack will be changed to 1 when creating this wrapper.
 * Because TownResourceHolder used other things to save the amount of items.
 */
@Getter
public class ItemStackWrapper {
    public ItemStack itemStack;

    public static final Codec<ItemStackWrapper> CODEC = RecordCodecBuilder.create(t -> t.group(
                    CodecUtil.defaultValue(ItemStack.CODEC, ItemStack.EMPTY).fieldOf("itemStack").forGetter(o -> o.itemStack)
            ).apply(t, ItemStackWrapper::new)
    );

    public ItemStackWrapper(ItemStack itemStack) {
        this.itemStack = itemStack.copyWithCount(1);
    }

    public static ItemStackWrapper of(ItemStack itemStack) {
        return new ItemStackWrapper(itemStack);
    }

    public boolean equivalentTo(ItemStack itemStack) {
        return ItemStack.isSameItemSameTags(this.itemStack, itemStack);
    }

    public boolean equals(Object o) {
        ItemStack itemStack2;
        if (o instanceof ItemStackWrapper) {
            itemStack2 = ((ItemStackWrapper) o).getItemStack();
        } else return false;
        return ItemStack.isSameItemSameTags(itemStack, itemStack2);
    }

    public int hashCode() {
        int itemHash = itemStack.getItem().hashCode();
        int tagHash = itemStack.getTag() == null ? 0 : itemStack.getTag().hashCode();
        return Objects.hash(itemHash, tagHash);
    }

}
