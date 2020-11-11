package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.*;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.HealingEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

@Mod.EventBusSubscriber(modid= MODID)
public class AnimaConduitEnchantment extends HealingEnchantment {
    public AnimaConduitEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealingEnchantment);
    }

    @SubscribeEvent
    public static void onAnimaConduitKill(LivingDeathEvent event){
        if(event.getSource() instanceof IndirectEntityDamageSource){
            IndirectEntityDamageSource indirectEntityDamageSource = (IndirectEntityDamageSource) event.getSource();
            if(indirectEntityDamageSource.getImmediateSource() instanceof AbstractArrowEntity) {
                AbstractArrowEntity arrowEntity = (AbstractArrowEntity) indirectEntityDamageSource.getImmediateSource();
                int animaConduitLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrowEntity, MeleeRangedEnchantmentList.ANIMA_CONDUIT);
                if(animaConduitLevel > 0){
                    LivingEntity victim = event.getEntityLiving();
                    if(victim instanceof MobEntity){
                        MobEntity mobEntity = (MobEntity) victim;
                        if (indirectEntityDamageSource.getTrueSource() instanceof LivingEntity) {
                            LivingEntity attacker = (LivingEntity) indirectEntityDamageSource.getTrueSource();
                            if(attacker instanceof PlayerEntity){
                                PlayerEntity playerEntity = (PlayerEntity) attacker;
                                int experiencePoints = getDroppedExperience(mobEntity, playerEntity);
                                double soulsToHealth = experiencePoints * (0.01 * animaConduitLevel);
                                PROXY.spawnParticles(playerEntity, ParticleTypes.ENTITY_EFFECT);
                                playerEntity.heal((float)soulsToHealth);
                            }
                        }
                    }
                }
            }
            else if(event.getSource().getTrueSource() instanceof LivingEntity){
                LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                LivingEntity victim = event.getEntityLiving();
                ItemStack mainhand = attacker.getHeldItemMainhand();
                if(victim instanceof MobEntity){
                    MobEntity mobEntity = (MobEntity) victim;
                    if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.ANIMA_CONDUIT)) {
                        int animaConduitLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.ANIMA_CONDUIT, mainhand);
                        if(attacker instanceof PlayerEntity){
                            PlayerEntity playerEntity = (PlayerEntity) attacker;
                            int experiencePoints = getDroppedExperience(mobEntity, playerEntity);
                            double soulsToHealth = experiencePoints * (0.01 * animaConduitLevel);
                            PROXY.spawnParticles(playerEntity, ParticleTypes.ENTITY_EFFECT);
                            playerEntity.heal((float)soulsToHealth);
                        }
                    }
                }
            }
        }
    }

    private static int getDroppedExperience(MobEntity mobEntity, PlayerEntity player) {
        try {
            Method m = ObfuscationReflectionHelper.findMethod(MobEntity.class,"func_70693_a", PlayerEntity.class);
            return (int) m.invoke(mobEntity, player);
        }
        catch (Exception e) {
            DungeonsGear.LOGGER.error("Reflection error ", e);
            return 0;
        }
    }
}
