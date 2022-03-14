package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class CooldownShotEnchantment extends DungeonsEnchantment {

    public CooldownShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onCooldownBowFired(ArrowLooseEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        int charge = event.getCharge();
        ItemStack stack = event.getBow();
        if(livingEntity instanceof PlayerEntity && !event.getWorld().isClientSide){
            PlayerEntity player = (PlayerEntity) livingEntity;
            float arrowVelocity = RangedAttackHelper.getVanillaOrModdedBowArrowVelocity(livingEntity, stack, charge);
            if(arrowVelocity >= 1.0F){
                int cooldownShotLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.COOLDOWN_SHOT, stack);
                if(cooldownShotLevel > 0){
                    double cooldownReduction = 0.5 * cooldownShotLevel;
                    ArtifactItem.reduceArtifactCooldowns(player, cooldownReduction);
                }
            }
        }
    }
}
