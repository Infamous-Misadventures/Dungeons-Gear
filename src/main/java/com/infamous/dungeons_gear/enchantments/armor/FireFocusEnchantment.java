package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class FireFocusEnchantment extends FocusEnchantment {

    public FireFocusEnchantment() {
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
    public static void onFireAttack(LivingDamageEvent event){
        if(event.getSource() == DamageSource.ON_FIRE) return; // ON_FIRE is applied when you set something on fire
        if(event.getEntityLiving().world.isRemote) return;

        LivingEntity attacker = event.getEntityLiving().getAttackingEntity();
        if(attacker != null){
            int fireFocusLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.FIRE_FOCUS, attacker);
            if(fireFocusLevel > 0){
                float multiplier = 1 + (0.25F * fireFocusLevel);
                float currentDamage = event.getAmount();
                event.setAmount(currentDamage * multiplier);
            }
        }
    }
}
