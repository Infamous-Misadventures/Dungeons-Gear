package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.SoulRobeItem;
import com.infamous.dungeons_gear.damagesources.SummonedFallingBlockDamageSource;
import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.items.ArmorList.CAVE_CRAWLER;
import static com.infamous.dungeons_gear.items.ArmorList.SPLENDID_ROBE;
import static com.infamous.dungeons_gear.items.ArtifactList.*;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ArtifactEvents {

    @SubscribeEvent
    public static void reAddSummonableGoals(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof LlamaEntity){
            LlamaEntity llamaEntity = (LlamaEntity) event.getEntity();
            ISummonable summonableCap = llamaEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonableCap.getSummoner() != null){
                llamaEntity.targetSelector.addGoal(1, new LlamaOwnerHurtByTargetGoal(llamaEntity));
                llamaEntity.targetSelector.addGoal(2, new LlamaOwnerHurtTargetGoal(llamaEntity));
                llamaEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(llamaEntity, LivingEntity.class, 5, false, false,
                        (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
                llamaEntity.goalSelector.addGoal(2, new LlamaFollowOwnerGoal(llamaEntity, 2.1D, 10.0F, 2.0F, false));
            }
        }
        if(event.getEntity() instanceof WolfEntity){
            WolfEntity wolfEntity = (WolfEntity) event.getEntity();
            ISummonable summonableCap = wolfEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonableCap.getSummoner() != null){
                wolfEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(wolfEntity, LivingEntity.class, 5, false, false,
                        (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
            }
        }
        if(event.getEntity() instanceof IronGolemEntity){
            IronGolemEntity ironGolemEntity = (IronGolemEntity) event.getEntity();
            ISummonable summonableCap = ironGolemEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonableCap.getSummoner() != null){
                ironGolemEntity.goalSelector.addGoal(2, new IronGolemFollowOwnerGoal(ironGolemEntity, 2.1D, 10.0F, 2.0F, false));

                ironGolemEntity.targetSelector.addGoal(1, new GolemOwnerHurtByTargetGoal(ironGolemEntity));
                ironGolemEntity.targetSelector.addGoal(2, new GolemOwnerHurtTargetGoal(ironGolemEntity));
            }
        }
        if(event.getEntity() instanceof BatEntity){
            BatEntity batEntity = (BatEntity) event.getEntity();
            ISummonable summonableCap = batEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonableCap.getSummoner() != null){
                batEntity.goalSelector.addGoal(1, new BatFollowOwnerGoal(batEntity, 2.1D, 10.0F, 2.0F, false));
                batEntity.goalSelector.addGoal(2, new BatMeleeAttackGoal(batEntity, 1.0D, true));

                batEntity.targetSelector.addGoal(1, new BatOwnerHurtByTargetGoal(batEntity));
                batEntity.targetSelector.addGoal(2, new BatOwnerHurtTargetGoal(batEntity));
                batEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(batEntity, LivingEntity.class, 5, false, false,
                        (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
            }
        }
        if(event.getEntity() instanceof BeeEntity){
            BeeEntity beeEntity = (BeeEntity) event.getEntity();
            ISummonable summonableCap = beeEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonableCap.getSummoner() != null){
                beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

                beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
                beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
                beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                        (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

            }
        }
    }

    @SubscribeEvent
    public static void onSummonedMobAttemptsToAttack(LivingSetAttackTargetEvent event){
        if(isEntitySummonable(event.getEntityLiving())){
            LivingEntity summonableAttacker = event.getEntityLiving();
            ISummonable attackerSummonableCap = summonableAttacker.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(attackerSummonableCap.getSummoner() != null){
                UUID attackersOwner = attackerSummonableCap.getSummoner();
                if(isEntitySummonable(event.getTarget())){
                    LivingEntity summonableTarget = event.getTarget();
                    ISummonable targetSummonableCap = summonableTarget.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
                    if(targetSummonableCap.getSummoner() != null){
                        UUID targetsOwner = targetSummonableCap.getSummoner();
                        if(targetsOwner.equals(attackersOwner)){
                            preventAttackForSummonableMob(summonableAttacker);
                        }
                    }
                }
            }
        }
        if(event.getTarget() instanceof PlayerEntity && isEntitySummonable(event.getEntityLiving())){
            LivingEntity summonableAttacker = event.getEntityLiving();
            ISummonable attackerSummonableCap = summonableAttacker.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(attackerSummonableCap.getSummoner() == event.getTarget().getUniqueID()){
                preventAttackForSummonableMob(summonableAttacker);
            }
        }
    }

    private static void preventAttackForSummonableMob(LivingEntity summonableAttacker) {
        if (summonableAttacker instanceof IronGolemEntity) {
            IronGolemEntity ironGolemEntity = (IronGolemEntity) summonableAttacker;
            ironGolemEntity.func_241356_K__();
        }
        if (summonableAttacker instanceof WolfEntity) {
            WolfEntity wolfEntity = (WolfEntity) summonableAttacker;
            wolfEntity.func_241356_K__();
        }
        if (summonableAttacker instanceof LlamaEntity) {
            LlamaEntity llamaEntity = (LlamaEntity) summonableAttacker;
            llamaEntity.setAttackTarget(null);
            llamaEntity.setRevengeTarget(null);
        }
        if (summonableAttacker instanceof BatEntity) {
            BatEntity batEntity = (BatEntity) summonableAttacker;
            batEntity.setAttackTarget(null);
            batEntity.setRevengeTarget(null);
        }
        if (summonableAttacker instanceof BeeEntity) {
            BeeEntity beeEntity = (BeeEntity) summonableAttacker;
            beeEntity.func_241356_K__();
        }
    }

    private static boolean isEntitySummonable(LivingEntity target) {
        return target instanceof IronGolemEntity
                || target instanceof WolfEntity
                || target instanceof LlamaEntity
                || target instanceof BatEntity
                || target instanceof BeeEntity;
    }

    @SubscribeEvent
    public static void onShielding(ProjectileImpactEvent event){
        if(event.getRayTraceResult() instanceof EntityRayTraceResult){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) event.getRayTraceResult();
            Entity entity = entityRayTraceResult.getEntity();
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) entity;
                Effect shielding = CustomEffects.SHIELDING;
                if(livingEntity.getActivePotionEffect(shielding) != null){
                    if(event.isCancelable()){
                        event.setCanceled(true);
                        if(event.getEntity() instanceof AbstractArrowEntity){
                            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getEntity();
                            ProjectileEffectHelper.ricochetArrowLikeShield(arrowEntity, livingEntity);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSouLProtection(LivingDeathEvent event){
        if(event.getEntityLiving().getActivePotionEffect(CustomEffects.SOUL_PROTECTION) != null){
            event.setCanceled(true);
            event.getEntityLiving().setHealth(1.0F);
            event.getEntityLiving().clearActivePotions();
            event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
            event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 900, 1));
            event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
        }
    }


    @SubscribeEvent
    public static void onArrowJoinWorld(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof AbstractArrowEntity){
            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getEntity();
            Entity shooter = arrowEntity.func_234616_v_();
            if(shooter instanceof PlayerEntity){
                PlayerEntity playerEntity = (PlayerEntity) shooter;
                ICombo comboCap = playerEntity.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
                if(comboCap.getFlamingArrowsCount() > 0){
                    int count = comboCap.getFlamingArrowsCount();
                    arrowEntity.setFire(100);
                    count--;
                    comboCap.setFlamingArrowsCount(count);
                }
                if(comboCap.getTormentArrowCount() > 0){
                    arrowEntity.addTag("TormentArrow");
                    int count = comboCap.getTormentArrowCount();
                    arrowEntity.setNoGravity(true);
                    arrowEntity.setMotion(arrowEntity.getMotion().scale(0.5D));
                    count--;
                    comboCap.setTormentArrowCount(count);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onTormentArrowImpact(ProjectileImpactEvent.Arrow event)  {

        AbstractArrowEntity arrowEntity = event.getArrow();
        Entity shooter = arrowEntity.func_234616_v_();

        if (!(shooter instanceof PlayerEntity))return;
        PlayerEntity player = (PlayerEntity) shooter;

        if (arrowEntity.getTags().contains("TormentArrow")){
            if (arrowEntity.ticksExisted > 1200){
                arrowEntity.remove();
                event.setCanceled(true);
            }

            if(event.getRayTraceResult() instanceof EntityRayTraceResult){
                EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) event.getRayTraceResult();
                Entity targetEntity = entityRayTraceResult.getEntity();
                if(!(targetEntity instanceof LivingEntity)){
                    event.setCanceled(true);
                }

                int currentKnockbackStrength = ObfuscationReflectionHelper.getPrivateValue(AbstractArrowEntity.class, arrowEntity, "field_70256_ap");
                (arrowEntity).setKnockbackStrength(currentKnockbackStrength + 1);
            }

            if(event.getRayTraceResult() instanceof BlockRayTraceResult)event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onIceBlockFalling(LivingDamageEvent event){
        if (event.getSource() instanceof SummonedFallingBlockDamageSource){
            if(event.getSource().getTrueSource() instanceof LivingEntity){
                LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                PROXY.spawnParticles(event.getEntityLiving(), ParticleTypes.ITEM_SNOWBALL);
                EffectInstance stun = new EffectInstance(CustomEffects.STUNNED, 100);
                EffectInstance nausea = new EffectInstance(Effects.NAUSEA, 100);
                EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 100, 4);
                event.getEntityLiving().addPotionEffect(stun);
                event.getEntityLiving().addPotionEffect(nausea);
                event.getEntityLiving().addPotionEffect(slowness);


                // Checks if the summoner has magic-damage boosting armor
                ItemStack chestplate = attacker.getItemStackFromSlot(EquipmentSlotType.CHEST);
                boolean magicBoostingChestplateFlag = chestplate.getItem() == SPLENDID_ROBE
                        || chestplate.getItem() instanceof SoulRobeItem
                        || chestplate.getItem() == CAVE_CRAWLER;
                float originalDamage = event.getAmount();
                if(magicBoostingChestplateFlag){
                    event.setAmount(originalDamage + originalDamage *0.5F);
                }
                // Finished checking summoner armor
            }
        }
    }

    // Avoids a situation where your summoned mob died but onSummonableDeath didn't fire in time,
    // making you unable to summon any more of that entity
    @SubscribeEvent
    public static void checkSummonedMobIsDead(TickEvent.PlayerTickEvent event){
        PlayerEntity summoner = event.player;

        if(event.phase == TickEvent.Phase.START) return;
        if(!summoner.isAlive()) return;

        ISummoner summonerCap = summoner.getCapability(SummonerProvider.SUMMONER_CAPABILITY).orElseThrow(IllegalStateException::new);
        if(summonerCap.getSummonedGolem() != null && event.player.world instanceof ServerWorld){
            UUID summonedGolem = summonerCap.getSummonedGolem();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedGolem);
            if(!(entity instanceof IronGolemEntity)) {
                summonerCap.setSummonedGolem(null);
                IArtifact.setArtifactCooldown(summoner, GOLEM_KIT, 600);
            }
        }
        if(summonerCap.getSummonedWolf() != null && event.player.world instanceof ServerWorld){
            UUID summonedWolf = summonerCap.getSummonedWolf();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedWolf);
            if(!(entity instanceof WolfEntity)) {
                summonerCap.setSummonedWolf(null);
                IArtifact.setArtifactCooldown(summoner, TASTY_BONE, 600);
            }
        }
        if(summonerCap.getSummonedLlama() != null && event.player.world instanceof ServerWorld){
            UUID summonedLlama = summonerCap.getSummonedLlama();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedLlama);
            if(!(entity instanceof LlamaEntity)) {
                summonerCap.setSummonedLlama(null);
                IArtifact.setArtifactCooldown(summoner, WONDERFUL_WHEAT, 600);
            }
        }
        if(summonerCap.getSummonedBat() != null && event.player.world instanceof ServerWorld){
            UUID summonedBat = summonerCap.getSummonedBat();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedBat);
            if(!(entity instanceof BatEntity)) {
                summonerCap.setSummonedBat(null);
            }
        }
        handleSummonableBeesAlreadyDead(event, summonerCap);
    }

    private static void handleSummonableBeesAlreadyDead(TickEvent.PlayerTickEvent event, ISummoner summonerCap) {
        for(int i = 0; i < 3; i++){
            UUID summonedBuzzyNestBee = summonerCap.getBuzzyNestBees()[i];
            UUID summonedTumblebeeBee = summonerCap.getTumblebeeBees()[i];
            UUID summonedBusyBeeBee = summonerCap.getBusyBeeBees()[i];
            if(summonedBuzzyNestBee != null && event.player.world instanceof ServerWorld){
                Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedBuzzyNestBee);
                if(!(entity instanceof BeeEntity)) {
                    summonerCap.getBuzzyNestBees()[i] = null;
                    if(!event.player.getCooldownTracker().hasCooldown(BUZZY_NEST)){
                        IArtifact.setArtifactCooldown(event.player, BUZZY_NEST, 460);
                    }
                }
            }
            if(summonedTumblebeeBee != null && event.player.world instanceof ServerWorld){
                Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedTumblebeeBee);
                if(!(entity instanceof BeeEntity)) {
                    summonerCap.getTumblebeeBees()[i] = null;
                }
            }
            if(summonedBusyBeeBee != null && event.player.world instanceof ServerWorld){
                Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedBusyBeeBee);
                if(!(entity instanceof BeeEntity)) {
                    summonerCap.getBusyBeeBees()[i] = null;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSummonableDeath(LivingDeathEvent event){
        if(isEntitySummonable(event.getEntityLiving())){
            LivingEntity livingEntity = event.getEntityLiving();
            World world = livingEntity.getEntityWorld();
            ISummonable summonableCap = livingEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonableCap.getSummoner() != null){
                PlayerEntity summoner = world.getPlayerByUuid(summonableCap.getSummoner());
                if(summoner != null){
                    ISummoner summonerCap = summoner.getCapability(SummonerProvider.SUMMONER_CAPABILITY).orElseThrow(IllegalStateException::new);
                    UUID summonableUUID = livingEntity.getUniqueID();

                    if(summonerCap.getSummonedGolem() == summonableUUID){
                        summonerCap.setSummonedGolem(null);
                        IArtifact.setArtifactCooldown(summoner, GOLEM_KIT, 600);
                    }
                    if(summonerCap.getSummonedWolf() == summonableUUID){
                        summonerCap.setSummonedWolf(null);
                        IArtifact.setArtifactCooldown(summoner, TASTY_BONE, 600);
                    }
                    if(summonerCap.getSummonedLlama() == summonableUUID){
                        summonerCap.setSummonedLlama(null);
                        IArtifact.setArtifactCooldown(summoner, WONDERFUL_WHEAT, 600);
                    }
                    if(summonerCap.getSummonedBat() == summonableUUID){
                        summonerCap.setSummonedBat(null);
                    }
                    for(int i = 0; i < 3; i++){
                        UUID buzzyNestBee = summonerCap.getBuzzyNestBees()[i];
                        UUID tumblebeeBee = summonerCap.getTumblebeeBees()[i];
                        UUID busyBeeBee = summonerCap.getBusyBeeBees()[i];
                        if(buzzyNestBee == summonableUUID){
                            summonerCap.getBuzzyNestBees()[i] = null;
                            if(!summoner.getCooldownTracker().hasCooldown(BUZZY_NEST)){
                                IArtifact.setArtifactCooldown(summoner, BUZZY_NEST, 460);
                            }
                        }
                        if(tumblebeeBee == summonableUUID){
                            summonerCap.getTumblebeeBees()[i] = null;
                        }
                        if(busyBeeBee == summonableUUID){
                            summonerCap.getBusyBeeBees()[i] = null;
                        }
                    }
                }
            }
        }
    }
}
