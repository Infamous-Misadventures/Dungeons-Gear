package com.infamous.dungeons_gear.enchantments.armor.feet;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.ArtifactEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.event.ArtifactEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class SpeedSynergyEnchantment extends ArtifactEnchantment {

    public SpeedSynergyEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_FEET, ARMOR_SLOT);
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
        LivingEntity livingEntity = event.getEntityLiving();
        if(ModEnchantmentHelper.hasEnchantment(livingEntity, ArmorEnchantmentList.SPEED_SYNERGY)){
            int speedSynergyLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.SPEED_SYNERGY, livingEntity);
            EffectInstance speedBoost = new EffectInstance(Effects.MOVEMENT_SPEED, 20 * speedSynergyLevel);
            livingEntity.addEffect(speedBoost);
        }
    }
}
