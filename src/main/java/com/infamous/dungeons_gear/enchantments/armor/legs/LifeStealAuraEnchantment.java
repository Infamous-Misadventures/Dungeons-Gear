package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.registry.MobEffectInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.AbilityHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.registry.EnchantmentInit.LIFE_STEAL_AURA;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;

@Mod.EventBusSubscriber(modid = MODID)
public class LifeStealAuraEnchantment extends PulseEnchantment {

    public LifeStealAuraEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof PulseEnchantment);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (ModEnchantmentHelper.canEnchantmentTrigger(event.getEntity())) {
            triggerEffect(event.getEntity());
        }
    }

    private static void triggerEffect(LivingEntity entity) {

        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(LIFE_STEAL_AURA.get(), entity);
        if (enchantmentLevel > 0) {
            applyToNearbyEntities(entity, 5,
                    (nearbyEntity) -> {
                        return AbilityHelper.isAlly(entity, nearbyEntity);
                    }, (LivingEntity nearbyEntity) -> {
                        MobEffectInstance speedBoost = new MobEffectInstance(MobEffectInit.LIFE_STEAL.get(), 20, enchantmentLevel - 1);
                        nearbyEntity.addEffect(speedBoost);
                    }
            );
        }
    }

}
