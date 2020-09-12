package com.infamous.dungeons_gear.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.models.ChampionsArmorModel;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static com.infamous.dungeons_gear.items.ArmorList.*;

public class ChampionsArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public ChampionsArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(this.unique) return DungeonsGear.MODID + ":textures/models/armor/heros_armor.png";
        return DungeonsGear.MODID + ":textures/models/armor/champions_armor.png";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == CHAMPIONS_ARMOR || stack.getItem() == CHAMPIONS_ARMOR_HELMET){
            return (A) new ChampionsArmorModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == HEROS_ARMOR || stack.getItem() == HEROS_ARMOR_HELMET){
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

        if (this.unique) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Given to the champions of the Overworld by the free villagers as a token of their thanks for many years of protection and assistance. The mark of a true hero."));
        }
        else{
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Only given to the highest caliber of heroes, the Hero's Armor is a precious gift from the Villagers."));

        }
        list.add(new TranslationTextComponent(
                "attribute.name.healthPotionsHealNearbyAllies")
                .func_240701_a_(TextFormatting.GREEN));
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
