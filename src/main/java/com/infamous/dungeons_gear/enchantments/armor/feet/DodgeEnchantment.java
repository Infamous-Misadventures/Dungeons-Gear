package com.infamous.dungeons_gear.enchantments.armor.feet;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.registry.EnchantmentInit.DODGE;

@Mod.EventBusSubscriber(modid = MODID)
public class DodgeEnchantment extends JumpingEnchantment {

    public DodgeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(DODGE.get(), victim);
        float negateHitChance = (float) (DungeonsGearConfig.DODGE_CHANCE_PER_LEVEL.get() * enchantmentLevel);

        float negateHitRand = victim.getRandom().nextFloat();
        if (negateHitRand <= negateHitChance) {
            event.setCanceled(true);
        }
    }

}
