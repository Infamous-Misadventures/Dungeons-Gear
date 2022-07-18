package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.WEAKENING_DISTANCE;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.WEAKENING_DURATION;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.*;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class WeakeningEnchantment extends DungeonsEnchantment {

    public WeakeningEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        LivingEntity livingTarget = (LivingEntity) target;
        livingTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, WEAKENING_DURATION.get(), level-1));
        applyToNearbyEntities(target, target.level, WEAKENING_DISTANCE.get(),
                getCanApplyToSecondEnemyPredicate(user, livingTarget), (LivingEntity nearbyEntity) -> {
                    nearbyEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, WEAKENING_DURATION.get(), level-1));
                }
        );
    }
}
