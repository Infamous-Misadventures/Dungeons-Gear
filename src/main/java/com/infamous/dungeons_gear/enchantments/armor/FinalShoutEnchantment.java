package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker;

public class FinalShoutEnchantment extends HealthAbilityEnchantment {

    public FinalShoutEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof HealthAbilityEnchantment);
    }

    @Override
    public void onUserHurt(LivingEntity user, Entity attacker, int level) {
        if(user instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) user;
            float currentHealth = player.getHealth();
            float maxHealth = player.getMaxHealth();
            if(currentHealth <= (0.25F * maxHealth)){
                CooldownTracker cooldownTracker = player.getCooldownTracker();
                for(Item item : cooldownTracker.cooldowns.keySet()){
                    if(item instanceof IArtifact){
                        cooldownTracker.removeCooldown(item);
                    }
                }
            }
        }
    }

    /*
    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent event){
        LivingEntity victim = event.getEntityLiving();
        if(victim != null && victim.isAlive()){
            if(victim instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) victim;
                float currentHealth = player.getHealth();
                float maxHealth = player.getMaxHealth();
                float damageDealt = event.getAmount();
                if(currentHealth - damageDealt <= (0.25F * maxHealth)){
                    if(EnchantUtils.hasEnchantment(player, ArmorEnchantmentList.FINAL_SHOUT)){
                        CooldownTracker cooldownTracker = player.getCooldownTracker();
                        for(Item item : cooldownTracker.cooldowns.keySet()){
                            if(item instanceof IArtifact){
                                cooldownTracker.removeCooldown(item);
                            }
                        }
                    }
                }
            }
        }
    }

     */
}
