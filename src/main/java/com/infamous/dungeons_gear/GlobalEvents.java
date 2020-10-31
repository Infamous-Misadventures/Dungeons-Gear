package com.infamous.dungeons_gear;


import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.weapon.IWeapon;
import com.infamous.dungeons_gear.capabilities.weapon.WeaponProvider;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.init.PotionList;
import com.infamous.dungeons_gear.interfaces.IArmor;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.items.RangedWeaponList.*;


@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class GlobalEvents {

    @SubscribeEvent
    public static void onArrowJoinWorld(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof AbstractArrowEntity){
            AbstractArrowEntity arrowEntity = (AbstractArrowEntity)event.getEntity();
            //if(arrowEntity.getTags().contains("BonusProjectile") || arrowEntity.getTags().contains("ChainReactionProjectile")) return;
            Entity shooterEntity = arrowEntity.func_234616_v_();
            if(shooterEntity instanceof LivingEntity){
                LivingEntity shooter = (LivingEntity) shooterEntity;
                ItemStack mainhandStack = shooter.getHeldItemMainhand();
                ItemStack offhandStack = shooter.getHeldItemOffhand();
                // This should guarantee the arrow came from the correct itemstack
                if(mainhandStack.getItem() instanceof BowItem || mainhandStack.getItem() instanceof CrossbowItem){
                    handleRangedEnchantments(arrowEntity, shooter, mainhandStack);
                }
                else if(offhandStack.getItem() instanceof BowItem || offhandStack.getItem() instanceof CrossbowItem){
                    handleRangedEnchantments(arrowEntity, shooter, offhandStack);
                }
            }
        }
    }

    private static void handleRangedEnchantments(AbstractArrowEntity arrowEntity, LivingEntity shooter, ItemStack stack) {
        ModEnchantmentHelper.addEnchantmentTagsToArrow(stack, arrowEntity);

        int fuseShotLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.FUSE_SHOT, stack);
        if(stack.getItem() == RED_SNAKE) fuseShotLevel++;
        if(fuseShotLevel > 0){
            IWeapon weaponCap = stack.getCapability(WeaponProvider.WEAPON_CAPABILITY).orElseThrow(IllegalStateException::new);
            int fuseShotCounter = weaponCap.getFuseShotCounter();
            // 6 - 1, 6 - 2, 6 - 3
            // zero indexing, so subtract 1 as well
            if(fuseShotCounter == 6 - fuseShotLevel - 1){
                arrowEntity.addTag("FuseShot");
                weaponCap.setFuseShotCounter(0);
            }
            else{
                weaponCap.setFuseShotCounter(fuseShotCounter + 1);
            }
        }

        if(shooter instanceof PlayerEntity){
            PlayerEntity playerEntity = (PlayerEntity) shooter;
            boolean soulsCriticalBoost = ProjectileEffectHelper.soulsCriticalBoost(playerEntity, stack);
            if(soulsCriticalBoost){
                PROXY.spawnParticles(playerEntity, ParticleTypes.SOUL);
                arrowEntity.setIsCritical(true);
                arrowEntity.setDamage(arrowEntity.getDamage() * 2);
            }
        }
    }

    @SubscribeEvent
    public static void onCancelAttackBecauseStunned(LivingAttackEvent event){
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity attacker = (PlayerEntity)event.getSource().getTrueSource();
            if(attacker.getActivePotionEffect(CustomEffects.STUNNED) != null)
                event.setCanceled(true);
        }
    }



    @SubscribeEvent
    public static void onStunnedMob(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof MobEntity){
            MobEntity mobEntity = (MobEntity) event.getEntityLiving();
            if(mobEntity.getActivePotionEffect(CustomEffects.STUNNED) != null && !mobEntity.getTags().contains("Stunned")){
                if(!mobEntity.isAIDisabled()){
                    mobEntity.setNoAI(true);
                    mobEntity.addTag("Stunned");
                }
            }
            else if(mobEntity.isAIDisabled() && mobEntity.getTags().contains("Stunned")){
                mobEntity.setNoAI(false);
                mobEntity.removeTag("Stunned");
            }
        }
    }

    @SubscribeEvent
    public static void onCapabilityAttack(LivingDamageEvent event){
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity playerEntity = (PlayerEntity)event.getSource().getTrueSource();

            ICombo comboCap = playerEntity.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(comboCap.getShadowForm()){
                float originalDamage = event.getAmount();
                event.setAmount(originalDamage * 2.0F);
                comboCap.setShadowForm(false);
                playerEntity.removePotionEffect(Effects.INVISIBILITY);
            }
            if(comboCap.getDynamoMultiplier() > 1.0D){
                double dynamoMultiplier = comboCap.getDynamoMultiplier();
                float originalDamage = event.getAmount();
                event.setAmount((float) (originalDamage * dynamoMultiplier));
                comboCap.setDynamoMultiplier(1.0);
            }
        }
    }

    @SubscribeEvent
    public static void onShadowFormAdded(LivingEntityUseItemEvent.Finish event){
        if(PotionUtils.getPotionFromItem(event.getItem()) == PotionList.SHADOW_BREW){
            if(event.getEntityLiving() instanceof PlayerEntity){
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                ICombo comboCap = playerEntity.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
                comboCap.setShadowForm(true);
            }
        }
    }

    @SubscribeEvent
    public static void onShadowFormRemoved(PotionEvent.PotionRemoveEvent event){
        if(event.getPotion() == Effects.INVISIBILITY){
            if(event.getEntityLiving() instanceof PlayerEntity){
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                ICombo comboCap = playerEntity.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
                comboCap.setShadowForm(false);
            }
        }
    }

    @SubscribeEvent
    public static void onSoulGatheringItemsXPDrop(LivingExperienceDropEvent event){
        if(event.getAttackingPlayer() != null){
            PlayerEntity attacker = event.getAttackingPlayer();
            int originalExperience = event.getDroppedExperience();
            int additionalExperienceCounter = 0;
            ItemStack mainhand = attacker.getHeldItemMainhand();
            ItemStack offhand = attacker.getHeldItemOffhand();
            if(mainhand.getItem() instanceof ISoulGatherer){
                additionalExperienceCounter += ((ISoulGatherer)mainhand.getItem()).getGatherAmount(mainhand);
            }
            if(offhand.getItem() instanceof ISoulGatherer){
                additionalExperienceCounter += ((ISoulGatherer)offhand.getItem()).getGatherAmount(offhand);
            }

            ItemStack helmet = attacker.getItemStackFromSlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = attacker.getItemStackFromSlot(EquipmentSlotType.CHEST);

            float soulsGathered = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getSoulsGathered() : 0;
            float soulsGathered2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getSoulsGathered() : 0;
            float totalSoulsGathered = soulsGathered * 0.01F + soulsGathered2 * 0.01F;

            if(totalSoulsGathered > 0){
                additionalExperienceCounter += originalExperience * totalSoulsGathered;
            }

            event.setDroppedExperience(originalExperience + additionalExperienceCounter);
        }
    }
}
