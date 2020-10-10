package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import net.minecraft.enchantment.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.WeaponList.GROWING_STAFF;
import static com.infamous.dungeons_gear.items.WeaponList.TRUTHSEEKER;

@Mod.EventBusSubscriber(modid= MODID)
public class CommittedEnchantment extends DamageBoostEnchantment {

    public CommittedEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment);
    }

    @SubscribeEvent
    public static void onCommittedDamage(LivingDamageEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            LivingEntity victim = (LivingEntity) event.getSource().getTrueSource();
            if(!(victim.getHealth() < victim.getMaxHealth())) return;
            ItemStack mainhand = attacker.getHeldItemMainhand();
            boolean uniqueWeaponFlag = mainhand.getItem() == TRUTHSEEKER || mainhand.getItem() == GROWING_STAFF;
            if((ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.COMMITTED)) || uniqueWeaponFlag){
                int committedLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.COMMITTED, mainhand);
                float victimRemainingHealth = victim.getHealth() / victim.getMaxHealth();
                float originalDamage = event.getAmount();
                float extraDamageMultiplier;
                // If normal damage is X, the same weapon with Tier 3 Committed adds an extra (X * (1 - (Mob Remaining HP/Mob Max HP))) damage.
                extraDamageMultiplier = 0.25F + committedLevel * 0.25F;
                if(uniqueWeaponFlag) extraDamageMultiplier += 0.5F;
                float extraDamage = (originalDamage * (1 - victimRemainingHealth)) * extraDamageMultiplier;
                event.setAmount(originalDamage + extraDamage);
            }
        }
    }
}
