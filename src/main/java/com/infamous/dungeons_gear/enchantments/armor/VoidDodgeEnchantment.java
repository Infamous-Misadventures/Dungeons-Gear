package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.ARROW_HOARDER;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.VOID_DODGE;

@Mod.EventBusSubscriber(modid= MODID)
public class VoidDodgeEnchantment extends JumpingEnchantment {

    public VoidDodgeEnchantment() {
        super(Rarity.UNCOMMON, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        LivingEntity victim = event.getEntityLiving();
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(VOID_DODGE, victim);
        float totalTeleportChance = 0.05F*enchantmentLevel;

        float teleportRand = victim.getRandom().nextFloat();
        if (teleportRand <= totalTeleportChance) {
            ArmorEffectHelper.teleportOnHit(victim);
        }
    }

}
