package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.armor.models.old_models.PhantomArmorModel;
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

public class PhantomArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public PhantomArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == ItemRegistry.PHANTOM_ARMOR.get() || stack.getItem() == ItemRegistry.PHANTOM_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/phantom_armor.png";
        }
        else if(stack.getItem() == ItemRegistry.FROST_BITE.get() || stack.getItem() == ItemRegistry.FROST_BITE_HELMET.get()){
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
    public boolean hasSnowballBuiltIn(ItemStack stack) {
        return this.unique;
    }

    @Override
    public Rarity getRarity(ItemStack itemStack){
        if(this.unique) return Rarity.RARE;
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
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
