package com.teammoeg.frostedheart.content.town.resource;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 此类用于玩家与城镇物品间的交互。
 * 在此类中，物品的数量仅支持整数，而非TownResourceHolder中的double类型。
 */
public class TownItemHandler implements IItemHandler {
    public final TownResourceHolder resourceHolder;
    /**
     * 这个列表仅仅用于展示，物品实际上存在于TownResourceManager中，对物品进行存取等操作时，同时对TownResourceManager进行对应的操作
     */
    private List<ItemStack> items;

    public TownItemHandler(TownResourceHolder resourceHolder){
        this.resourceHolder = resourceHolder;
        items = resourceHolder.getAllItems().entrySet().stream()
                .map(entry -> entry.getKey().copyWithCount((int)Math.floor(entry.getValue())))
                .toList();
    }

    public TownItemHandler(){
        this.resourceHolder = TownResourceHolder.EMPTY;
        items = List.of();
    }

    @Override
    public int getSlots() {
        //至少显示8*9=72个格子
        return Math.max(this.items.size(), 72);
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        if(slot>=items.size()) return ItemStack.EMPTY;
        return items.get(slot);
    }

    //slot will be ignored
    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if(stack.isEmpty()) return stack;
        //由于ItemStack只能为整数个，这里的capacityLeft被转换为整数
        //小数点部分会被忽略
        int capacityLeft = (int) Math.floor(resourceHolder.getCapacityLeft());
        if(capacityLeft>=stack.getCount()){
            if(!simulate){
                insertItem(stack, stack.getCount());
            }
            return ItemStack.EMPTY;
        }else{
            if(!simulate){
                insertItem(stack, capacityLeft);
            }
            return stack.copyWithCount(stack.getCount()-capacityLeft);
        }
    }

    //仅用于上面的insertItem方法，在其它地方使用可能引发问题。
    private void insertItem(ItemStack stack, int amount){
        resourceHolder.addUnsafe(stack, amount);
        boolean notInList = true;
        for(ItemStack listItemStack : items){
            if(ItemStack.isSameItemSameTags(listItemStack, stack)){
                listItemStack.grow(amount);
                notInList = false;
                break;
            }
        }
        if(notInList){
            items.add(stack.copyWithCount(amount));
        }
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0){
            return ItemStack.EMPTY;
        }
        if(slot>=items.size()){
            return ItemStack.EMPTY;
        }
        ItemStack slotStack = items.get(slot);
        int maxStackSize = slotStack.getItem().getMaxStackSize(slotStack);
        int toCost = Math.min(Math.min(amount, maxStackSize), slotStack.getCount());
        if(toCost == 0) return ItemStack.EMPTY;
        ItemStack costed = slotStack.copyWithCount(toCost);
        if(!simulate){
            this.resourceHolder.costUnsafe(slotStack, toCost);
            slotStack.shrink(toCost);
            if(slotStack.isEmpty()){
                items.remove(slot);
            }
        }
        return costed;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return resourceHolder.getCapacityLeft() > 1;
    }
}
