package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

public class IllagersBaneEnchantment extends DungeonsEnchantment {

    public IllagersBaneEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, ModEnchantmentTypes.WEAPON_SLOT);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMinCost(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 20;
    }

    /**
     * Calculates the additional damage that will be dealt by an item with this enchantment. This alternative to
     * calcModifierDamage is sensitive to the targets EnumCreatureAttribute.
     */
    @Override
    public float getDamageBonus(int level, MobType creatureType) {
        if (creatureType == MobType.ILLAGER) {
            return (float) level * 2.5F;
        } else {
            return 0.0F;
        }
    }

    @Override
    public boolean checkCompatibility(Enchantment ench) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get()
                || (!(ench instanceof DamageEnchantment) && !(ench instanceof IllagersBaneEnchantment));
    }
}
