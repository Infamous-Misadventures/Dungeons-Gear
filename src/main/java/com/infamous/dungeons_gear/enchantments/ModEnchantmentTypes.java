package com.infamous.dungeons_gear.enchantments;

import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.IRangedWeapon;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ModEnchantmentTypes {
    public static final EnchantmentType MELEE = addEnchantment("melee", item ->
            item instanceof SwordItem
                    || item instanceof IMeleeWeapon);
    public static final EnchantmentType MELEE_RANGED = addEnchantment("melee_ranged", item ->
            item instanceof SwordItem
                    || item instanceof IMeleeWeapon
                    || item instanceof BowItem
                    || item instanceof CrossbowItem);
    public static final EnchantmentType RANGED = addEnchantment("ranged", item ->
            item instanceof BowItem
            || item instanceof CrossbowItem);
    public static final EnchantmentType CHEST_RANGED = addEnchantment("ranged",
            item -> item instanceof BowItem
            || item instanceof CrossbowItem
            || (item instanceof ArmorItem && ((ArmorItem)item).getEquipmentSlot() == EquipmentSlotType.CHEST));

    @Nonnull
    public static EnchantmentType addEnchantment(String name, Predicate<Item> condition) {
        return EnchantmentType.create(name, condition);
    }
}
