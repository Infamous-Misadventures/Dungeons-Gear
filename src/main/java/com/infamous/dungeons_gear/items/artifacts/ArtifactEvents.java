package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.integration.curios.CuriosIntegration;
import com.infamous.dungeons_gear.utilties.*;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMinion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMinionCapability;

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

    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        if(!event.getIdentifier().equals(CuriosIntegration.ARTIFACT_IDENTIFIER)) return;
        ItemStack itemstack = event.getTo();
        if(itemstack.getItem() instanceof ArtifactItem) {
            if (!itemstack.isEmpty()) {
                event.getEntityLiving().getAttributes().addTransientAttributeModifiers(((ArtifactItem) itemstack.getItem()).getDefaultAttributeModifiers(event.getSlotIndex()));
            }
        }

        ItemStack itemstack1 = event.getFrom();
        if(itemstack1.getItem() instanceof ArtifactItem) {
            if (!itemstack1.isEmpty()) {
                event.getEntityLiving().getAttributes().removeAttributeModifiers(((ArtifactItem) itemstack1.getItem()).getDefaultAttributeModifiers(event.getSlotIndex()));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(event.player);
        if(cap != null && cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof ArtifactItem){
            cap.getUsingArtifact().getItem().onUseTick(event.player.level, event.player, cap.getUsingArtifact(), cap.getUsingArtifactRemaining());
            cap.setUsingArtifactRemaining(cap.getUsingArtifactRemaining() - 1);
        }
    }
}
