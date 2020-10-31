package com.infamous.dungeons_gear.armor;


import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.goals.BeeFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtTargetGoal;
import com.infamous.dungeons_gear.interfaces.IArmor;
import com.infamous.dungeons_gear.items.RangedWeaponList;
import com.infamous.dungeons_gear.items.WeaponList;
import com.infamous.dungeons_gear.utilties.AbilityHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Collection;
import java.util.List;

import static com.infamous.dungeons_gear.items.ArmorList.*;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ArmorEvents {

    @SubscribeEvent
    public static void onSpelunkerArmorEquipped(LivingEquipmentChangeEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            World world = playerEntity.getEntityWorld();
            if(event.getTo().getItem() instanceof IArmor){
                if(((IArmor)event.getTo().getItem()).doGivesYouAPetBat()){
                    ArmorEffectHelper.summonOrTeleportBat(playerEntity, world);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDamageInvolvingSpecialArmor(LivingDamageEvent event){

        // Handling armors that boost ranged or magic damage - Attacker POV
        if(event.getSource() instanceof IndirectEntityDamageSource){
            if(event.getSource().getTrueSource() instanceof LivingEntity){

                float originalDamage = event.getAmount();

                LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
                ItemStack helmet = attacker.getItemStackFromSlot(EquipmentSlotType.HEAD);
                ItemStack chestplate = attacker.getItemStackFromSlot(EquipmentSlotType.CHEST);

                if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity){
                    increaseEventRangedDamage(event, originalDamage, helmet, chestplate);
                }
                else if(event.getSource().isMagicDamage()){
                    increaseEventMagicDamage(event, originalDamage, helmet, chestplate);

                }
            }
        }


        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
            ItemStack helmet = attacker.getItemStackFromSlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = attacker.getItemStackFromSlot(EquipmentSlotType.CHEST);
            float damageAmount = event.getAmount();
            handleLifeSteal(damageAmount, attacker, helmet, chestplate);
        }

        // Handling armors that either negate hits or teleport the victim away when hit - Victim POV
        LivingEntity victim = event.getEntityLiving();
        ItemStack helmet = victim.getItemStackFromSlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = victim.getItemStackFromSlot(EquipmentSlotType.CHEST);

        handleNegateHit(event, victim, helmet, chestplate);
        handleTeleportOnHit(victim, helmet, chestplate);
    }

    private static void handleLifeSteal(float damageAmount, LivingEntity attacker, ItemStack helmet, ItemStack chestplate) {
        float lifeSteal = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float lifeSteal2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float totalLifeSteal  = lifeSteal * 0.01F + lifeSteal2 * 0.01F;
        attacker.heal(damageAmount * totalLifeSteal);
    }

    private static void handleTeleportOnHit(LivingEntity victim, ItemStack helmet, ItemStack chestplate) {
        float teleportChance = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float teleportChance2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float totalTeleportChance  = teleportChance * 0.01F + teleportChance2 * 0.01F;

        float teleportRand = victim.getRNG().nextFloat();
        if(teleportRand <= totalTeleportChance){
            ArmorEffectHelper.teleportOnHit(victim);
        }
    }

    private static void handleNegateHit(LivingDamageEvent event, LivingEntity victim, ItemStack helmet, ItemStack chestplate) {
        float negateHitChance = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getChanceToNegateHits() : 0;
        float negateHitChance2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getChanceToNegateHits() : 0;
        float totalNegateHitChance = negateHitChance * 0.01F + negateHitChance2 * 0.01F;

        float negateHitRand = victim.getRNG().nextFloat();
        if(negateHitRand <= totalNegateHitChance){
            event.setCanceled(true);
        }
    }

    private static void increaseEventMagicDamage(LivingDamageEvent event, float originalDamage, ItemStack helmet, ItemStack chestplate) {

        float magicDamage = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getMagicDamage() : 0;
        float magicDamage2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getMagicDamage() : 0;

        float damageMultiplier = magicDamage * 0.01F + magicDamage2 * 0.01F;

        float additionalDamage = originalDamage * damageMultiplier;

        if(additionalDamage > 0) event.setAmount(originalDamage + additionalDamage);
    }

    private static void increaseEventRangedDamage(LivingDamageEvent event, float originalDamage, ItemStack helmet, ItemStack chestplate) {

        float rangedDamage = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getRangedDamage() : 0;
        float rangedDamage2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getRangedDamage() : 0;

        float damageMultiplier = rangedDamage * 0.01F + rangedDamage2 * 0.01F;

        float additionalDamage = originalDamage * damageMultiplier;

        if(additionalDamage > 0) event.setAmount(originalDamage + additionalDamage);
    }

    @SubscribeEvent
    public static void onFreezingApplied(PotionEvent.PotionAddedEvent event){
        EffectInstance effectInstance = event.getPotionEffect();
        LivingEntity livingEntity = event.getEntityLiving();
        ItemStack helmet = livingEntity.getItemStackFromSlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = livingEntity.getItemStackFromSlot(EquipmentSlotType.CHEST);
        reduceFreezingEffect(event, effectInstance, helmet, chestplate);
    }

    private static void reduceFreezingEffect(PotionEvent.PotionAddedEvent event, EffectInstance effectInstance, ItemStack helmet, ItemStack chestplate) {
        float freezingResistance = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getFreezingResistance() : 0;
        float freezingResistance2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getFreezingResistance() : 0;

        float freezingMultiplier = freezingResistance * 0.01F + freezingResistance2 * 0.01F;

        if(freezingMultiplier > 0){
            if(event.getPotionEffect().getPotion() == Effects.SLOWNESS || event.getPotionEffect().getPotion() == Effects.MINING_FATIGUE){
                int oldDuration = effectInstance.getDuration();
                ObfuscationReflectionHelper.setPrivateValue(EffectInstance.class, effectInstance, (int)(oldDuration * freezingMultiplier), "field_76460_b");
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event){
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            ItemStack chestplate = attacker.getItemStackFromSlot(EquipmentSlotType.CHEST);
            boolean lifeStealChestplateFlag = chestplate.getItem() == SPIDER_ARMOR || chestplate.getItem() instanceof GrimArmorItem;
            if(lifeStealChestplateFlag){
                float victimMaxHealth = event.getEntityLiving().getMaxHealth();
                if(attacker.getHealth() < attacker.getMaxHealth()){
                    attacker.heal(victimMaxHealth * 0.03F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHealthPotionConsumed(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if(player.isAlive()){
            List<EffectInstance> potionEffects = PotionUtils.getEffectsFromStack(event.getItem());
            if(potionEffects.isEmpty()) return;
            ItemStack helmet = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = player.getItemStackFromSlot(EquipmentSlotType.CHEST);

            if(potionEffects.get(0).getPotion() == Effects.INSTANT_HEALTH){
                EffectInstance instantHealth = potionEffects.get(0);
                handleHealthPotionBoost(player, helmet, chestplate);
                handleHealNearbyAllies(player, instantHealth, helmet, chestplate);
            }
        }
    }

    private static void handleHealNearbyAllies(PlayerEntity player, EffectInstance instantHealth, ItemStack helmet, ItemStack chestplate) {
        boolean doHealthPotionsHealNearbyAllies = helmet.getItem() instanceof IArmor && ((IArmor) helmet.getItem()).doHealthPotionsHealNearbyAllies();
        boolean doHealthPotionsHealNearbyAllies2 = chestplate.getItem() instanceof IArmor && ((IArmor) chestplate.getItem()).doHealthPotionsHealNearbyAllies();

        boolean healNearbyAllies = doHealthPotionsHealNearbyAllies || doHealthPotionsHealNearbyAllies2;
        if(healNearbyAllies){
            AreaOfEffectHelper.healNearbyAllies(player, instantHealth, 12);
        }
    }

    private static void handleHealthPotionBoost(PlayerEntity player, ItemStack helmet, ItemStack chestplate) {
        float healthPotionBoost = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getHealthPotionBoost() : 0;
        float healthPotionBoost2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getHealthPotionBoost() : 0;
        float totalhealthPotionBoost = (healthPotionBoost + healthPotionBoost2);

        if(totalhealthPotionBoost > 0){
            player.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, (int) totalhealthPotionBoost - 1));
        }
    }

    @SubscribeEvent
    public static void onArrowDrop(LivingDropsEvent event){
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            LivingEntity victim = (LivingEntity) event.getEntityLiving();
            ItemStack helmet = attacker.getItemStackFromSlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = attacker.getItemStackFromSlot(EquipmentSlotType.CHEST);


            int arrowDrops = helmet.getItem() instanceof IArmor ? ((IArmor) helmet.getItem()).getArrowsPerBundle() : 0;
            int arrowDrops2 = chestplate.getItem() instanceof IArmor ? ((IArmor) chestplate.getItem()).getArrowsPerBundle() : 0;
            int totalarrowDrops = arrowDrops + arrowDrops2;

            // TODO: Add Arrow Bundles, rework this to add to Arrow Bundles
            if(totalarrowDrops > 0){
                Collection<ItemEntity> itemEntities = event.getDrops();
                if(victim instanceof IMob){
                    if(attacker.getRNG().nextFloat() <= 0.5F){
                        ItemEntity arrowDrop = new ItemEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ(), new ItemStack(Items.ARROW, totalarrowDrops));
                        itemEntities.add(arrowDrop);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if(player == null) return;
        if(event.phase == TickEvent.Phase.START) return;
        if(player.isAlive()){
            ICombo comboCap = player.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
            int jumpCooldownTimer = comboCap.getJumpCooldownTimer();
            if(jumpCooldownTimer > 0){
                comboCap.setJumpCooldownTimer(jumpCooldownTimer - 1);
            }
        }
    }

    @SubscribeEvent
    public static void handleJumpAbilities(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        if(livingEntity instanceof PlayerEntity){
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            ItemStack helmet = playerEntity.getItemStackFromSlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = playerEntity.getItemStackFromSlot(EquipmentSlotType.CHEST);
            ICombo comboCap = playerEntity.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
            int jumpCooldownTimer = comboCap.getJumpCooldownTimer();

            if(jumpCooldownTimer <= 0){
                handleJumpBoost(playerEntity, helmet, chestplate);

                handleInvulnerableJump(playerEntity, helmet, chestplate);

                handleJumpEnchantments(playerEntity, helmet, chestplate);
            }

            float jumpCooldown = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getLongerJumpAbilityCooldown() : 0;
            float jumpCooldown2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getLongerJumpAbilityCooldown() : 0;
            float totalJumpCooldown = jumpCooldown * 0.01F + jumpCooldown2 * 0.01F;

            int jumpCooldownTimerLength = totalJumpCooldown > 0 ? 60 + (int) (60 * totalJumpCooldown) : 60;
            comboCap.setJumpCooldownTimer(jumpCooldownTimerLength);
        }
    }

    private static void handleJumpEnchantments(PlayerEntity playerEntity, ItemStack helmet, ItemStack chestplate) {
        if(ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.ELECTRIFIED)){
                AreaOfEffectHelper.electrifyNearbyEnemies(playerEntity, 5, 5, 3);
        }

        if(ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.FIRE_TRAIL)){
            int fireTrailLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.FIRE_TRAIL, playerEntity);
                AreaOfEffectHelper.burnNearbyEnemies(playerEntity, 1.0F * fireTrailLevel, 1.5F);
        }

        // TODO: Beenest Armor and Buzzynest Armor
        if(ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.TUMBLEBEE)){
            int tumblebeeLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.TUMBLEBEE, playerEntity);

            float tumblebeeRand = playerEntity.getRNG().nextFloat();
            if(tumblebeeRand <= 0.333F * tumblebeeLevel){
                summonTumblebeeBee(playerEntity);
            }
        }

        boolean highlandArmorFlag = chestplate.getItem() == HIGHLAND_ARMOR
                || helmet.getItem() == HIGHLAND_ARMOR_HELMET;
        if(ModEnchantmentHelper.hasEnchantment(playerEntity, ArmorEnchantmentList.SWIFTFOOTED) || highlandArmorFlag){
                int swiftfootedLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.SWIFTFOOTED, playerEntity);
                if(highlandArmorFlag) swiftfootedLevel++;
                EffectInstance speedBoost = new EffectInstance(Effects.SPEED, 60, swiftfootedLevel - 1);
                playerEntity.addPotionEffect(speedBoost);
        }

        handleDynamoEnchantment(playerEntity);
    }

    private static void handleDynamoEnchantment(PlayerEntity playerEntity) {
        ItemStack mainhand = playerEntity.getHeldItemMainhand();
        boolean uniqueWeaponFlag = mainhand.getItem() == WeaponList.GREAT_AXEBLADE
                || mainhand.getItem() == RangedWeaponList.ANCIENT_BOW
                || mainhand.getItem() == RangedWeaponList.CORRUPTED_CROSSBOW;
        if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.DYNAMO) || uniqueWeaponFlag){
            int dynamoLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.DYNAMO, mainhand);
            if(uniqueWeaponFlag) dynamoLevel++;
            ICombo comboCap = playerEntity.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
            double originalDynamoMultiplier = comboCap.getDynamoMultiplier();
            double dynamoModifier = 1.0D + (0.5D * Math.max((dynamoLevel - 1), 0));
            comboCap.setDynamoMultiplier(originalDynamoMultiplier + dynamoModifier);
        }
    }

    private static void summonTumblebeeBee(PlayerEntity playerEntity) {
        ISummoner summonerCap = playerEntity.getCapability(SummonerProvider.SUMMONER_CAPABILITY).orElseThrow(IllegalStateException::new);
        BeeEntity beeEntity = EntityType.BEE.create(playerEntity.world);
        if (beeEntity!= null) {
            if(summonerCap.addTumblebeeBee(beeEntity.getUniqueID())){
                beeEntity.setLocationAndAngles((double)playerEntity.getPosX() + 0.5D, (double)playerEntity.getPosY() + 0.05D, (double)playerEntity.getPosZ() + 0.5D, 0.0F, 0.0F);

                beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

                beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
                beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
                beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                        (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

                playerEntity.world.playSound((PlayerEntity)null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_BEE_LOOP, SoundCategory.AMBIENT, 64.0F, 1.0F);
                playerEntity.world.addEntity(beeEntity);

                ISummonable summonable = beeEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
                summonable.setSummoner(playerEntity.getUniqueID());
            }
            else {
                beeEntity.remove();
            }
        }
    }

    private static void handleInvulnerableJump(PlayerEntity playerEntity, ItemStack helmet, ItemStack chestplate) {
        boolean invulnerableJump = helmet.getItem() instanceof IArmor && ((IArmor) helmet.getItem()).doBriefInvulnerabilityWhenJumping();
        boolean invulnerableJump2 = chestplate.getItem() instanceof IArmor && ((IArmor) chestplate.getItem()).doBriefInvulnerabilityWhenJumping();
        boolean doInvulnerableJump  = invulnerableJump || invulnerableJump2;

            if(doInvulnerableJump){
                EffectInstance resistance = new EffectInstance(Effects.RESISTANCE, 20, 4);
                playerEntity.addPotionEffect(resistance);
            }
    }

    private static void handleJumpBoost(PlayerEntity playerEntity, ItemStack helmet, ItemStack chestplate) {
        float jumpBoost = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getHigherJumps() : 0;
        float jumpBoost2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getHigherJumps() : 0;
        float totalJumpBoost = jumpBoost * 0.002F + jumpBoost2 * 0.002F;

            if(totalJumpBoost > 0){
                playerEntity.setMotion(playerEntity.getMotion().add(0, totalJumpBoost, 0));
            }
    }
}
