package com.infamous.dungeons_gear.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.models.SoulRobeModel;
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

import static com.infamous.dungeons_gear.items.ArmorList.*;

public class SoulRobeItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public SoulRobeItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(this.unique) return DungeonsGear.MODID + ":textures/models/armor/souldancer_robe.png";
        return DungeonsGear.MODID + ":textures/models/armor/soul_robe.png";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == SOUL_ROBE || stack.getItem() == SOUL_ROBE_HOOD){
            return (A) new SoulRobeModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == SOULDANCER_ROBE || stack.getItem() == SOULDANCER_ROBE_HOOD){
            return (A) new SoulRobeModel<>(1.0F, slot, entityLiving);
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
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Souldancer Robe comes alive in the light, as if the souls within are dancing for all eternity."));
        }
        else{
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "In a particular light, the souls woven into the cloth of the Soul Robe shimmer with power."));
        }
    }

    @Override
    public double getChanceToNegateHits() {
        if(this.unique) return 15;
        else return 0;
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
