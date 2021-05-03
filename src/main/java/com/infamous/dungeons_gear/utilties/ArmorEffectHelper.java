package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.IArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ArmorEffectHelper {
    public static void summonOrTeleportBat(PlayerEntity playerEntity, World world) {
        ISummoner summonerCap = CapabilityHelper.getSummonerCapability(playerEntity);
        if(summonerCap == null) return;
        if(summonerCap.getSummonedBat() == null){
            BatEntity batEntity = EntityType.BAT.create(world);
            if (batEntity!= null) {
                ISummonable summonable = CapabilityHelper.getSummonableCapability(batEntity);
                if(summonable != null){

                    summonable.setSummoner(playerEntity.getUniqueID());
                    summonerCap.setSummonedBat(batEntity.getUniqueID());

                    createBat(playerEntity, world, batEntity);
                }
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

    private static void createBat(PlayerEntity playerEntity, World world, BatEntity batEntity) {
        batEntity.setLocationAndAngles((double)playerEntity.getPosX() + playerEntity.getEyeHeight(), (double)playerEntity.getPosY() + playerEntity.getEyeHeight(), (double)playerEntity.getPosZ() + playerEntity.getEyeHeight(), 0.0F, 0.0F);

        batEntity.goalSelector.addGoal(1, new BatMeleeAttackGoal(batEntity, 1.0D, true));
        batEntity.goalSelector.addGoal(2, new BatFollowOwnerGoal(batEntity, 2.1D, 10.0F, 2.0F, false));


        batEntity.targetSelector.addGoal(1, new BatOwnerHurtByTargetGoal(batEntity));
        batEntity.targetSelector.addGoal(2, new BatOwnerHurtTargetGoal(batEntity));
        batEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(batEntity, LivingEntity.class, 5, false, false,
                (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

        world.playSound((PlayerEntity)null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_BAT_AMBIENT, SoundCategory.AMBIENT, 64.0F, 1.0F);
        world.addEntity(batEntity);
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

    public static void handleInvulnerableJump(PlayerEntity playerEntity, ItemStack helmet, ItemStack chestplate) {
        boolean invulnerableJump = helmet.getItem() instanceof IArmor && ((IArmor) helmet.getItem()).doBriefInvulnerabilityWhenJumping();
        boolean invulnerableJump2 = chestplate.getItem() instanceof IArmor && ((IArmor) chestplate.getItem()).doBriefInvulnerabilityWhenJumping();
        boolean doInvulnerableJump = invulnerableJump || invulnerableJump2;

        if (doInvulnerableJump) {
            EffectInstance resistance = new EffectInstance(Effects.RESISTANCE, 20, 4);
            playerEntity.addPotionEffect(resistance);
        }
    }

    public static void handleJumpBoost(PlayerEntity playerEntity, ItemStack helmet, ItemStack chestplate) {
        float jumpBoost = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getHigherJumps() : 0;
        float jumpBoost2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getHigherJumps() : 0;
        float totalJumpBoost = jumpBoost * 0.002F + jumpBoost2 * 0.002F;

        if (totalJumpBoost > 0) {
            playerEntity.setMotion(playerEntity.getMotion().add(0, totalJumpBoost, 0));
        }
    }

    public static void handleJumpEnchantments(PlayerEntity playerEntity, ItemStack helmet, ItemStack chestplate) {
        if (ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.ELECTRIFIED)) {
            AreaOfEffectHelper.electrifyNearbyEnemies(playerEntity, 5, 5, 3);
        }

        if (ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.FIRE_TRAIL)) {
            int fireTrailLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.FIRE_TRAIL, playerEntity);
            AreaOfEffectHelper.burnNearbyEnemies(playerEntity, 1.0F * fireTrailLevel, 1.5F);
        }

        // TODO: Beenest Armor and Buzzynest Armor
        if (ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.TUMBLEBEE)) {
            int tumblebeeLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.TUMBLEBEE, playerEntity);

            float tumblebeeRand = playerEntity.getRNG().nextFloat();
            if (tumblebeeRand <= 0.333F * tumblebeeLevel) {
                summonTumblebeeBee(playerEntity);
            }
        }

        boolean highlandArmorFlag = chestplate.getItem() == DeferredItemInit.HIGHLAND_ARMOR.get()
                || helmet.getItem() == DeferredItemInit.HIGHLAND_ARMOR_HELMET.get();
        if (ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.SWIFTFOOTED) || highlandArmorFlag) {
            int swiftfootedLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.SWIFTFOOTED, playerEntity);
            if (highlandArmorFlag) swiftfootedLevel++;
            EffectInstance speedBoost = new EffectInstance(Effects.SPEED, 60, swiftfootedLevel - 1);
            playerEntity.addPotionEffect(speedBoost);
        }

        handleDynamoEnchantment(playerEntity);
    }

    private static void handleDynamoEnchantment(PlayerEntity playerEntity) {
        ItemStack mainhand = playerEntity.getHeldItemMainhand();
        boolean uniqueWeaponFlag = mainhand.getItem() == DeferredItemInit.GREAT_AXEBLADE.get()
                || mainhand.getItem() == DeferredItemInit.ANCIENT_BOW.get()
                || mainhand.getItem() == DeferredItemInit.CORRUPTED_CROSSBOW.get();
        if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.DYNAMO) || uniqueWeaponFlag) {
            int dynamoLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.DYNAMO, mainhand);
            if (uniqueWeaponFlag) dynamoLevel++;
            ICombo comboCap = CapabilityHelper.getComboCapability(playerEntity);
            if (comboCap == null) return;
            double originalDynamoMultiplier = comboCap.getDynamoMultiplier();
            double dynamoModifier = 1.0D + (0.5D * Math.max((dynamoLevel - 1), 0));
            comboCap.setDynamoMultiplier(originalDynamoMultiplier + dynamoModifier);
        }
    }

    private static void summonTumblebeeBee(PlayerEntity playerEntity) {
        ISummoner summonerCap = CapabilityHelper.getSummonerCapability(playerEntity);
        if (summonerCap == null) return;
        BeeEntity beeEntity = EntityType.BEE.create(playerEntity.world);
        if (beeEntity != null) {
            ISummonable summonable = CapabilityHelper.getSummonableCapability(beeEntity);
            if (summonable != null && summonerCap.addTumblebeeBee(beeEntity.getUniqueID())) {
                summonable.setSummoner(playerEntity.getUniqueID());

                createBee(playerEntity, beeEntity);
            } else {
                beeEntity.remove();
            }
        }
    }

    private static void createBee(PlayerEntity playerEntity, BeeEntity beeEntity) {
        beeEntity.setLocationAndAngles((double) playerEntity.getPosX() + 0.5D, (double) playerEntity.getPosY() + 0.05D, (double) playerEntity.getPosZ() + 0.5D, 0.0F, 0.0F);

        beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

        beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

        playerEntity.world.playSound((PlayerEntity) null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_BEE_LOOP, SoundCategory.AMBIENT, 64.0F, 1.0F);
        playerEntity.world.addEntity(beeEntity);
    }


}
