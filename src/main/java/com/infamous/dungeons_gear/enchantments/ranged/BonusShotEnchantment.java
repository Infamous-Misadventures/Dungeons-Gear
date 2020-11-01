package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
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
                boolean uniqueWeaponFlag = stack.getItem() == DeferredItemInit.BUTTERFLY_CROSSBOW.get();
                if(ModEnchantmentHelper.hasEnchantment(stack, RangedEnchantmentList.BONUS_SHOT) || uniqueWeaponFlag){
                    int bonusShotLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.BONUS_SHOT, stack);
                    float damageMultiplier;
                    damageMultiplier = 0.1F + (bonusShotLevel-1 * 0.07F);
                    if(uniqueWeaponFlag) damageMultiplier += 0.1F;
                    float arrowVelocity = RangedAttackHelper.getvVanillaOrModdedCrossbowArrowVelocity(stack);
                    ProjectileEffectHelper.fireBonusShotTowardsOtherEntity(player, 10,
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
        boolean uniqueWeaponFlag = stack.getItem() == DeferredItemInit.TWIN_BOW.get()
                || stack.getItem() == DeferredItemInit.HAUNTED_BOW.get();
        if(ModEnchantmentHelper.hasEnchantment(stack, RangedEnchantmentList.BONUS_SHOT) || uniqueWeaponFlag){
            int bonusShotLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.BONUS_SHOT, stack);
            float damageMultiplier;
            damageMultiplier = 0.1F + (bonusShotLevel-1 * 0.07F);
            if(uniqueWeaponFlag) damageMultiplier += 0.1F;
            float arrowVelocity = RangedAttackHelper.getVanillaOrModdedBowArrowVelocity(stack, charge);
            if(arrowVelocity >= 0.1F){
                ProjectileEffectHelper.fireBonusShotTowardsOtherEntity(livingEntity, 10, damageMultiplier, arrowVelocity);
            }
        }
    }
}
