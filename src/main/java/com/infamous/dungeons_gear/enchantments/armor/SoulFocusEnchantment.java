package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class SoulFocusEnchantment extends FocusEnchantment {

    public SoulFocusEnchantment() {
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
    public static void onMagicAttack(LivingDamageEvent event){
        if(!isIndirectMagic(event.getSource())) return; // we don't want this to trigger via generic magic damage
        if(event.getEntityLiving().level.isClientSide) return;

        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            int soulFocusLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.SOUL_FOCUS, attacker);
            if(soulFocusLevel > 0){
                float multiplier = 1 + (0.1F * soulFocusLevel);
                float currentDamage = event.getAmount();
                event.setAmount(currentDamage * multiplier);
            }
        }
    }

    private static boolean isIndirectMagic(DamageSource damageSource) {
        return damageSource instanceof IndirectEntityDamageSource && damageSource.isMagic();
    }
}
