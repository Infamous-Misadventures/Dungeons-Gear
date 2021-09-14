package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class PoisonFocusEnchantment extends FocusEnchantment {

    public PoisonFocusEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPoisonAttack(LivingDamageEvent event){
        if(event.getSource() != DamageSource.MAGIC) return; // Poison effect applies this specific damage source
        if(event.getEntityLiving().world.isRemote) return;

        LivingEntity victim = event.getEntityLiving();
        if(victim.getActivePotionEffect(Effects.POISON) != null){
            LivingEntity attacker = victim.getAttackingEntity();
            if(attacker != null){
                int poisonFocusLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.POISON_FOCUS, attacker);
                if(poisonFocusLevel > 0){
                    float multiplier = 1 + (0.25F * poisonFocusLevel);
                    float currentDamage = event.getAmount();
                    event.setAmount(currentDamage * multiplier);
                }
            }
        }
    }
}
