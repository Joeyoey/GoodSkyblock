package io.github.goodskyblock.utilobjects;

import org.bukkit.inventory.ItemStack;

public class FakeBlock {

    private int mat;
    private Byte data;
    private boolean chest;
    private ItemStack[] itemStacks;


    public FakeBlock(int mat, Byte data, ItemStack[] itemStacks) {
        this.mat = mat;
        this.data = data;
        this.chest = true;
        this.itemStacks = itemStacks;
    }

    public FakeBlock(int mat, Byte data) {
        this.mat = mat;
        this.data = data;
        this.chest = false;
    }


    public int getMat() {
        return this.mat;
    }

    public boolean isChest() {
        return this.chest;
    }

    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }

    public Byte getData() {
        return this.data;
    }

}
