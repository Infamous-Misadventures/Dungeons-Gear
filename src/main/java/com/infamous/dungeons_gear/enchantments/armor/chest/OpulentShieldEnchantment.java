package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class OpulentShieldEnchantment extends FocusEnchantment {

    public OpulentShieldEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, ARMOR_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event){
        PlayerEntity player = event.getPlayer();
        if(player.level.isClientSide) return;
        int opulentShieldLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.OPULENT_SHIELD, player);
        int invulnerableTime = 20 * opulentShieldLevel;
        if(player.invulnerableTime >= invulnerableTime) return;
        player.invulnerableTime = invulnerableTime;
    }

}
