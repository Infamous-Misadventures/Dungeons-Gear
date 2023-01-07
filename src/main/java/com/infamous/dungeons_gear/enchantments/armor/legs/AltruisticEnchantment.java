package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class AltruisticEnchantment extends DungeonsEnchantment {

    public AltruisticEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.ALTRUISTIC.get())) {
            int altruisticLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ALTRUISTIC.get(), livingEntity);
            float damage = event.getAmount();
            float damageToHealingMultiplier = DungeonsGearConfig.ALTRUISTIC_DAMAGE_TO_HEALING_PER_LEVEL.get().floatValue() * altruisticLevel;
            AreaOfEffectHelper.healNearbyAllies(livingEntity, damage * damageToHealingMultiplier, 12);
        }
    }
}
