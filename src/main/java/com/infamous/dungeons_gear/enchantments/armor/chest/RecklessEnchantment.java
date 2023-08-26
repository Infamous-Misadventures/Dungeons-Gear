package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class RecklessEnchantment extends DungeonsEnchantment {
    private static final UUID RECKLESS = UUID.fromString("c131aecf-3b88-43c9-9df3-16f791077d41");

    public RecklessEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEquipmentChangeEvent event) {
        //no handsies!
        if (event.getSlot().getType() == EquipmentSlot.Type.HAND) return;
        LivingEntity livingEntity = event.getEntity();
        int levelFrom = event.getFrom().getEnchantmentLevel(EnchantmentInit.RECKLESS.get());
        int levelTo = event.getTo().getEnchantmentLevel(EnchantmentInit.RECKLESS.get());
        if (levelFrom == levelTo) return;
        if (levelFrom == 0) {
            livingEntity.getAttribute(Attributes.MAX_HEALTH).removeModifier(RECKLESS);
            livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RECKLESS);
        }
        if (levelTo > 0) {
            livingEntity.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier(RECKLESS, "reckless multiplier", DungeonsGearConfig.RECKLESS_MAX_HEALTH_MULTIPLIER.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(RECKLESS, "reckless multiplier", DungeonsGearConfig.RECKLESS_ATTACK_DAMAGE_BASE_MULTIPLIER.get() + (DungeonsGearConfig.RECKLESS_ATTACK_DAMAGE_MULTIPLIER_PER_LEVEL.get() * levelTo), AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
