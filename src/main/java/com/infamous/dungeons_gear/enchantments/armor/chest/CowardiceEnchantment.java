package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.attribute.AttributeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class CowardiceEnchantment extends HealthAbilityEnchantment {
    private static final UUID COWARD = UUID.fromString("86ded262-f5b3-41f9-a1ca-b881f6abfcff");

    public CowardiceEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        triggerEffect(player);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        triggerEffect(event.getEntity());
    }

    private static void triggerEffect(LivingEntity livingEntity) {
        if (livingEntity.isAlive()) {
            float maxHealth = livingEntity.getMaxHealth();
            float currentHealth = livingEntity.getHealth();
            AttributeInstance attackDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeInstance rangedDamage = livingEntity.getAttribute(AttributeRegistry.RANGED_DAMAGE_MULTIPLIER.get());
            if (attackDamage != null) {
                attackDamage.removeModifier(COWARD);
            }
            if (rangedDamage != null) {
                rangedDamage.removeModifier(COWARD);
            }
            if (currentHealth >= maxHealth) {
                if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.COWARDICE.get())) {
                    int cowardiceLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.COWARDICE.get(), livingEntity);
                    if (attackDamage != null) {
                        livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(COWARD, "cowardice multiplier", DungeonsGearConfig.COWARDICE_BASE_MULTIPLIER.get() + DungeonsGearConfig.COWARDICE_MULTIPLIER_PER_LEVEL.get() * cowardiceLevel, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (rangedDamage != null) {
                        livingEntity.getAttribute(AttributeRegistry.RANGED_DAMAGE_MULTIPLIER.get()).addTransientModifier(new AttributeModifier(COWARD, "cowardice multiplier", DungeonsGearConfig.COWARDICE_BASE_MULTIPLIER.get() + DungeonsGearConfig.COWARDICE_MULTIPLIER_PER_LEVEL.get() * cowardiceLevel, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                }
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealthAbilityEnchantment);
    }
}
