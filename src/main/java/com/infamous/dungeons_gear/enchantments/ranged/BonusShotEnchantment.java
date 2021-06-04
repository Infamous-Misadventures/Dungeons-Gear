package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class BonusShotEnchantment extends DungeonsEnchantment {

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
                boolean uniqueWeaponFlag = hasBonusShotBuiltIn(stack);
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

    private static boolean hasBonusShotBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon) stack.getItem()).hasBonusShotBuiltIn(stack);
    }

    @SubscribeEvent
    public static void onBowFired(ArrowLooseEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        ItemStack stack = event.getBow();
        int charge = event.getCharge();
        boolean uniqueWeaponFlag = hasBonusShotBuiltIn(stack);
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
