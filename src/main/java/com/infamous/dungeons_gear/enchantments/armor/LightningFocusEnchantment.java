package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class LightningFocusEnchantment extends FocusEnchantment {

    public LightningFocusEnchantment() {
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
    public static void onLightningAttack(LivingDamageEvent event){
        if(!(event.getSource() instanceof ElectricShockDamageSource)) return;
        if(event.getEntityLiving().world.isRemote) return;

        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            int lightningFocusLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.LIGHTNING_FOCUS, attacker);
            if(lightningFocusLevel > 0){
                float multiplier = 1 + (0.25F * lightningFocusLevel);
                float currentDamage = event.getAmount();
                event.setAmount(currentDamage * multiplier);
            }
        }
    }
}
