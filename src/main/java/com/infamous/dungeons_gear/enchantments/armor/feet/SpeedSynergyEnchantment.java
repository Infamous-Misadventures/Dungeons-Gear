package com.infamous.dungeons_gear.enchantments.armor.feet;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.ArtifactEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.event.ArtifactEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class SpeedSynergyEnchantment extends ArtifactEnchantment {

    public SpeedSynergyEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof ArtifactEnchantment);
    }

    @SubscribeEvent
    public static void onArtifactTriggered(ArtifactEvent.Activated event){
        LivingEntity livingEntity = event.getEntity();
        if(ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.SPEED_SYNERGY.get())){
            int speedSynergyLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPEED_SYNERGY.get(), livingEntity);
            MobEffectInstance speedBoost = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * speedSynergyLevel);
            livingEntity.addEffect(speedBoost);
        }
    }
}
