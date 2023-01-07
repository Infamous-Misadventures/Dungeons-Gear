package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class OpulentShieldEnchantment extends FocusEnchantment {

    public OpulentShieldEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        if (player.level.isClientSide) return;
        int opulentShieldLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.OPULENT_SHIELD.get(), player);
        int invulnerableTime = 20 * opulentShieldLevel;
        if (player.invulnerableTime >= invulnerableTime) return;
        player.invulnerableTime = invulnerableTime;
    }

}
