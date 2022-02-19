package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class MeleeEvents {

    @SubscribeEvent
    public static void onMeleeDamage(LivingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if (event.getSource() instanceof OffhandAttackDamageSource) return;
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntityLiving();
            ItemStack mainhand = attacker.getMainHandItem();
            if (hasFireAspectBuiltIn(mainhand)) {
                int fireAspectLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, mainhand);
                victim.setSecondsOnFire(4 + fireAspectLevel * 4);
            } else if (hasSmiteBuiltIn(mainhand)) {
                if (victim.isInvertedHealAndHarm()) {
                    float currentDamage = event.getAmount();
                    event.setAmount(currentDamage + 2.5f);
                }
            } else if (hasIllagersBaneBuiltIn(mainhand)) {
                if (victim.getMobType() == CreatureAttribute.ILLAGER) {
                    float currentDamage = event.getAmount();
                    event.setAmount(currentDamage + 2.5f);
                }
            }
        }
    }

    private static boolean hasFireAspectBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasFireAspectBuiltIn(mainhand);
    }

    private static boolean hasSmiteBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasSmiteBuiltIn(mainhand);
    }

    private static boolean hasIllagersBaneBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasIllagersBaneBuiltIn(mainhand);
    }

    @SubscribeEvent
    public static void dualWield(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlotType.OFFHAND) {
            final ItemStack outgoing = event.getTo();
            if (outgoing.getItem() instanceof IDualWieldWeapon && event.getEntityLiving().getOffhandItem().getItem() instanceof IDualWieldWeapon) {
                ((IDualWieldWeapon) event.getEntityLiving().getOffhandItem().getItem()).updateOff(event.getEntityLiving(), event.getEntityLiving().getOffhandItem());
            }
        }else if (event.getSlot() == EquipmentSlotType.MAINHAND) {
            final ItemStack incoming = event.getTo();
            if (incoming.getItem() instanceof IDualWieldWeapon) {
                ((IDualWieldWeapon) incoming.getItem()).updateMain(event.getEntityLiving(), incoming);
            }
        }
    }

//    @SubscribeEvent
//    public static void onClaymoreAttack(LivingAttackEvent event) {
//        if (event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
//        if (event.getSource() instanceof OffhandAttackDamageSource) return;
//        if (event.getSource().getEntity() instanceof LivingEntity) {
//            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
//            if (event.getEntityLiving() == null) return;
//            LivingEntity victim = (LivingEntity) event.getEntityLiving();
//            if ((attacker.getMainHandItem().getItem() instanceof ClaymoreItem)
//                    && !ModEnchantmentHelper.hasEnchantment(attacker.getMainHandItem(), Enchantments.KNOCKBACK)) {
//                if (attacker instanceof PlayerEntity) {
//                    PlayerEntity playerEntity = (PlayerEntity) attacker;
//                    float cooledAttackStrength = playerEntity.getAttackStrengthScale(0.5F);
//                    boolean atFullAttackStrength = cooledAttackStrength > 0.9F;
//                    float attackKnockbackStrength = 1;
//                    if (playerEntity.isSprinting() && atFullAttackStrength) {
//                        SoundHelper.playKnockbackSound(playerEntity);
//                        ++attackKnockbackStrength;
//                    }
//                    victim.knockback(attackKnockbackStrength * 0.5F, (double) MathHelper.sin(playerEntity.yRot * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(playerEntity.yRot * ((float) Math.PI / 180F))));
//                    playerEntity.setDeltaMovement(playerEntity.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
//
//                } else if (attacker instanceof MobEntity) {
//                    MobEntity mobEntity = (MobEntity) attacker;
//                    float attackKnockbackStrength = (float) mobEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
//                    attackKnockbackStrength += 1;
//                    if (attackKnockbackStrength > 0.0F) {
//                        victim.knockback(attackKnockbackStrength * 0.5F, (double) MathHelper.sin(mobEntity.yRot * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(mobEntity.yRot * ((float) Math.PI / 180F))));
//                        mobEntity.setDeltaMovement(mobEntity.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
//                    }
//                }
//            }
//        }
//    }

    // TODO: There is no longer an attacker passed into applyKnockback
    /*
    @SubscribeEvent
    public static void onClaymoreKnockback(LivingKnockBackEvent event){
        if(event.getAttacker() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getAttacker();
            if (attacker.getHeldItemMainhand().getItem() instanceof ClaymoreItem
                            && ModEnchantmentHelper.hasEnchantment(attacker.getHeldItemMainhand(), Enchantments.KNOCKBACK)) {
                float knockbackStrength = event.getStrength();
                event.setStrength(knockbackStrength + 0.5f);
            }
        }
    }

     */

    @SubscribeEvent
    public static void onFortuneSpearLooting(LootingLevelEvent event) {
        if (event.getDamageSource() == null) return; // should fix Scaling Health bug
        if (event.getDamageSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if (event.getDamageSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getDamageSource().getEntity();
            int lootingLevel = event.getLootingLevel();
            if (hasFortuneBuiltIn(attacker.getMainHandItem())) {
                event.setLootingLevel(lootingLevel + 1);
            }
        }
    }

    private static boolean hasFortuneBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasFortuneBuiltIn(mainhand);
    }
}
