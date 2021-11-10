package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.armor.models.old_models.*;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class EmeraldGearArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public EmeraldGearArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == ItemRegistry.EMERALD_GEAR.get() || stack.getItem() == ItemRegistry.EMERALD_GEAR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/emerald_gear.png";
        }
        else if(stack.getItem() == ItemRegistry.GILDED_GLORY.get() || stack.getItem() == ItemRegistry.GILDED_GLORY_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/gilded_glory.png";
        }
        else if(stack.getItem() == ItemRegistry.OPULENT_ARMOR.get() || stack.getItem() == ItemRegistry.OPULENT_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/opulent_armor.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == ItemRegistry.EMERALD_GEAR.get() || stack.getItem() == ItemRegistry.EMERALD_GEAR_HELMET.get()){
            return (A) new EmeraldArmorModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == ItemRegistry.GILDED_GLORY.get() || stack.getItem() == ItemRegistry.GILDED_GLORY_HELMET.get()){
            return (A) new GildedGloryModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == ItemRegistry.OPULENT_ARMOR.get() || stack.getItem() == ItemRegistry.OPULENT_ARMOR_HELMET.get()){
            return (A) new OpulentArmorModel<>(1.0F, slot, entityLiving);
        }
        return null;
    }


    @Override
    public Rarity getRarity(ItemStack itemStack){
        if(this.unique) return Rarity.RARE;
        return Rarity.UNCOMMON;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addLoreDescription(list, stack);
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean hasLuckyExplorerBuiltIn(ItemStack stack){return true;}

    @Override
    public boolean hasDeathBarterBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.GILDED_GLORY.get() || stack.getItem() == ItemRegistry.GILDED_GLORY_HELMET.get();
    }
    @Override
    public boolean hasOpulentShieldBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.OPULENT_ARMOR.get() || stack.getItem() == ItemRegistry.OPULENT_ARMOR_HELMET.get();
    }
}