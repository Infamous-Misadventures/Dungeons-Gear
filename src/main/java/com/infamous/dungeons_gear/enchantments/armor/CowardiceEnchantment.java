package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_libraries.attribute.AttributeRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CowardiceEnchantment extends HealthAbilityEnchantment {
    private static final UUID COWARD = UUID.fromString("86ded262-f5b3-41f9-a1ca-b881f6abfcff");

    public CowardiceEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        triggerEffect(player);
    }
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        triggerEffect(event.getEntityLiving());
    }

    private static void triggerEffect(LivingEntity livingEntity) {
        if (livingEntity.isAlive()) {
            float maxHealth = livingEntity.getMaxHealth();
            float currentHealth = livingEntity.getHealth();
            ModifiableAttributeInstance attackDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
            if(attackDamage == null) return;
            attackDamage.removeModifier(COWARD);
            livingEntity.getAttribute(AttributeRegistry.RANGED_DAMAGE_MULTIPLIER.get()).removeModifier(COWARD);
            if (currentHealth >= maxHealth) {
                if (ModEnchantmentHelper.hasEnchantment(livingEntity, ArmorEnchantmentList.COWARDICE)) {
                    int cowardiceLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.COWARDICE, livingEntity);
                    livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(COWARD, "cowardice multiplier", DungeonsGearConfig.COWARDICE_BASE_MULTIPLIER.get() + DungeonsGearConfig.COWARDICE_MULTIPLIER_PER_LEVEL.get() * cowardiceLevel, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    livingEntity.getAttribute(AttributeRegistry.RANGED_DAMAGE_MULTIPLIER.get()).addTransientModifier(new AttributeModifier(COWARD, "cowardice multiplier", DungeonsGearConfig.COWARDICE_BASE_MULTIPLIER.get() + DungeonsGearConfig.COWARDICE_MULTIPLIER_PER_LEVEL.get() * cowardiceLevel, AttributeModifier.Operation.MULTIPLY_TOTAL));
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
