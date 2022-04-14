package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.capabilities.timers.ITimers;
import com.infamous.dungeons_libraries.capabilities.timers.TimersHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class ChillingEnchantment extends PulseEnchantment {

    public ChillingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
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
        PlayerEntity player = event.player;
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
        if(ModEnchantmentHelper.hasEnchantment(livingEntity, ArmorEnchantmentList.CHILLING)){
            ITimers timers = TimersHelper.getTimersCapability(livingEntity);
            if(timers == null) return;
            int currentTimer = timers.getEnchantmentTimer(ArmorEnchantmentList.CHILLING);
            if(currentTimer < 0) {
                timers.setEnchantmentTimer(ArmorEnchantmentList.CHILLING, 40);
            }else if(currentTimer == 0){
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.BURNING, livingEntity);
                AreaOfEffectHelper.freezeNearbyEnemies(livingEntity, enchantmentLevel - 1, 1.5F, 1);
                timers.setEnchantmentTimer(ArmorEnchantmentList.CHILLING, 40);
            }
        }
    }

}
