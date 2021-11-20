package com.infamous.dungeons_gear.items.armor;


import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.armor.ArrowHoarderEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.infamous.dungeons_gear.registry.ItemRegistry.ARROW_BUNDLE;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ArmorEvents {

    @SubscribeEvent
    public static void onSpelunkerArmorEquipped(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && event.getSlot() != EquipmentSlotType.OFFHAND && event.getSlot() != EquipmentSlotType.MAINHAND) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            World world = playerEntity.getCommandSenderWorld();
            if (event.getTo().getItem() instanceof IArmor) {
                if (((IArmor) event.getTo().getItem()).doGivesYouAPetBat()) {
                    ArmorEffectHelper.summonOrTeleportBat(playerEntity, world);
                }
            }
        }
    }

    @SubscribeEvent
    public static void respawnPetBat(TickEvent.PlayerTickEvent event) {
        if (event.player.tickCount % 140 == 0)
            for (ItemStack i : event.player.getArmorSlots()) {
                if (i.getItem() instanceof IArmor && ((IArmor) i.getItem()).doGivesYouAPetBat()) {
                    ArmorEffectHelper.summonOrTeleportBat(event.player, event.player.level);
                    return;
                }
            }
    }

    @SubscribeEvent
    public static void onDamageInvolvingSpecialArmor(LivingDamageEvent event) {

        // Handling armors that boost ranged or magic damage - Attacker POV
        if (event.getSource() instanceof IndirectEntityDamageSource) {
            if (event.getSource().getEntity() instanceof LivingEntity) {

                float originalDamage = event.getAmount();

                LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
                ItemStack helmet = attacker.getItemBySlot(EquipmentSlotType.HEAD);
                ItemStack chestplate = attacker.getItemBySlot(EquipmentSlotType.CHEST);

                if (event.getSource().getDirectEntity() instanceof AbstractArrowEntity) {
                    increaseEventRangedDamage(event, originalDamage, helmet, chestplate);
                } else if (event.getSource().isMagic()) {
                    increaseEventMagicDamage(event, originalDamage, helmet, chestplate);

                }
            }
        }


        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            ItemStack helmet = attacker.getItemBySlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = attacker.getItemBySlot(EquipmentSlotType.CHEST);
            float damageAmount = event.getAmount();
            handleLifeSteal(damageAmount, attacker, helmet, chestplate);
        }

        // Handling armors that either negate hits or teleport the victim away when hit - Victim POV
        LivingEntity victim = event.getEntityLiving();
        ItemStack helmet = victim.getItemBySlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = victim.getItemBySlot(EquipmentSlotType.CHEST);

        handleNegateHit(event, victim, helmet, chestplate);
        handleTeleportOnHit(victim, helmet, chestplate);
    }

    private static void handleLifeSteal(float damageAmount, LivingEntity attacker, ItemStack helmet, ItemStack chestplate) {
        float lifeSteal = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float lifeSteal2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float totalLifeSteal = lifeSteal * 0.01F + lifeSteal2 * 0.01F;
        attacker.heal(damageAmount * totalLifeSteal);
    }

    private static void handleTeleportOnHit(LivingEntity victim, ItemStack helmet, ItemStack chestplate) {
        float teleportChance = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float teleportChance2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getChanceToTeleportAwayWhenHit() : 0;
        float totalTeleportChance = teleportChance * 0.01F + teleportChance2 * 0.01F;

        float teleportRand = victim.getRandom().nextFloat();
        if (teleportRand <= totalTeleportChance) {
            ArmorEffectHelper.teleportOnHit(victim);
        }
    }

    private static void handleNegateHit(LivingDamageEvent event, LivingEntity victim, ItemStack helmet, ItemStack chestplate) {
        float negateHitChance = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getChanceToNegateHits() : 0;
        float negateHitChance2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getChanceToNegateHits() : 0;
        float totalNegateHitChance = negateHitChance * 0.01F + negateHitChance2 * 0.01F;

        float negateHitRand = victim.getRandom().nextFloat();
        if (negateHitRand <= totalNegateHitChance) {
            event.setCanceled(true);
        }
    }

    private static void increaseEventMagicDamage(LivingDamageEvent event, float originalDamage, ItemStack helmet, ItemStack chestplate) {

        float magicDamage = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getMagicDamage() : 0;
        float magicDamage2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getMagicDamage() : 0;

        float damageMultiplier = magicDamage * 0.01F + magicDamage2 * 0.01F;

        float additionalDamage = originalDamage * damageMultiplier;

        if (additionalDamage > 0) event.setAmount(originalDamage + additionalDamage);
    }

    private static void increaseEventRangedDamage(LivingDamageEvent event, float originalDamage, ItemStack helmet, ItemStack chestplate) {

        float rangedDamage = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getRangedDamage() : 0;
        float rangedDamage2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getRangedDamage() : 0;

        float damageMultiplier = rangedDamage * 0.01F + rangedDamage2 * 0.01F;

        float additionalDamage = originalDamage * damageMultiplier;

        if (additionalDamage > 0) event.setAmount(originalDamage + additionalDamage);
    }

    @SubscribeEvent
    public static void onFreezingApplied(PotionEvent.PotionAddedEvent event) {
        EffectInstance effectInstance = event.getPotionEffect();
        LivingEntity livingEntity = event.getEntityLiving();
        ItemStack helmet = livingEntity.getItemBySlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = livingEntity.getItemBySlot(EquipmentSlotType.CHEST);
        reduceFreezingEffect(event, effectInstance, helmet, chestplate);
    }

    private static void reduceFreezingEffect(PotionEvent.PotionAddedEvent event, EffectInstance effectInstance, ItemStack helmet, ItemStack chestplate) {
        float freezingResistance = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getFreezingResistance() : 0;
        float freezingResistance2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getFreezingResistance() : 0;

        float freezingMultiplier = freezingResistance * 0.01F + freezingResistance2 * 0.01F;

        if (freezingMultiplier > 0) {
            if (event.getPotionEffect().getEffect() == Effects.MOVEMENT_SLOWDOWN || event.getPotionEffect().getEffect() == Effects.DIG_SLOWDOWN) {
                int oldDuration = effectInstance.getDuration();
                effectInstance.duration = (int) (oldDuration * freezingMultiplier);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            ItemStack helmet = attacker.getItemBySlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = attacker.getItemBySlot(EquipmentSlotType.CHEST);
            boolean lifeStealHelmetFlag = hasLifeSteal(chestplate);
            boolean lifeStealChestplateFlag = hasLifeSteal(chestplate);
            if (lifeStealHelmetFlag || lifeStealChestplateFlag) {
                double lifeStealAmount = getLifeSteal(helmet) + getLifeSteal(chestplate);
                float lifeStealAsDecimal = (float) (lifeStealAmount * 0.01);
                float victimMaxHealth = event.getEntityLiving().getMaxHealth();
                if (attacker.getHealth() < attacker.getMaxHealth()) {
                    attacker.heal(victimMaxHealth * lifeStealAsDecimal);
                }
            }
        }
    }

    private static boolean hasLifeSteal(ItemStack stack) {
        return getLifeSteal(stack) > 0;
    }

    private static double getLifeSteal(ItemStack stack) {
        if (stack.getItem() instanceof IArmor) {
            return ((IArmor) stack.getItem()).getLifeSteal();
        }
        return 0;
    }

    @SubscribeEvent
    public static void onHealthPotionConsumed(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if (player.isAlive()) {
            List<EffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if (potionEffects.isEmpty()) return;
            ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = player.getItemBySlot(EquipmentSlotType.CHEST);

            if (potionEffects.get(0).getEffect() == Effects.HEAL) {
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
        if (healNearbyAllies) {
            AreaOfEffectHelper.healNearbyAllies(player, instantHealth, 12);
        }
    }

    private static void handleHealthPotionBoost(PlayerEntity player, ItemStack helmet, ItemStack chestplate) {
        float healthPotionBoost = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getHealthPotionBoost() : 0;
        float healthPotionBoost2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getHealthPotionBoost() : 0;
        float totalhealthPotionBoost = (healthPotionBoost + healthPotionBoost2);

        if (totalhealthPotionBoost > 0) {
            //player.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, (int) totalhealthPotionBoost - 1));
            //nerf hammer!
            player.heal(totalhealthPotionBoost * 2);
        }
    }

    @SubscribeEvent
    public static void onArrowDrop(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = (LivingEntity) event.getEntityLiving();
            int maxLevel = StreamSupport.stream(attacker.getArmorSlots().spliterator(), false).map(ArrowHoarderEnchantment::arrowHoarderLevel).max(Integer::compare).orElse(0);
            int drops = (maxLevel / 4);
            drops += attacker.getRandom().nextFloat() <= (maxLevel % 4) / 4.0F ? 1 : 0;
            Collection<ItemEntity> itemEntities = event.getDrops();
            if (drops > 0 && victim instanceof IMob && itemEntities.stream().anyMatch(itemEntity -> itemEntity.getItem().getItem().equals(Items.ARROW))) {
                ItemEntity arrowDrop = new ItemEntity(victim.level, victim.getX(), victim.getY(), victim.getZ(), new ItemStack(ARROW_BUNDLE.get(), drops));
                itemEntities.add(arrowDrop);
            }
        }
    }

    private static int getArrowHoarderBuiltIn(ItemStack helmet) {
        return helmet.getItem() instanceof IArmor && ((IArmor) helmet.getItem()).hasArrowHoarderBuiltIn(helmet) ? 1 : 0;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()) {
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap == null) return;

            if (comboCap.getJumpCooldownTimer() > 0) {
                comboCap.setJumpCooldownTimer(comboCap.getJumpCooldownTimer() - 1);
            } else if(comboCap.getJumpCooldownTimer() < 0){
                comboCap.setJumpCooldownTimer(0);
            }

            if (comboCap.getLastShoutTimer() > 0) {
                comboCap.setLastShoutTimer(comboCap.getLastShoutTimer() - 1);
            } else if(comboCap.getLastShoutTimer() < 0){
                comboCap.setLastShoutTimer(0);
            }


            if (comboCap.getComboTimer() > 0) {
                comboCap.setComboTimer(comboCap.getComboTimer() - 1);
            } else if (comboCap.getComboCount() < 0){
                comboCap.setComboCount(0);
            }
            comboCap.setOffhandCooldown(comboCap.getOffhandCooldown() + 1);
        }
    }

    @SubscribeEvent
    public static void onLivingKnockbackEvent(LivingKnockBackEvent event){
        LivingEntity defender = (LivingEntity) event.getEntityLiving();
        Optional<Float> max = StreamSupport.stream(defender.getArmorSlots().spliterator(), false).map(ArmorEvents::getKnockbackResistance).max(Comparator.naturalOrder());
        max.ifPresent(knockbackResistance -> {
                event.setStrength(event.getStrength() * (1-knockbackResistance));
        });
    }

    private static float getKnockbackResistance(ItemStack stack) {
        if (stack.getItem() instanceof IArmor) {
            return ((IArmor) stack.getItem()).getKnockbackResistance();
        }
        return 0;
    }


}
