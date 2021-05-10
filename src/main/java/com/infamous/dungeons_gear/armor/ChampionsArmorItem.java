package com.infamous.dungeons_gear.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.models.ChampionsArmorModel;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IArmor;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ChampionsArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public ChampionsArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == ItemRegistry.CHAMPIONS_ARMOR.get() || stack.getItem() == ItemRegistry.CHAMPIONS_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/champions_armor.png";
        }
        else if(stack.getItem() == ItemRegistry.HEROS_ARMOR.get() || stack.getItem() == ItemRegistry.HEROS_ARMOR_HELMET.get()){
            return DungeonsGear.MODID + ":textures/models/armor/heros_armor.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == ItemRegistry.CHAMPIONS_ARMOR.get() || stack.getItem() == ItemRegistry.CHAMPIONS_ARMOR_HELMET.get()){
            return (A) new ChampionsArmorModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == ItemRegistry.HEROS_ARMOR.get() || stack.getItem() == ItemRegistry.HEROS_ARMOR_HELMET.get()){
            return (A) new ChampionsArmorModel<>(1.0F, slot, entityLiving);
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
        if (this.unique) {
            list.add(new TranslationTextComponent(
                    "attribute.name.healthPotionsHealNearbyAllies")
                    .mergeStyle(TextFormatting.GREEN));
        }
    }

    @Override
    public boolean doHealthPotionsHealNearbyAllies() {
        return this.unique;
    }

    @Override
    public double getHealthPotionBoost() {
        return 1;
    }
}
