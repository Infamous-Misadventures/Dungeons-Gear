package com.infamous.dungeons_gear.enchantments.armor.feet;


import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.VOID_DODGE;

@Mod.EventBusSubscriber(modid= MODID)
public class VoidDodgeEnchantment extends JumpingEnchantment {

    public VoidDodgeEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_FEET, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        LivingEntity victim = event.getEntityLiving();
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(VOID_DODGE, victim);
        float totalTeleportChance = (float) (DungeonsGearConfig.VOID_DODGE_CHANCE_PER_LEVEL.get() * enchantmentLevel);

        float teleportRand = victim.getRandom().nextFloat();
        if (teleportRand <= totalTeleportChance) {
            ArmorEffectHelper.teleportOnHit(victim);
        }
    }

}
