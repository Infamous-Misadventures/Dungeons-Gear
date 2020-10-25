package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.goals.BatFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.BatMeleeAttackGoal;
import com.infamous.dungeons_gear.goals.BatOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.BatOwnerHurtTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ArmorEffectHelper {
    public static void summonOrTeleportBat(PlayerEntity playerEntity, World world) {
        ISummoner summonerCap = playerEntity.getCapability(SummonerProvider.SUMMONER_CAPABILITY).orElseThrow(IllegalStateException::new);

        if(summonerCap.getSummonedBat() == null){
            BatEntity batEntity = EntityType.BAT.create(world);
            if (batEntity!= null) {
                batEntity.setLocationAndAngles((double)playerEntity.getPosX() + playerEntity.getEyeHeight(), (double)playerEntity.getPosY() + playerEntity.getEyeHeight(), (double)playerEntity.getPosZ() + playerEntity.getEyeHeight(), 0.0F, 0.0F);

                batEntity.goalSelector.addGoal(1, new BatFollowOwnerGoal(batEntity, 2.1D, 10.0F, 2.0F, false));
                batEntity.goalSelector.addGoal(2, new BatMeleeAttackGoal(batEntity, 1.0D, true));


                batEntity.targetSelector.addGoal(1, new BatOwnerHurtByTargetGoal(batEntity));
                batEntity.targetSelector.addGoal(2, new BatOwnerHurtTargetGoal(batEntity));
                batEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(batEntity, LivingEntity.class, 5, false, false,
                        (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

                world.playSound((PlayerEntity)null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_BAT_AMBIENT, SoundCategory.AMBIENT, 64.0F, 1.0F);
                world.addEntity(batEntity);
                ISummonable summonable = batEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);

                summonable.setSummoner(playerEntity.getUniqueID());

                summonerCap.setSummonedBat(batEntity.getUniqueID());
            }
        } else{
            if(world instanceof ServerWorld){
                Entity entity = ((ServerWorld)world).getEntityByUuid(summonerCap.getSummonedBat());
                if(entity instanceof BatEntity){
                    BatEntity batEntity = (BatEntity) entity;
                    batEntity.teleportKeepLoaded(playerEntity.getPosX() + playerEntity.getEyeHeight(), playerEntity.getPosY() + playerEntity.getEyeHeight(), playerEntity.getPosZ() + playerEntity.getEyeHeight());
                }
            }
        }
    }

    public static void teleportOnHit(LivingEntity livingEntity){
        World world = livingEntity.getEntityWorld();
        if (!world.isRemote) {
            double lvt_5_1_ = livingEntity.getPosX();
            double lvt_7_1_ = livingEntity.getPosY();
            double lvt_9_1_ = livingEntity.getPosZ();

            for(int i = 0; i < 16; ++i) {
                double teleportX = livingEntity.getPosX() + (livingEntity.getRNG().nextDouble() - 0.5D) * 16.0D;
                double teleportY = MathHelper.clamp(livingEntity.getPosY() + (double)(livingEntity.getRNG().nextInt(16) - 8), 0.0D, (double)(world.func_234938_ad_() - 1));
                double teleportZ = livingEntity.getPosZ() + (livingEntity.getRNG().nextDouble() - 0.5D) * 16.0D;
                if (livingEntity.isPassenger()) {
                    livingEntity.stopRiding();
                }

                if (livingEntity.attemptTeleport(teleportX, teleportY, teleportZ, true)) {
                    SoundEvent lvt_18_1_ = livingEntity instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    world.playSound((PlayerEntity)null, lvt_5_1_, lvt_7_1_, lvt_9_1_, lvt_18_1_, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    livingEntity.playSound(lvt_18_1_, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }
}
