package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.ArtifactEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.event.ArtifactEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class HealthSynergyEnchantment extends ArtifactEnchantment {

    public HealthSynergyEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
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
        if(ModEnchantmentHelper.hasEnchantment(livingEntity, EnchantmentInit.HEALTH_SYNERGY.get())){
            int healthSynergyLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.HEALTH_SYNERGY.get(), livingEntity);
            livingEntity.heal(0.2F + (0.1F * healthSynergyLevel));
        }
    }
}
