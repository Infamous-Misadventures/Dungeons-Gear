package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_libraries.utils.RangedAttackHelper.getVanillaOrModdedCrossbowArrowVelocity;

@Mod.EventBusSubscriber(modid = MODID)
public class BonusShotEnchantment extends DungeonsEnchantment {

    public BonusShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onCrossbowFired(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof CrossbowItem) {
            if (CrossbowItem.isCharged(stack)) {
                if (ModEnchantmentHelper.hasEnchantment(stack, EnchantmentInit.BONUS_SHOT.get())) {
                    int bonusShotLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.BONUS_SHOT.get(), stack);
                    float damageMultiplier;
                    damageMultiplier = 0.1F + (bonusShotLevel - 1 * 0.07F);
                    float arrowVelocity = getVanillaOrModdedCrossbowArrowVelocity(player, stack);
                    ProjectileEffectHelper.fireBonusShotTowardsOtherEntity(player, 10,
                            damageMultiplier, arrowVelocity);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBowFired(ArrowLooseEvent event) {
        LivingEntity livingEntity = event.getEntity();
        ItemStack stack = event.getBow();
        int charge = event.getCharge();
        if (ModEnchantmentHelper.hasEnchantment(stack, EnchantmentInit.BONUS_SHOT.get())) {
            int bonusShotLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.BONUS_SHOT.get(), stack);
            float damageMultiplier;
            damageMultiplier = 0.1F + (bonusShotLevel - 1 * 0.07F);
            float arrowVelocity = RangedAttackHelper.getArrowVelocity(livingEntity, stack, charge);
            if (arrowVelocity >= 0.1F) {
                ProjectileEffectHelper.fireBonusShotTowardsOtherEntity(livingEntity, 10, damageMultiplier, arrowVelocity);
            }
        }
    }
}
