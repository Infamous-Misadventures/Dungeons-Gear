package com.infamous.dungeons_gear.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.models.CuriousArmorModel;
import com.infamous.dungeons_gear.armor.models.GuardsArmorModel;
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

public class GuardsArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public GuardsArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == DeferredItemInit.GUARDS_ARMOR.get() || stack.getItem() == DeferredItemInit.GUARDS_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/guards_armor.png";
        }
        else if(stack.getItem() == DeferredItemInit.CURIOUS_ARMOR.get() || stack.getItem() == DeferredItemInit.CURIOUS_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/curious_armor.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == DeferredItemInit.GUARDS_ARMOR.get() || stack.getItem() == DeferredItemInit.GUARDS_ARMOR_HELMET.get()){
            return (A) new GuardsArmorModel<>(1.0F, slot, entityLiving);
        }
        if(stack.getItem() == DeferredItemInit.CURIOUS_ARMOR.get() || stack.getItem() == DeferredItemInit.CURIOUS_ARMOR_HELMET.get()){
            return (A) new CuriousArmorModel<>(1.0F, slot, entityLiving);
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
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "No one knows where this strange armor came from but it seems familiar to you."));
        }
        else{
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Cheap armor made in bulk, the Guard's Armor is a common sight in the villages of the Overworld."));

        }
    }

    @Override
    public double getChanceToTeleportAwayWhenHit() {
        if(this.unique) return 2.5D;
        else return 0;
    }

    @Override
    public double getArtifactCooldown() {
        return 12.5D;
    }

    @Override
    public int getArrowsPerBundle() {
        return 4; // 8 out of 64
    }
}
