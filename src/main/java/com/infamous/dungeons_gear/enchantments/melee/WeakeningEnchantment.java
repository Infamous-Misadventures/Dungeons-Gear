package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.WEAKENING_DISTANCE;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.WEAKENING_DURATION;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.*;

@Mod.EventBusSubscriber(modid = MODID)
public class WeakeningEnchantment extends DungeonsEnchantment {

    public WeakeningEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        applyToNearbyEntities(target, target.level, WEAKENING_DISTANCE.get(),
                getCanApplyToSecondEnemyPredicate(user, (LivingEntity)target), (LivingEntity nearbyEntity) -> {
                    nearbyEntity.addEffect(new EffectInstance(Effects.WEAKNESS, WEAKENING_DURATION.get(), level-1));
                }
        );
    }
}
