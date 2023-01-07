package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_libraries.capabilities.timers.Timers;
import com.infamous.dungeons_libraries.capabilities.timers.TimersHelper;
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

@Mod.EventBusSubscriber(modid = MODID)
public class SnowballEnchantment extends PulseEnchantment {

    public SnowballEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player == null || player.isSpectator()) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive() && player.isEffectiveAi()) {
            triggerEffect(player);
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        triggerEffect(event.getEntity());
    }

    public static void triggerEffect(LivingEntity livingEntity) {
        if (ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.SNOWBALL.get())) {
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SNOWBALL.get(), livingEntity);
            Timers timers = TimersHelper.getTimersCapability(livingEntity);
            if (timers == null) return;
            int currentTimer = timers.getEnchantmentTimer(EnchantmentInit.SNOWBALL.get());
            if (currentTimer < 0) {
                timers.setEnchantmentTimer(EnchantmentInit.SNOWBALL.get(), Math.max(100 - (enchantmentLevel - 1) * 40, 20));
            } else if (currentTimer == 0) {
                ProjectileEffectHelper.fireSnowballAtNearbyEnemy(livingEntity, 10);
                timers.setEnchantmentTimer(EnchantmentInit.SNOWBALL.get(), Math.max(100 - (enchantmentLevel - 1) * 40, 20));
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof PulseEnchantment);
    }
}
