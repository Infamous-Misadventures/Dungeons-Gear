package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
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
import static com.infamous.dungeons_libraries.utils.RangedAttackHelper.getVanillaOrModdedCrossbowArrowVelocity;

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
                if(ModEnchantmentHelper.hasEnchantment(stack, RangedEnchantmentList.BONUS_SHOT)){
                    int bonusShotLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.BONUS_SHOT, stack);
                    float damageMultiplier;
                    damageMultiplier = 0.1F + (bonusShotLevel-1 * 0.07F);
                    float arrowVelocity = getVanillaOrModdedCrossbowArrowVelocity(player, stack);
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
        if(ModEnchantmentHelper.hasEnchantment(stack, RangedEnchantmentList.BONUS_SHOT)){
            int bonusShotLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.BONUS_SHOT, stack);
            float damageMultiplier;
            damageMultiplier = 0.1F + (bonusShotLevel-1 * 0.07F);
            float arrowVelocity = RangedAttackHelper.getArrowVelocity(livingEntity, stack, charge);
            if(arrowVelocity >= 0.1F){
                ProjectileEffectHelper.fireBonusShotTowardsOtherEntity(livingEntity, 10, damageMultiplier, arrowVelocity);
            }
        }
    }
}
