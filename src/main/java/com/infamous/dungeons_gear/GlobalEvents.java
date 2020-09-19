package com.infamous.dungeons_gear;


import com.infamous.dungeons_gear.armor.*;
import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.weapon.IWeapon;
import com.infamous.dungeons_gear.capabilities.weapon.WeaponProvider;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.init.PotionList;
import com.infamous.dungeons_gear.interfaces.IArmor;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.items.ArtifactList;
import com.infamous.dungeons_gear.items.WeaponList;
import com.infamous.dungeons_gear.melee.SoulKnifeItem;
import com.infamous.dungeons_gear.melee.SoulScytheItem;
import com.infamous.dungeons_gear.ranged.bows.SoulBowItem;
import com.infamous.dungeons_gear.ranged.crossbows.SoulCrossbowItem;
import com.infamous.dungeons_gear.utilties.AbilityUtils;
import com.infamous.dungeons_gear.utilties.EnchantUtils;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.items.ArmorList.*;
import static com.infamous.dungeons_gear.items.RangedWeaponList.*;
import static com.infamous.dungeons_gear.items.WeaponList.commonWeaponMap;
import static com.infamous.dungeons_gear.items.WeaponList.uniqueWeaponMap;
import static com.infamous.dungeons_gear.items.ArtifactList.artifactMap;


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
        EnchantUtils.addEnchantmentTagsToArrow(stack, arrowEntity);

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
            boolean soulsCriticalBoost = AbilityUtils.soulsCriticalBoost(playerEntity, stack);
            if(soulsCriticalBoost){
                PROXY.spawnParticles(playerEntity, ParticleTypes.field_239812_C_);
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
    public static void onShadowFormAttack(LivingDamageEvent event){
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity playerEntity = (PlayerEntity)event.getSource().getTrueSource();

            ICombo comboCap = playerEntity.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(comboCap.getShadowForm()){
                float originalDamage = event.getAmount();
                event.setAmount(originalDamage * 2.0F);
                comboCap.setShadowForm(false);
                playerEntity.removePotionEffect(Effects.INVISIBILITY);
            }
            /*
            if(comboCap.getGhostForm()){
                comboCap.setGhostForm(false);
                playerEntity.removePotionEffect(Effects.INVISIBILITY);
                playerEntity.removePotionEffect(Effects.GLOWING);
                playerEntity.removePotionEffect(Effects.SPEED);
                playerEntity.removePotionEffect(Effects.RESISTANCE);
            }

             */
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
