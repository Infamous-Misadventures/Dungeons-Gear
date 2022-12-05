package com.infamous.dungeons_gear.enchantments.armor.feet;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.registry.EnchantmentInit.VOID_DODGE;

@Mod.EventBusSubscriber(modid= MODID)
public class VoidDodgeEnchantment extends JumpingEnchantment {

    public VoidDodgeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(VOID_DODGE.get(), victim);
        float totalTeleportChance = (float) (DungeonsGearConfig.VOID_DODGE_CHANCE_PER_LEVEL.get() * enchantmentLevel);

        float teleportRand = victim.getRandom().nextFloat();
        if (teleportRand <= totalTeleportChance) {
            ArmorEffectHelper.teleportOnHit(victim);
        }
    }

}
