package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.ranged.bows.DungeonsBowItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.infamous.dungeons_gear.items.RangedWeaponList.BOW_OF_LOST_SOULS;
import static com.infamous.dungeons_gear.items.RangedWeaponList.WINTERS_TOUCH;
import static com.infamous.dungeons_gear.ranged.bows.DungeonsBowItem.getArrowVelocity;

public class VolleyEnchantment extends Enchantment {

    public VolleyEnchantment() {
        super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment == Enchantments.INFINITY);
    }

}
