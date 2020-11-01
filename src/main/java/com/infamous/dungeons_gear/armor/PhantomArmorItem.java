package com.infamous.dungeons_gear.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.models.PhantomArmorModel;
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

public class PhantomArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public PhantomArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == DeferredItemInit.PHANTOM_ARMOR.get() || stack.getItem() == DeferredItemInit.PHANTOM_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/phantom_armor.png";
        }
        else if(stack.getItem() == DeferredItemInit.FROST_BITE.get() || stack.getItem() == DeferredItemInit.FROST_BITE_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/frost_bite.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        return (A) new PhantomArmorModel<>(1.0F, slot, entityLiving, this.unique);
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
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Frost Bite remembers the icy winds which once flowed beneath the wings of the night Phantoms."));
            if(this.slot == EquipmentSlotType.CHEST){
                list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns A Snowy Companion (Snowball I)"));
            }
        }
        else{
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This armor, crafted from the bones of Phantoms, is a terrifying sight on the battlefield."));
        }
    }

    @Override
    public double getSoulsGathered() {
        return 50;
    }

    @Override
    public double getRangedDamage() {
        return 15;
    }
}
