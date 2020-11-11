package com.infamous.dungeons_gear.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.models.FrostArmorModel;
import com.infamous.dungeons_gear.armor.models.SnowArmorModel;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SnowArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public SnowArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == DeferredItemInit.SNOW_ARMOR.get() || stack.getItem() == DeferredItemInit.SNOW_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/snow_armor.png";
        }
        else if(stack.getItem() == DeferredItemInit.FROST_ARMOR.get() || stack.getItem() == DeferredItemInit.FROST_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/frost_armor.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == DeferredItemInit.SNOW_ARMOR.get() || stack.getItem() == DeferredItemInit.SNOW_ARMOR_HELMET.get()){
            return (A) new SnowArmorModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == DeferredItemInit.FROST_ARMOR.get() || stack.getItem() == DeferredItemInit.FROST_ARMOR_HELMET.get()){
            return (A) new FrostArmorModel<>(1.0F, slot, entityLiving);
        }
        return null;
    }

    @Override
    public Rarity getRarity(ItemStack itemStack){
        if(this.unique) return Rarity.RARE;
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (this.unique) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This legendary armor, forged from ice that never melts, makes the wearer feel as if they are one with winter."));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Emits a Chilling Aura"));
        }
        else{
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A suit of armor that was tempered in snowmelt, protecting the wearer from the harsh cold of the tundra."));
        }
    }

    @Override
    public double getFreezingResistance() {
        return 25;
    }
}
