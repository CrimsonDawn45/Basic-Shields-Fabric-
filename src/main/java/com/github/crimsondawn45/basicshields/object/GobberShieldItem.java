package com.github.crimsondawn45.basicshields.object;

import java.util.List;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class GobberShieldItem extends FabricBannerShieldItem {

    private boolean unbreakable;
    private float reflectPercentage;

    public GobberShieldItem(Settings settings, int cooldownTicks, int enchantability, float reflectPercentage, boolean unbreakable, Item... repairItems) {
        super(settings, cooldownTicks, enchantability, repairItems);
        this.unbreakable = unbreakable;
        this.reflectPercentage = reflectPercentage;
    }

    public GobberShieldItem(Settings settings, int cooldownTicks, int enchantability, float reflectPercentage, boolean unbreakable, TagKey<Item> repairItemTag) {
        super(settings, cooldownTicks, enchantability, repairItemTag);
        this.unbreakable = unbreakable;
        this.reflectPercentage = reflectPercentage;
    }

    public GobberShieldItem(Settings settings, int cooldownTicks, int enchantability, float reflectPercentage, boolean unbreakable, ToolMaterial repairMaterial) {
        super(settings, cooldownTicks, repairMaterial);
        this.unbreakable = unbreakable;
        this.reflectPercentage = reflectPercentage;
    }

    public GobberShieldItem(Settings settings, int cooldownTicks, int enchantability, float reflectPercentage, boolean unbreakable, List<TagKey<Item>> repairItemTags) {
        super(settings, cooldownTicks, enchantability, repairItemTags);
        this.unbreakable = unbreakable;
        this.reflectPercentage = reflectPercentage;
    }

    //Add reflection tooltip
    @Override
    public void appendShieldTooltip(ItemStack stack, List<Text> tooltip, TooltipContext context) {

        //TODO: Fix this stuff later
        //tooltip.add(new TranslatableTextContent("item.basicshields.gobber.tooltip.start").append(new LiteralMessage(String.valueOf(this.reflectPercentage * 100))).append(new TranslatableText("item.basicshields.gobber.tooltip.end")).formatted(Formatting.GREEN));
    }

    //Make Unbreakable
    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) 
	{
		if(world.isClient) return;

		if(unbreakable)
		{
			stack.getOrCreateNbt().putBoolean("Unbreakable", true);
		}
	}

    public float getReflectPercentage() {
        return this.reflectPercentage;
    }
}