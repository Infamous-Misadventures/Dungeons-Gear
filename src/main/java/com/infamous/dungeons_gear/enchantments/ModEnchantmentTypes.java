package com.infamous.dungeons_gear.enchantments;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_libraries.items.interfaces.IRangedWeapon;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ModEnchantmentTypes {
    public static final EnchantmentCategory MELEE = addEnchantment("dungeons_gear_melee", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof SwordItem || item instanceof TridentItem || item instanceof IMeleeWeapon) :
                            (item instanceof IMeleeWeapon)
    );

    public static final EnchantmentCategory MELEE_RANGED = addEnchantment("dungeons_gear_melee_ranged", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof SwordItem || item instanceof IMeleeWeapon || item instanceof TridentItem || item instanceof BowItem || item instanceof CrossbowItem) :
                            (item instanceof IMeleeWeapon || item instanceof IRangedWeapon)
    );

    public static final EnchantmentCategory RANGED = addEnchantment("dungeons_gear_ranged", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof BowItem || item instanceof CrossbowItem) :
                            (item instanceof IRangedWeapon)
    );


    public static final EnchantmentCategory BOW = addEnchantment("dungeons_gear_bow",
            item -> DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                    (item instanceof BowItem) : (item instanceof BowItem && item instanceof IRangedWeapon));


    public static final EnchantmentCategory ARMOR = addEnchantment("dungeons_gear_armor", item ->
            DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                    (item instanceof ArmorItem) :
                    (item instanceof ArmorGear)
    );

    public static final EnchantmentCategory ARMOR_RANGED = addEnchantment("dungeons_gear_armor_ranged", item ->
                    DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() ?
                            (item instanceof BowItem || item instanceof CrossbowItem || item instanceof ArmorItem) :
                            (item instanceof IRangedWeapon || item instanceof ArmorGear)
    );

    public static final EquipmentSlot[] WEAPON_SLOT = new EquipmentSlot[]{
            EquipmentSlot.MAINHAND};

    public static final EquipmentSlot[] ARMOR_SLOT = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET};

    public static final EquipmentSlot[] ARMOR_RANGED_SLOT = new EquipmentSlot[]{
            EquipmentSlot.MAINHAND,
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET};

    @Nonnull
    public static EnchantmentCategory addEnchantment(String name, Predicate<Item> condition) {
        return EnchantmentCategory.create(name, condition);
    }
}
