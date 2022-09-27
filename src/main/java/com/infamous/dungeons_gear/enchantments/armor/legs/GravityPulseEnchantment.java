package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.capabilities.timers.Timers;
import com.infamous.dungeons_libraries.capabilities.timers.TimersHelper;
import net.minecraft.core.particles.ParticleTypes;
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

@Mod.EventBusSubscriber(modid= MODID)
public class GravityPulseEnchantment extends PulseEnchantment {

    public GravityPulseEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof PulseEnchantment);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        if(player == null || player.isSpectator()) return;
        if(event.phase == TickEvent.Phase.START) return;
        if(player.isAlive() && player.isEffectiveAi()){
            triggerEffect(player);
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        triggerEffect(event.getEntityLiving());
    }

    public static void triggerEffect(LivingEntity livingEntity){
        if(ModEnchantmentHelper.hasEnchantment(livingEntity, ArmorEnchantmentList.GRAVITY_PULSE)){
            Timers timers = TimersHelper.getTimersCapability(livingEntity);
            if(timers == null) return;
            int currentTimer = timers.getEnchantmentTimer(ArmorEnchantmentList.GRAVITY_PULSE);
            if(currentTimer < 0) {
                timers.setEnchantmentTimer(ArmorEnchantmentList.GRAVITY_PULSE, 100);
            }else if(currentTimer == 0){
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.GRAVITY_PULSE, livingEntity);
                AreaOfEffectHelper.pullInNearbyEntities(livingEntity, livingEntity, (float) (DungeonsGearConfig.GRAVITY_PULSE_BASE_STRENGTH.get() + DungeonsGearConfig.GRAVITY_PULSE_STRENGTH_PER_LEVEL.get() * enchantmentLevel), ParticleTypes.PORTAL);
                timers.setEnchantmentTimer(ArmorEnchantmentList.GRAVITY_PULSE, 100);
            }
        }
    }
}
