package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.armor.models.old_models.CuriousArmorModel;
import com.infamous.dungeons_gear.items.armor.models.old_models.GuardsArmorModel;
import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
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

public class GuardsArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public GuardsArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == ItemRegistry.GUARDS_ARMOR.get() || stack.getItem() == ItemRegistry.GUARDS_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/guards_armor.png";
        }
        else if(stack.getItem() == ItemRegistry.CURIOUS_ARMOR.get() || stack.getItem() == ItemRegistry.CURIOUS_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/curious_armor.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == ItemRegistry.GUARDS_ARMOR.get() || stack.getItem() == ItemRegistry.GUARDS_ARMOR_HELMET.get()){
            return (A) new GuardsArmorModel<>(1.0F, slot, entityLiving);
        }
        if(stack.getItem() == ItemRegistry.CURIOUS_ARMOR.get() || stack.getItem() == ItemRegistry.CURIOUS_ARMOR_HELMET.get()){
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
        DescriptionHelper.addLoreDescription(list, stack);
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
