package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.COMMITTED_BASE_MULTIPLIER;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.COMMITTED_MULTIPLIER_PER_LEVEL;

@Mod.EventBusSubscriber(modid= MODID)
public class CommittedEnchantment extends DamageBoostEnchantment {

    public CommittedEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlot[]{
            EquipmentSlot.MAINHAND});
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
        if(event.getSource().getDirectEntity() instanceof AbstractArrow) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntity();
            if(victim.getHealth() >= victim.getMaxHealth()) return;
            ItemStack mainhand = attacker.getMainHandItem();
            if((ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.COMMITTED.get()))){
                int committedLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.COMMITTED.get(), mainhand);
                float victimRemainingHealth = victim.getHealth() / victim.getMaxHealth();
                float originalDamage = event.getAmount();
                // If normal damage is X, the same weapon with Tier 3 Committed adds an extra (X * (1 - (Mob Remaining HP/Mob Max HP))) damage.
                float extraDamageMultiplier = (float) (COMMITTED_BASE_MULTIPLIER.get() + committedLevel * COMMITTED_MULTIPLIER_PER_LEVEL.get());
                event.setAmount(originalDamage * (1 + ((extraDamageMultiplier - 1) * (1 - victimRemainingHealth))));
            }
        }
    }

}
