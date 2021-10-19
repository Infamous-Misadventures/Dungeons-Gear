package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.armor.models.old_models.SoulRobeModel;
import com.infamous.dungeons_gear.registry.ItemRegistry;
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

import net.minecraft.item.Item.Properties;

public class SoulRobeItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public SoulRobeItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == ItemRegistry.SOUL_ROBE.get() || stack.getItem() == ItemRegistry.SOUL_ROBE_HOOD.get()){
            return DungeonsGear.MODID + ":textures/models/armor/soul_robe.png";
        }
        else if(stack.getItem() == ItemRegistry.SOULDANCER_ROBE.get() || stack.getItem() == ItemRegistry.SOULDANCER_ROBE_HOOD.get()){
            return DungeonsGear.MODID + ":textures/models/armor/souldancer_robe.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        return (A) new SoulRobeModel<>(1.0F, slot, entityLiving);
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
    public double getChanceToNegateHits() {
        if(this.unique) return 15;
        else return 0;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public double getSoulsGathered() {
        return 50;
    }

    @Override
    public double getMagicDamage() {
        return 25;
    }
}
