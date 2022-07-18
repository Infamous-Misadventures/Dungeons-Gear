package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsage;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.integration.curios.CuriosIntegration;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Minion;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
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
    public static void onEnchantedSheepAttack(LivingDamageEvent event){
        if(event.getSource().getEntity() instanceof Sheep){
            Sheep sheepEntity = (Sheep)event.getSource().getEntity();
            Minion summonableCap = getMinionCapability(sheepEntity);
            if(summonableCap == null) return;
            if(summonableCap.getMaster() != null){
                if(sheepEntity.getTags().contains(FIRE_SHEEP_TAG)){
                    event.getEntityLiving().setSecondsOnFire(5);
                }
                else if(sheepEntity.getTags().contains(POISON_SHEEP_TAG)){
                    MobEffectInstance poison = new MobEffectInstance(MobEffects.POISON, 100);
                    event.getEntityLiving().addEffect(poison);
                }
            }
        }
    }

    @SubscribeEvent
    public static void updateBlueEnchantedSheep(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof Sheep){
            Sheep sheepEntity = (Sheep)event.getEntityLiving();
            Minion summonableCap = getMinionCapability(sheepEntity);
            if(summonableCap == null) return;
            LivingEntity summoner = summonableCap.getMaster();
            if(summoner != null){
                if(sheepEntity.level instanceof ServerLevel){
                    if(!summoner.hasEffect(MobEffects.MOVEMENT_SPEED) && sheepEntity.getTags().contains(SPEED_SHEEP_TAG)){
                        MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100);
                        summoner.addEffect(speed);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSoulProtection(LivingDeathEvent event){
        if(event.getEntityLiving().getEffect(CustomEffects.SOUL_PROTECTION) != null){
            event.setCanceled(true);
            event.getEntityLiving().setHealth(1.0F);
            event.getEntityLiving().removeAllEffects();
            event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
            event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 900, 1));
            event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        }
    }

    @SubscribeEvent
    public static void onArrowJoinWorld(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof AbstractArrow){
            AbstractArrow arrowEntity = (AbstractArrow) event.getEntity();
            Entity shooter = arrowEntity.getOwner();
            if(shooter instanceof Player){
                Player playerEntity = (Player) shooter;
                Combo comboCap = ComboHelper.getComboCapability(playerEntity);
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
                    arrowEntity.setPierceLevel((byte) (arrowEntity.getPierceLevel() + 1));
                }
            }
        }
    }


    @SubscribeEvent
    public static void onSpecialArrowImpact(ProjectileImpactEvent event) {

        if (event.getProjectile() instanceof AbstractArrow arrow) {
            Entity shooter = arrow.getOwner();

            if (!(shooter instanceof Player)) return;
            Player player = (Player) shooter;

            if (arrow.getTags().contains(TormentQuiverItem.TORMENT_ARROW)) {
                if (arrow.tickCount > 1200) {
                    arrow.remove(Entity.RemovalReason.DISCARDED);
                    event.setCanceled(true);
                }

                if (event.getRayTraceResult() instanceof EntityHitResult) {
                    EntityHitResult entityRayTraceResult = (EntityHitResult) event.getRayTraceResult();
                    Entity targetEntity = entityRayTraceResult.getEntity();
                    if (!(targetEntity instanceof LivingEntity)) {
                        event.setCanceled(true);
                    }

                    int currentKnockbackStrength = arrow.knockback;
                    (arrow).setKnockback(currentKnockbackStrength + 1);
                }

                if (event.getRayTraceResult() instanceof BlockHitResult) event.setCanceled(true);
            }
            if (arrow.getTags().contains(ThunderingQuiverItem.THUNDERING_ARROW)) {
                if (event.getRayTraceResult() instanceof EntityHitResult) {
                    EntityHitResult entityRayTraceResult = (EntityHitResult) event.getRayTraceResult();
                    Entity targetEntity = entityRayTraceResult.getEntity();
                    if (targetEntity instanceof LivingEntity) {
                        SoundHelper.playLightningStrikeSounds(arrow);
                        AreaOfEffectHelper.electrifyNearbyEnemies(arrow, 5, 5, Integer.MAX_VALUE);
                    }
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
        ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(event.player);
        if(cap != null && cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof ArtifactItem){
            cap.getUsingArtifact().getItem().onUseTick(event.player.level, event.player, cap.getUsingArtifact(), cap.getUsingArtifactRemaining());
            cap.setUsingArtifactRemaining(cap.getUsingArtifactRemaining() - 1);
        }
    }
}
