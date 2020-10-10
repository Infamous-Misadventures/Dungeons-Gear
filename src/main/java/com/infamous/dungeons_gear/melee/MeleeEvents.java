package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.items.WeaponList.FIREBRAND;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class MeleeEvents {


    @SubscribeEvent
    public static void onMeleeDamage(LivingDamageEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            LivingEntity victim = event.getEntityLiving();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            if(mainhand.getItem() == FIREBRAND){
                int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, mainhand);
                victim.setFire(4 + fireAspectLevel * 4);
            }
            else if (attacker.getHeldItemMainhand().getItem() == WeaponList.GRAVE_BANE) {
                if(victim.isEntityUndead()){
                    float currentDamage = event.getAmount();
                    event.setAmount(currentDamage + 2.5f);
                }
            }
            else if (attacker.getHeldItemMainhand().getItem() == WeaponList.DARK_KATANA) {
                if(victim.isEntityUndead()){
                    float currentDamage = event.getAmount();
                    event.setAmount(currentDamage + 2.5f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onClaymoreAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            if(event.getEntityLiving() == null) return;
            LivingEntity victim = (LivingEntity) event.getEntityLiving();
            if ((attacker.getHeldItemMainhand().getItem()  instanceof ClaymoreItem)
                    && !ModEnchantmentHelper.hasEnchantment(attacker.getHeldItemMainhand(), Enchantments.KNOCKBACK)) {
                if(attacker instanceof PlayerEntity){
                    PlayerEntity playerEntity = (PlayerEntity) attacker;
                    float cooledAttackStrength = playerEntity.getCooledAttackStrength(0.5F);
                    boolean atFullAttackStrength = cooledAttackStrength > 0.9F;
                    float attackKnockbackStrength = 1;
                    if (playerEntity.isSprinting() && atFullAttackStrength) {
                        playerEntity.world.playSound((PlayerEntity)null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, playerEntity.getSoundCategory(), 1.0F, 1.0F);
                        ++attackKnockbackStrength;
                    }
                    victim.func_233627_a_(attackKnockbackStrength * 0.5F, (double) MathHelper.sin(playerEntity.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(playerEntity.rotationYaw * ((float)Math.PI / 180F))));
                    playerEntity.setMotion(playerEntity.getMotion().mul(0.6D, 1.0D, 0.6D));

                }
                else if(attacker instanceof MobEntity){
                    MobEntity mobEntity = (MobEntity) attacker;
                    float attackKnockbackStrength = (float)mobEntity.func_233637_b_(Attributes.field_233824_g_);
                    attackKnockbackStrength += 1;
                    if (attackKnockbackStrength > 0.0F) {
                        victim.func_233627_a_(attackKnockbackStrength * 0.5F, (double)MathHelper.sin(mobEntity.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(mobEntity.rotationYaw * ((float)Math.PI / 180F))));
                        mobEntity.setMotion(mobEntity.getMotion().mul(0.6D, 1.0D, 0.6D));
                    }
                }
            }
        }
    }

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

    @SubscribeEvent
    public static void onFortuneSpearLooting(LootingLevelEvent event){
        if(event.getDamageSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getDamageSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getDamageSource().getTrueSource();
            int lootingLevel = event.getLootingLevel();
            if(attacker.getHeldItemMainhand().getItem() == WeaponList.FORTUNE_SPEAR){
                event.setLootingLevel(lootingLevel + 1);
            }
        }
    }
}
