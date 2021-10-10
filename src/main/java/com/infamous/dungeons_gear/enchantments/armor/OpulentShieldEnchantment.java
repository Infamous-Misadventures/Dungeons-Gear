package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.StreamSupport;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class OpulentShieldEnchantment extends FocusEnchantment {

    public OpulentShieldEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event){
        PlayerEntity player = event.getPlayer();
        if(player.level.isClientSide) return;
        boolean armorFlag = StreamSupport.stream(player.getArmorSlots().spliterator(), false).anyMatch(OpulentShieldEnchantment::hasOpulentShieldBuiltIn);
        int opulentShieldLevel = Math.max(EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.OPULENT_SHIELD, player), armorFlag ? 1 : 0);
        player.invulnerableTime = player.invulnerableTime + 20*opulentShieldLevel;
    }

    private static boolean hasOpulentShieldBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IArmor && ((IArmor) stack.getItem()).hasOpulentShieldBuiltIn(stack);
    }
}
