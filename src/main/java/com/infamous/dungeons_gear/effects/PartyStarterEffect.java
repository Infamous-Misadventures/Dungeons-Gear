package com.infamous.dungeons_gear.effects;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class PartyStarterEffect extends MobEffect {
    public PartyStarterEffect(MobEffectCategory effectType, int liquidColorIn) {
        super(effectType, liquidColorIn);
    }

    @SubscribeEvent
    public static void onPartyStarterAttack(LivingAttackEvent event){
        if (PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) return;

        LivingEntity victim = event.getEntityLiving();
        Entity trueSource = event.getSource().getEntity();
        if(trueSource instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) trueSource;
            MobEffectInstance partyStarter = attacker.getEffect(CustomEffects.PARTY_STARTER);
            if (partyStarter != null) {
                int partyStarterLevel = partyStarter.getAmplifier();

                if(!attacker.level.isClientSide){
                    AOECloudHelper.spawnExplosionCloud(attacker, victim, 3.0F);
                    AreaOfEffectHelper.causeExplosionAttack(attacker, victim, DungeonsGearConfig.PARTY_STARTER_DAMAGE.get(), 3.0F);
                    SoundHelper.playGenericExplodeSound(victim);
                }
                partyStarterLevel--;
                if(partyStarterLevel <= 0){
                    attacker.removeEffect(CustomEffects.PARTY_STARTER);
                } else{
                    partyStarter.amplifier = partyStarterLevel;
                }
            }
        }
    }
}
