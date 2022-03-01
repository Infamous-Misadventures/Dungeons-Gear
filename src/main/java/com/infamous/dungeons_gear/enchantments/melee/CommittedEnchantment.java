package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.COMMITTED_BASE_MULTIPLIER;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.COMMITTED_MULTIPLIER_PER_LEVEL;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class CommittedEnchantment extends DamageBoostEnchantment {

    public CommittedEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }

    @SubscribeEvent
    public static void onCommittedDamage(LivingDamageEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntityLiving();
            if(victim.getHealth() >= victim.getMaxHealth()) return;
            ItemStack mainhand = attacker.getMainHandItem();
            if((ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.COMMITTED))){
                int committedLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.COMMITTED, mainhand);
                float victimRemainingHealth = victim.getHealth() / victim.getMaxHealth();
                float originalDamage = event.getAmount();
                // If normal damage is X, the same weapon with Tier 3 Committed adds an extra (X * (1 - (Mob Remaining HP/Mob Max HP))) damage.
                float extraDamageMultiplier = (float) (COMMITTED_BASE_MULTIPLIER.get() + committedLevel * COMMITTED_MULTIPLIER_PER_LEVEL.get());
                event.setAmount(originalDamage * (1 + ((extraDamageMultiplier - 1) * (1 - victimRemainingHealth))));
            }
        }
    }

}
