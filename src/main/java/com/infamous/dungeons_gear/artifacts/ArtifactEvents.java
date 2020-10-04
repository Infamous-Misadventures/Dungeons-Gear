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
import com.infamous.dungeons_gear.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.EnchantUtils;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.utilties.ProjectileEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
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
                llamaEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(llamaEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
                    return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
                }));
                llamaEntity.goalSelector.addGoal(2, new LlamaFollowOwnerGoal(llamaEntity, 2.1D, 10.0F, 2.0F, false));
            }
        }
        if(event.getEntity() instanceof WolfEntity){
            WolfEntity wolfEntity = (WolfEntity) event.getEntity();
            ISummonable summonableCap = wolfEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonableCap.getSummoner() != null){
                wolfEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(wolfEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
                    return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
                }));
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
                batEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(batEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
                    return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
                }));
            }
        }
    }

    @SubscribeEvent
    public static void onSummonedMobAttemptsToAttack(LivingSetAttackTargetEvent event){
        if(event.getEntityLiving() instanceof IronGolemEntity
            || event.getEntityLiving() instanceof WolfEntity
            || event.getEntityLiving() instanceof LlamaEntity){
            LivingEntity summonableMob = event.getEntityLiving();
            ISummonable attackerSummonableCap = summonableMob.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(attackerSummonableCap.getSummoner() != null){
                UUID attackersOwner = attackerSummonableCap.getSummoner();
                if(event.getTarget() instanceof IronGolemEntity
                        || event.getTarget() instanceof WolfEntity
                        || event.getTarget() instanceof LlamaEntity){
                    LivingEntity summonableTarget = event.getTarget();
                    ISummonable targetSummonableCap = summonableTarget.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
                    if(targetSummonableCap.getSummoner() != null){
                        UUID targetsOwner = targetSummonableCap.getSummoner();
                        if(targetsOwner.equals(attackersOwner)){
                            if(summonableMob instanceof IronGolemEntity){
                                IronGolemEntity ironGolemEntity = (IronGolemEntity) summonableMob;
                                ironGolemEntity.setAttackTarget(null);
                                ironGolemEntity.setRevengeTarget(null);
                            }
                            if(summonableMob instanceof WolfEntity){
                                WolfEntity wolfEntity = (WolfEntity) summonableMob;
                                wolfEntity.setAttackTarget(null);
                                wolfEntity.setRevengeTarget(null);
                            }
                            if(summonableMob instanceof LlamaEntity){
                                LlamaEntity llamaEntity = (LlamaEntity) summonableMob;
                                llamaEntity.setAttackTarget(null);
                                llamaEntity.setRevengeTarget(null);
                            }
                            if(summonableMob instanceof BatEntity){
                                BatEntity batEntity = (BatEntity) summonableMob;
                                batEntity.setAttackTarget(null);
                                batEntity.setRevengeTarget(null);
                            }
                        }
                    }
                }
            }
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
                if(livingEntity.getActivePotionEffect(shielding) != null){
                    if(event.isCancelable()){
                        event.setCanceled(true);
                        if(event.getEntity() instanceof AbstractArrowEntity){
                            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getEntity();
                            ProjectileEffects.ricochetArrowLikeShield(arrowEntity, livingEntity);
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
                int currentKnockbackStrength = arrowEntity.knockbackStrength;
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
        if(summonerCap.getSummonedGolem() != null){
            UUID summonedGolem = summonerCap.getSummonedGolem();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedGolem);
            if(!(entity instanceof IronGolemEntity)) {
                summonerCap.setSummonedGolem(null);
                setArtifactCooldown(summoner, GOLEM_KIT, 600);
            }
        }
        if(summonerCap.getSummonedWolf() != null){
            UUID summonedWolf = summonerCap.getSummonedWolf();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedWolf);
            if(!(entity instanceof WolfEntity)) {
                summonerCap.setSummonedWolf(null);
                setArtifactCooldown(summoner, TASTY_BONE, 600);
            }
        }
        if(summonerCap.getSummonedLlama() != null){
            UUID summonedLlama = summonerCap.getSummonedLlama();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedLlama);
            if(!(entity instanceof LlamaEntity)) {
                summonerCap.setSummonedLlama(null);
                setArtifactCooldown(summoner, WONDERFUL_WHEAT, 600);
            }
        }
        if(summonerCap.getSummonedBat() != null){
            UUID summonedBat = summonerCap.getSummonedBat();
            Entity entity = ((ServerWorld) event.player.world).getEntityByUuid(summonedBat);
            if(!(entity instanceof BatEntity)) {
                summonerCap.setSummonedBat(null);
            }
        }
    }

    @SubscribeEvent
    public static void onSummonableDeath(LivingDeathEvent event){
        if(event.getEntityLiving() instanceof IronGolemEntity
            || event.getEntityLiving() instanceof WolfEntity
            || event.getEntityLiving() instanceof LlamaEntity
                || event.getEntityLiving() instanceof BatEntity){
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
                        setArtifactCooldown(summoner, GOLEM_KIT, 600);
                    }
                    if(summonerCap.getSummonedWolf() == summonableUUID){
                        summonerCap.setSummonedWolf(null);
                        setArtifactCooldown(summoner, TASTY_BONE, 600);
                    }
                    if(summonerCap.getSummonedLlama() == summonableUUID){
                        summonerCap.setSummonedLlama(null);
                        setArtifactCooldown(summoner, WONDERFUL_WHEAT, 600);
                    }
                    if(summonerCap.getSummonedBat() == summonableUUID){
                        summonerCap.setSummonedBat(null);
                    }
                }
            }
        }
    }

    // A copy of the default method found in IArtifact
    public static void setArtifactCooldown(PlayerEntity playerIn, Item item, int cooldownInTicks){
        ItemStack helmet = playerIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = playerIn.getItemStackFromSlot(EquipmentSlotType.CHEST);


        float armorCooldownModifier = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getArtifactCooldown() : 0;
        float armorCooldownModifier2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getArtifactCooldown() : 0;

        float totalArmorCooldownModifier = 1.0F - armorCooldownModifier*0.01F - armorCooldownModifier2*0.01F;
        float cooldownEnchantmentReduction = 0;
        if(EnchantUtils.hasEnchantment(playerIn, ArmorEnchantmentList.COOLDOWN)) {
            int cooldownEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.COOLDOWN, playerIn);
            if (cooldownEnchantmentLevel == 1) cooldownEnchantmentReduction = (int) (0.1F * cooldownInTicks);
            if (cooldownEnchantmentLevel == 2) cooldownEnchantmentReduction = (int) (0.19F * cooldownInTicks);
            if (cooldownEnchantmentLevel == 3) cooldownEnchantmentReduction = (int) (0.27F * cooldownInTicks);
        }
        playerIn.getCooldownTracker().setCooldown(item, Math.max(0, (int) (cooldownInTicks * totalArmorCooldownModifier - cooldownEnchantmentReduction)));
    }
}
