package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.utilties.EnchantUtils;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.utilties.AbilityUtils;
import com.infamous.dungeons_gear.utilties.RangedUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

@Mod.EventBusSubscriber(modid= MODID)
public class BonusShotEnchantment extends Enchantment {

    public BonusShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onCrossbowFired(PlayerInteractEvent.RightClickItem event){
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getItemStack();
        if(stack.getItem() instanceof CrossbowItem){
            if(CrossbowItem.isCharged(stack)){
                boolean uniqueWeaponFlag = stack.getItem() == BUTTERFLY_CROSSBOW;
                if(EnchantUtils.hasEnchantment(stack, RangedEnchantmentList.BONUS_SHOT) || uniqueWeaponFlag){
                    int bonusShotLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.BONUS_SHOT, stack);
                    float damageMultiplier = 0;
                    if(bonusShotLevel == 1) damageMultiplier = 0.1F;
                    if(bonusShotLevel == 2) damageMultiplier = 0.71F;
                    if(bonusShotLevel == 3) damageMultiplier = 0.24F;
                    if(uniqueWeaponFlag) damageMultiplier += 0.2F;
                    float arrowVelocity = RangedUtils.getvVanillaOrModdedCrossbowArrowVelocity(stack);
                    AbilityUtils.fireBonusShotTowardsOtherEntity(player, 10,
                            damageMultiplier, arrowVelocity);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBowFired(ArrowLooseEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        ItemStack stack = event.getBow();
        int charge = event.getCharge();
        boolean uniqueWeaponFlag = stack.getItem() == TWIN_BOW;
        if(EnchantUtils.hasEnchantment(stack, RangedEnchantmentList.BONUS_SHOT) || uniqueWeaponFlag){
            int bonusShotLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.BONUS_SHOT, stack);
            float damageMultiplier = 0;
            if(bonusShotLevel == 1) damageMultiplier = 0.1F;
            if(bonusShotLevel == 2) damageMultiplier = 0.71F;
            if(bonusShotLevel == 3) damageMultiplier = 0.24F;
            if(uniqueWeaponFlag) damageMultiplier += 0.2F;
            float arrowVelocity = RangedUtils.getVanillaOrModdedBowArrowVelocity(stack, charge);
            if(arrowVelocity >= 0.1F){
                AbilityUtils.fireBonusShotTowardsOtherEntity(livingEntity, 10, damageMultiplier, arrowVelocity);
            }
        }
    }
}
