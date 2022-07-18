package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class OpulentShieldEnchantment extends FocusEnchantment {

    public OpulentShieldEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event){
        Player player = event.getPlayer();
        if(player.level.isClientSide) return;
        int opulentShieldLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.OPULENT_SHIELD, player);
        int invulnerableTime = 20 * opulentShieldLevel;
        if(player.invulnerableTime >= invulnerableTime) return;
        player.invulnerableTime = invulnerableTime;
    }

}
