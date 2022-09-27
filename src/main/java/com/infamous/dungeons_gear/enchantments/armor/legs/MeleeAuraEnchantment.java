package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_libraries.utils.AbilityHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.MELEE_AURA;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;

@Mod.EventBusSubscriber(modid= MODID)
public class MeleeAuraEnchantment extends PulseEnchantment {

    public MeleeAuraEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_LEGS, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 2;
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
        if(player.isAlive()&&player.isEffectiveAi()){
            apply(player);
        }
    }

    @SubscribeEvent
    public static void onLivingEntityTick(LivingEvent.LivingUpdateEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        if(livingEntity == null || livingEntity instanceof PlayerEntity) return;
        if(livingEntity.isAlive() && livingEntity.isEffectiveAi()){
            apply(livingEntity);
        }
    }

    private static void apply(LivingEntity entity) {
//        ICombo comboCap = CapabilityHelper.getComboCapability(entity);
//        if(comboCap == null) return;
//        int burnNearbyTimer = comboCap.getBurnNearbyTimer();

        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(MELEE_AURA, entity);
        if(enchantmentLevel > 0){
//            if(burnNearbyTimer <= 0){
            applyToNearbyEntities(entity, 5,
                    (nearbyEntity) -> {
                        return AbilityHelper.isAlly(entity, nearbyEntity);
                    }, (LivingEntity nearbyEntity) -> {
                        EffectInstance speedBoost = new EffectInstance(Effects.DAMAGE_BOOST, 20, enchantmentLevel - 1);
                        nearbyEntity.addEffect(speedBoost);
//                        PROXY.spawnParticles(nearbyEntity, ParticleTypes.FLAME);
                    }
            );
//                comboCap.setBurnNearbyTimer(10);
        }
//        else{
//            if(burnNearbyTimer != 10){
//                comboCap.setBurnNearbyTimer(10);
//            }
//        }
    }

}
