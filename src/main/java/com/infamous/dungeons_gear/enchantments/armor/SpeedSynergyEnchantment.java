package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.ArtifactEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class SpeedSynergyEnchantment extends ArtifactEnchantment {

    public SpeedSynergyEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof ArtifactEnchantment);
    }

    @SubscribeEvent
    public static void onArtifactUsed(PlayerInteractEvent.RightClickItem event){
        PlayerEntity player = event.getPlayer();
        Hand activeHand = event.getHand();
        ItemStack itemStack = player.getHeldItem(activeHand);
        if(itemStack.getItem() instanceof IArtifact){
            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.SPEED_SYNERGY)){
                int speedSynergyLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.SPEED_SYNERGY, player);
                EffectInstance speedBoost = new EffectInstance(Effects.SPEED, 20 * speedSynergyLevel);
                player.addPotionEffect(speedBoost);
            }
        }
    }
}
