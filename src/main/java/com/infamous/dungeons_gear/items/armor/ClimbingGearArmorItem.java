package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.armor.models.old_models.ClimbingGearModel;
import com.infamous.dungeons_gear.items.armor.models.old_models.GoatGearModel;
import com.infamous.dungeons_gear.items.armor.models.old_models.RuggedClimbingGearModel;
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

import static com.infamous.dungeons_gear.registry.ItemRegistry.*;

public class ClimbingGearArmorItem extends ArmorItem implements IArmor {
    private final boolean unique;

    public ClimbingGearArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(stack.getItem() == ItemRegistry.CLIMBING_GEAR.get() || stack.getItem() == ItemRegistry.CLIMBING_GEAR_HOOD.get()){
            return DungeonsGear.MODID + ":textures/models/armor/climbing_gear.png";
        }
        else if(stack.getItem() == RUGGED_CLIMBING_GEAR.get() || stack.getItem() == ItemRegistry.RUGGED_CLIMBING_GEAR_HOOD.get()){
            return DungeonsGear.MODID + ":textures/models/armor/rugged_climbing_gear.png";
        }
        else if(stack.getItem() == ItemRegistry.GOAT_GEAR.get() || stack.getItem() == ItemRegistry.GOAT_GEAR_HOOD.get()){
            return DungeonsGear.MODID + ":textures/models/armor/goat_gear.png";
        }
        else return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == ItemRegistry.CLIMBING_GEAR.get() || stack.getItem() == ItemRegistry.CLIMBING_GEAR_HOOD.get()){
            return (A) new ClimbingGearModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == RUGGED_CLIMBING_GEAR.get() || stack.getItem() == ItemRegistry.RUGGED_CLIMBING_GEAR_HOOD.get()){
            return (A) new RuggedClimbingGearModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == ItemRegistry.GOAT_GEAR.get() || stack.getItem() == ItemRegistry.GOAT_GEAR_HOOD.get()){
            return (A) new GoatGearModel<>(1.0F, slot, entityLiving);
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
    public double getArtifactCooldown() {
        return 12.5D;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.75F;
    }

    @Override
    public double getFreezingResistance() {
        if(this == RUGGED_CLIMBING_GEAR.get() || this == ItemRegistry.RUGGED_CLIMBING_GEAR_HOOD.get()) {
            return 25;
        }
        return 0;
    }

    @Override
    public boolean hasMultiRollBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.GOAT_GEAR.get() || stack.getItem() == ItemRegistry.GOAT_GEAR_HOOD.get();
    }

    @Override
    public boolean hasEnvironmentalProtectionBuiltIn(ItemStack stack) {
        return stack.getItem() == RUGGED_CLIMBING_GEAR.get() || stack.getItem() == ItemRegistry.RUGGED_CLIMBING_GEAR_HOOD.get();
    }
}
