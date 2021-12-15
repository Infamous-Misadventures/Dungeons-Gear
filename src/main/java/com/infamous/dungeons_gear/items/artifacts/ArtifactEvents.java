package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_libraries.capabilities.summoning.IMinion;
import com.infamous.dungeons_libraries.capabilities.summoning.IMaster;
import com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.utilties.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper.getMinionCapability;
import static com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper.getMasterCapability;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ArtifactEvents {

    public static final String FIRE_SHEEP_TAG = "FireSheep";
    public static final String POISON_SHEEP_TAG = "PoisonSheep";
    public static final String SPEED_SHEEP_TAG = "SpeedSheep";

    @SubscribeEvent
    public static void reAddSummonableGoals(EntityJoinWorldEvent event){
        if(MinionMasterHelper.isMinionEntity(event.getEntity())){
            IMinion summonableCap = getMinionCapability(event.getEntity());
            if(summonableCap == null) return;
            if(event.getEntity() instanceof LlamaEntity){
                LlamaEntity llamaEntity = (LlamaEntity) event.getEntity();
                if(summonableCap.getMaster() != null){
                    llamaEntity.targetSelector.addGoal(1, new LlamaOwnerHurtByTargetGoal(llamaEntity));
                    llamaEntity.targetSelector.addGoal(2, new LlamaOwnerHurtTargetGoal(llamaEntity));
                    llamaEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(llamaEntity, LivingEntity.class, 5, false, false,
                            (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
                    llamaEntity.goalSelector.addGoal(2, new LlamaFollowOwnerGoal(llamaEntity, 2.1D, 10.0F, 2.0F, false));
                }
            }
            if(event.getEntity() instanceof WolfEntity){
                WolfEntity wolfEntity = (WolfEntity) event.getEntity();
                if(summonableCap.getMaster() != null){
                    wolfEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(wolfEntity, LivingEntity.class, 5, false, false,
                            (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
                }
            }
            if(event.getEntity() instanceof IronGolemEntity){
                IronGolemEntity ironGolemEntity = (IronGolemEntity) event.getEntity();
                if(summonableCap.getMaster() != null){
                    ironGolemEntity.goalSelector.addGoal(2, new IronGolemFollowOwnerGoal(ironGolemEntity, 2.1D, 10.0F, 2.0F, false));

                    ironGolemEntity.targetSelector.addGoal(1, new GolemOwnerHurtByTargetGoal(ironGolemEntity));
                    ironGolemEntity.targetSelector.addGoal(2, new GolemOwnerHurtTargetGoal(ironGolemEntity));
                }
            }
            if(event.getEntity() instanceof BatEntity){
                BatEntity batEntity = (BatEntity) event.getEntity();
                if(summonableCap.getMaster() != null){
                    batEntity.goalSelector.addGoal(1, new BatMeleeAttackGoal(batEntity, 1.0D, true));
                    batEntity.goalSelector.addGoal(2, new BatFollowOwnerGoal(batEntity, 2.1D, 10.0F, 2.0F, false));

                    batEntity.targetSelector.addGoal(1, new BatOwnerHurtByTargetGoal(batEntity));
                    batEntity.targetSelector.addGoal(2, new BatOwnerHurtTargetGoal(batEntity));
                    batEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(batEntity, LivingEntity.class, 5, false, false,
                            (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
                }
            }
            if(event.getEntity() instanceof BeeEntity){
                BeeEntity beeEntity = (BeeEntity) event.getEntity();
                if(summonableCap.getMaster() != null){
                    beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

                    beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
                    beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
                    beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                            (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

                }
            }
            if(event.getEntity() instanceof SheepEntity){
                SheepEntity sheepEntity = (SheepEntity) event.getEntity();
                if(summonableCap.getMaster() != null){
                    if(sheepEntity.getTags().contains(FIRE_SHEEP_TAG) || sheepEntity.getTags().contains(POISON_SHEEP_TAG)){
                        sheepEntity.goalSelector.addGoal(1, new SheepMeleeAttackGoal(sheepEntity, 1.0D, true));

                        sheepEntity.targetSelector.addGoal(1, new SheepOwnerHurtByTargetGoal(sheepEntity));
                        sheepEntity.targetSelector.addGoal(2, new SheepOwnerHurtTargetGoal(sheepEntity));
                        sheepEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(sheepEntity, LivingEntity.class, 5, false, false,
                                (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
                    }
                    sheepEntity.goalSelector.addGoal(2, new SheepFollowOwnerGoal(sheepEntity, 2.1D, 10.0F, 2.0F, false));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEnchantedSheepAttack(LivingDamageEvent event){
        if(event.getSource().getEntity() instanceof SheepEntity){
            SheepEntity sheepEntity = (SheepEntity)event.getSource().getEntity();
            IMinion summonableCap = getMinionCapability(sheepEntity);
            if(summonableCap == null) return;
            if(summonableCap.getMaster() != null){
                if(sheepEntity.getTags().contains(FIRE_SHEEP_TAG)){
                    event.getEntityLiving().setSecondsOnFire(5);
                }
                else if(sheepEntity.getTags().contains(POISON_SHEEP_TAG)){
                    EffectInstance poison = new EffectInstance(Effects.POISON, 100);
                    event.getEntityLiving().addEffect(poison);
                }
            }
        }
    }

    @SubscribeEvent
    public static void updateBlueEnchantedSheep(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof SheepEntity){
            SheepEntity sheepEntity = (SheepEntity)event.getEntityLiving();
            IMinion summonableCap = getMinionCapability(sheepEntity);
            if(summonableCap == null) return;
            if(summonableCap.getMaster() != null){
                if(sheepEntity.level instanceof ServerWorld){
                    Entity summoner = ((ServerWorld) sheepEntity.level).getEntity(summonableCap.getMaster());
                    if(summoner instanceof PlayerEntity){
                        PlayerEntity playerEntity = (PlayerEntity)summoner;
                        if(!playerEntity.hasEffect(Effects.MOVEMENT_SPEED) && sheepEntity.getTags().contains(SPEED_SHEEP_TAG)){
                            EffectInstance speed = new EffectInstance(Effects.MOVEMENT_SPEED, 100);
                            playerEntity.addEffect(speed);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSummonedMobAttemptsToAttack(LivingSetAttackTargetEvent event){
        if(MinionMasterHelper.isMinionEntity(event.getEntityLiving())){
            LivingEntity summonableAttacker = event.getEntityLiving();
            IMinion attackerSummonableCap = getMinionCapability(summonableAttacker);
            if(attackerSummonableCap == null) return;
            if(attackerSummonableCap.getMaster() != null){
                UUID attackersOwner = attackerSummonableCap.getMaster();
                if(MinionMasterHelper.isMinionEntity(event.getTarget())){
                    LivingEntity summonableTarget = event.getTarget();
                    IMinion targetSummonableCap = getMinionCapability(summonableTarget);
                    if(targetSummonableCap == null) return;
                    if(targetSummonableCap.getMaster() != null){
                        UUID targetsOwner = targetSummonableCap.getMaster();
                        if(targetsOwner.equals(attackersOwner)){
                            preventAttackForSummonableMob(summonableAttacker);
                        }
                    }
                }
            }
        }
        if(event.getTarget() instanceof PlayerEntity && MinionMasterHelper.isMinionEntity(event.getEntityLiving())){
            LivingEntity summonableAttacker = event.getEntityLiving();
            IMinion attackerSummonableCap = getMinionCapability(summonableAttacker);
            if(attackerSummonableCap == null) return;
            if(attackerSummonableCap.getMaster() == event.getTarget().getUUID()){
                preventAttackForSummonableMob(summonableAttacker);
            }
        }
    }

    private static void preventAttackForSummonableMob(LivingEntity summonableAttacker) {
        if (summonableAttacker instanceof IronGolemEntity) {
            IronGolemEntity ironGolemEntity = (IronGolemEntity) summonableAttacker;
            ironGolemEntity.stopBeingAngry();
        }
        if (summonableAttacker instanceof WolfEntity) {
            WolfEntity wolfEntity = (WolfEntity) summonableAttacker;
            wolfEntity.stopBeingAngry();
        }
        if (summonableAttacker instanceof LlamaEntity) {
            LlamaEntity llamaEntity = (LlamaEntity) summonableAttacker;
            llamaEntity.setTarget(null);
            llamaEntity.setLastHurtByMob(null);
        }
        if (summonableAttacker instanceof BatEntity) {
            BatEntity batEntity = (BatEntity) summonableAttacker;
            batEntity.setTarget(null);
            batEntity.setLastHurtByMob(null);
        }
        if (summonableAttacker instanceof BeeEntity) {
            BeeEntity beeEntity = (BeeEntity) summonableAttacker;
            beeEntity.stopBeingAngry();
        }
        if (summonableAttacker instanceof SheepEntity) {
            SheepEntity llamaEntity = (SheepEntity) summonableAttacker;
            llamaEntity.setTarget(null);
            llamaEntity.setLastHurtByMob(null);
        }
    }

    @SubscribeEvent
    public static void onShielding(ProjectileImpactEvent event){
        if(event.getRayTraceResult() instanceof EntityRayTraceResult){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) event.getRayTraceResult();
            Entity entity = entityRayTraceResult.getEntity();
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) entity;
                Effect shielding = CustomEffects.SHIELDING;
                if(livingEntity.getEffect(shielding) != null){
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
        if(event.getEntityLiving().getEffect(CustomEffects.SOUL_PROTECTION) != null){
            event.setCanceled(true);
            event.getEntityLiving().setHealth(1.0F);
            event.getEntityLiving().removeAllEffects();
            event.getEntityLiving().addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
            event.getEntityLiving().addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 900, 1));
            event.getEntityLiving().addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
        }
    }



    @SubscribeEvent
    public static void onPartyStarterAttack(LivingAttackEvent event){
        if (PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) return;

        LivingEntity victim = event.getEntityLiving();
        Entity trueSource = event.getSource().getEntity();
        if(trueSource instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) trueSource;
            EffectInstance partyStarter = attacker.getEffect(CustomEffects.PARTY_STARTER);
            if (partyStarter != null) {
                int partyStarterLevel = partyStarter.getAmplifier() + 1;

                SoundHelper.playGenericExplodeSound(victim);
                if(!attacker.level.isClientSide){
                    float explosionDamage;
                    explosionDamage = victim.getMaxHealth() * 0.2F * partyStarterLevel;
                    AOECloudHelper.spawnExplosionCloud(attacker, victim, 3.0F);
                    AreaOfEffectHelper.causeExplosionAttack(attacker, victim, explosionDamage, 3.0F);

                }
                partyStarterLevel--;
                if(partyStarterLevel <= 0){
                    attacker.removeEffect(CustomEffects.PARTY_STARTER);
                } else{
                    partyStarter.amplifier = partyStarterLevel - 1;
                }
            }
        }
    }


    @SubscribeEvent
    public static void onArrowJoinWorld(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof AbstractArrowEntity){
            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getEntity();
            Entity shooter = arrowEntity.getOwner();
            if(shooter instanceof PlayerEntity){
                PlayerEntity playerEntity = (PlayerEntity) shooter;
                ICombo comboCap = CapabilityHelper.getComboCapability(playerEntity);
                if(comboCap == null) return;
                if(comboCap.getFlamingArrowsCount() > 0){
                    int count = comboCap.getFlamingArrowsCount();
                    arrowEntity.setSecondsOnFire(100);
                    count--;
                    comboCap.setFlamingArrowsCount(count);
                }
                if(comboCap.getTormentArrowsCount() > 0){
                    arrowEntity.addTag(TormentQuiverItem.TORMENT_ARROW);
                    int count = comboCap.getTormentArrowsCount();
                    arrowEntity.setNoGravity(true);
                    arrowEntity.setDeltaMovement(arrowEntity.getDeltaMovement().scale(0.5D));
                    count--;
                    comboCap.setTormentArrowCount(count);
                }
                if(comboCap.getThunderingArrowsCount() > 0){
                    arrowEntity.addTag(ThunderingQuiverItem.THUNDERING_ARROW);
                    int count = comboCap.getThunderingArrowsCount();
                    count--;
                    comboCap.setThunderingArrowsCount(count);
                }
                if(comboCap.getHarpoonCount() > 0){
                    arrowEntity.addTag(HarpoonQuiverItem.HARPOON_QUIVER);
                    int count = comboCap.getHarpoonCount();
                    count--;
                    comboCap.setHarpoonCount(count);
                    arrowEntity.setDeltaMovement(arrowEntity.getDeltaMovement().scale(1.5D));
                }
            }
        }
    }


    @SubscribeEvent
    public static void onSpecialArrowImpact(ProjectileImpactEvent.Arrow event)  {

        AbstractArrowEntity arrowEntity = event.getArrow();
        Entity shooter = arrowEntity.getOwner();

        if (!(shooter instanceof PlayerEntity))return;
        PlayerEntity player = (PlayerEntity) shooter;

        if (arrowEntity.getTags().contains(TormentQuiverItem.TORMENT_ARROW)){
            if (arrowEntity.tickCount > 1200){
                arrowEntity.remove();
                event.setCanceled(true);
            }

            if(event.getRayTraceResult() instanceof EntityRayTraceResult){
                EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) event.getRayTraceResult();
                Entity targetEntity = entityRayTraceResult.getEntity();
                if(!(targetEntity instanceof LivingEntity)){
                    event.setCanceled(true);
                }

                int currentKnockbackStrength = arrowEntity.knockback;
                (arrowEntity).setKnockback(currentKnockbackStrength + 1);
            }

            if(event.getRayTraceResult() instanceof BlockRayTraceResult)event.setCanceled(true);
        }
        if (arrowEntity.getTags().contains(ThunderingQuiverItem.THUNDERING_ARROW)){
            if(event.getRayTraceResult() instanceof EntityRayTraceResult){
                EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) event.getRayTraceResult();
                Entity targetEntity = entityRayTraceResult.getEntity();
                if(targetEntity instanceof LivingEntity){
                    SoundHelper.playLightningStrikeSounds(arrowEntity);
                    AreaOfEffectHelper.electrifyNearbyEnemies(arrowEntity, 5, 5, Integer.MAX_VALUE);
                }
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

        IMaster summonerCap = getMasterCapability(event.player);
        if(summonerCap == null) return;
        if(summonerCap.getSummonedGolem() != null && event.player.level instanceof ServerWorld){
            UUID summonedGolem = summonerCap.getSummonedGolem();
            Entity entity = ((ServerWorld) event.player.level).getEntity(summonedGolem);
            if(!(entity instanceof IronGolemEntity)) {
                summonerCap.setSummonedGolem(null);
                ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.GOLEM_KIT.get());
            }
        }
        if(summonerCap.getSummonedWolf() != null && event.player.level instanceof ServerWorld){
            UUID summonedWolf = summonerCap.getSummonedWolf();
            Entity entity = ((ServerWorld) event.player.level).getEntity(summonedWolf);
            if(!(entity instanceof WolfEntity)) {
                summonerCap.setSummonedWolf(null);
                ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.TASTY_BONE.get());
            }
        }
        if(summonerCap.getSummonedLlama() != null && event.player.level instanceof ServerWorld){
            UUID summonedLlama = summonerCap.getSummonedLlama();
            Entity entity = ((ServerWorld) event.player.level).getEntity(summonedLlama);
            if(!(entity instanceof LlamaEntity)) {
                summonerCap.setSummonedLlama(null);
                ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.WONDERFUL_WHEAT.get());
            }
        }
        if(summonerCap.getSummonedBat() != null && event.player.level instanceof ServerWorld){
            UUID summonedBat = summonerCap.getSummonedBat();
            Entity entity = ((ServerWorld) event.player.level).getEntity(summonedBat);
            if(!(entity instanceof BatEntity)) {
                summonerCap.setSummonedBat(null);
            }
        }
        if(summonerCap.getSummonedSheep() != null && event.player.level instanceof ServerWorld){
            UUID summonedSheep = summonerCap.getSummonedSheep();
            Entity entity = ((ServerWorld) event.player.level).getEntity(summonedSheep);
            if(!(entity instanceof SheepEntity)) {
                summonerCap.setSummonedSheep(null);
                ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.ENCHANTED_GRASS.get());
            }
        }
        handleSummonableBeesAlreadyDead(event, summonerCap);
    }

    private static void handleSummonableBeesAlreadyDead(TickEvent.PlayerTickEvent event, IMaster summonerCap) {
        for(int i = 0; i < 3; i++){
            UUID summonedBuzzyNestBee = summonerCap.getBuzzyNestBees()[i];
            UUID summonedTumblebeeBee = summonerCap.getTumblebeeBees()[i];
            UUID summonedBusyBeeBee = summonerCap.getBusyBeeBees()[i];
            if(summonedBuzzyNestBee != null && event.player.level instanceof ServerWorld){
                Entity entity = ((ServerWorld) event.player.level).getEntity(summonedBuzzyNestBee);
                if(!(entity instanceof BeeEntity)) {
                    summonerCap.getBuzzyNestBees()[i] = null;
                    if(summonerCap.hasNoBuzzyNestBees()){
                        ArtifactItem.putArtifactOnCooldown(event.player, ItemRegistry.BUZZY_NEST.get());
                    }
                }
            }
            if(summonedTumblebeeBee != null && event.player.level instanceof ServerWorld){
                Entity entity = ((ServerWorld) event.player.level).getEntity(summonedTumblebeeBee);
                if(!(entity instanceof BeeEntity)) {
                    summonerCap.getTumblebeeBees()[i] = null;
                }
            }
            if(summonedBusyBeeBee != null && event.player.level instanceof ServerWorld){
                Entity entity = ((ServerWorld) event.player.level).getEntity(summonedBusyBeeBee);
                if(!(entity instanceof BeeEntity)) {
                    summonerCap.getBusyBeeBees()[i] = null;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSummonableDeath(LivingDeathEvent event){
        if(MinionMasterHelper.isMinionEntity(event.getEntityLiving())){
            LivingEntity livingEntity = event.getEntityLiving();
            World world = livingEntity.getCommandSenderWorld();
            IMinion summonableCap = getMinionCapability(livingEntity);
            if(summonableCap == null) return;
            if(summonableCap.getMaster() != null){
                PlayerEntity summoner = world.getPlayerByUUID(summonableCap.getMaster());
                if(summoner != null){
                    IMaster summonerCap = getMasterCapability(summoner);
                    if(summonerCap == null) return;
                    UUID summonableUUID = livingEntity.getUUID();

                    if(summonerCap.getSummonedGolem() == summonableUUID){
                        summonerCap.setSummonedGolem(null);
                        ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.GOLEM_KIT.get());
                    }
                    if(summonerCap.getSummonedWolf() == summonableUUID){
                        summonerCap.setSummonedWolf(null);
                        ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.TASTY_BONE.get());
                    }
                    if(summonerCap.getSummonedLlama() == summonableUUID){
                        summonerCap.setSummonedLlama(null);
                        ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.WONDERFUL_WHEAT.get());
                    }
                    if(summonerCap.getSummonedBat() == summonableUUID){
                        summonerCap.setSummonedBat(null);
                    }
                    if(summonerCap.getSummonedSheep() == summonableUUID){
                        ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.ENCHANTED_GRASS.get());
                        summonerCap.setSummonedSheep(null);
                    }
                    for(int i = 0; i < 3; i++){
                        UUID buzzyNestBee = summonerCap.getBuzzyNestBees()[i];
                        UUID tumblebeeBee = summonerCap.getTumblebeeBees()[i];
                        UUID busyBeeBee = summonerCap.getBusyBeeBees()[i];
                        if(buzzyNestBee == summonableUUID){
                            summonerCap.getBuzzyNestBees()[i] = null;
                            if(!summoner.getCooldowns().isOnCooldown(ItemRegistry.BUZZY_NEST.get())){
                                ArtifactItem.putArtifactOnCooldown(summoner, ItemRegistry.BUZZY_NEST.get());
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

    // Preserves ownership of summoned mobs on respawn
    // Prevents problems like summoning an entirely new summon of the same type
    // as one you already have after you respawn
    @SubscribeEvent
    public static void cloneSummonerCaps(PlayerEvent.Clone event){
        IMaster oldSummonerCap = getMasterCapability(event.getOriginal());
        IMaster newSummonerCap = getMasterCapability(event.getPlayer());
        if (oldSummonerCap != null && newSummonerCap != null) {
            newSummonerCap.copyFrom(oldSummonerCap);
        }
    }
}
