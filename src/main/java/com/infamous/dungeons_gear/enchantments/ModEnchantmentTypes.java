package com.infamous.dungeons_gear.enchantments;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ModEnchantmentTypes {
    public static final EnchantmentType MELEE = addEnchantment("melee", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof SwordItem || item instanceof IMeleeWeapon) :
                            (item instanceof IMeleeWeapon)
    );

    public static final EnchantmentType MELEE_RANGED = addEnchantment("melee_ranged", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof SwordItem || item instanceof IMeleeWeapon || item instanceof BowItem || item instanceof CrossbowItem) :
                            (item instanceof IMeleeWeapon || item instanceof IRangedWeapon)
    );

    public static final EnchantmentType RANGED = addEnchantment("ranged", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof BowItem || item instanceof CrossbowItem) :
                            (item instanceof IRangedWeapon)
    );


    public static final EnchantmentType ARMOR = addEnchantment("armor", item ->
            DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                    (item instanceof ArmorItem) :
                    (item instanceof IArmor)
    );

    public static final EnchantmentType ARMOR_RANGED = addEnchantment("armor_ranged", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof BowItem || item instanceof CrossbowItem || item instanceof ArmorItem) :
                            (item instanceof IRangedWeapon || item instanceof IArmor)
    );

    public static final EquipmentSlotType[] WEAPON_SLOT = new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND};

    public static final EquipmentSlotType[] ARMOR_SLOT = new EquipmentSlotType[]{
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET};

    public static final EquipmentSlotType[] ARMOR_RANGED_SLOT = new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND,
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET};

    @Nonnull
    public static EnchantmentType addEnchantment(String name, Predicate<Item> condition) {
        return EnchantmentType.create(name, condition);
    }
}
