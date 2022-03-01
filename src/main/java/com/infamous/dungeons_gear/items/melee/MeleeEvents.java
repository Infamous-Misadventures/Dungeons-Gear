package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class MeleeEvents {

    @SubscribeEvent
    public static void onMeleeDamage(LivingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if (event.getSource() instanceof OffhandAttackDamageSource) return;
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntityLiving();
            ItemStack mainhand = attacker.getMainHandItem();
            if (hasFireAspectBuiltIn(mainhand)) {
                int fireAspectLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, mainhand);
                victim.setSecondsOnFire(4 + fireAspectLevel * 4);
            }
        }
    }

    private static boolean hasFireAspectBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasFireAspectBuiltIn(mainhand);
    }

    @SubscribeEvent
    public static void dualWield(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlotType.OFFHAND) {
            final ItemStack outgoing = event.getTo();
            if (outgoing.getItem() instanceof IDualWieldWeapon && event.getEntityLiving().getOffhandItem().getItem() instanceof IDualWieldWeapon) {
                ((IDualWieldWeapon) event.getEntityLiving().getOffhandItem().getItem()).updateOff(event.getEntityLiving(), event.getEntityLiving().getOffhandItem());
            }
        }else if (event.getSlot() == EquipmentSlotType.MAINHAND) {
            final ItemStack incoming = event.getTo();
            if (incoming.getItem() instanceof IDualWieldWeapon) {
                ((IDualWieldWeapon) incoming.getItem()).updateMain(event.getEntityLiving(), incoming);
            }
        }
    }
}
