package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class MeleeEvents {

    @SubscribeEvent
    public static void dualWield(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlot.OFFHAND) {
            final ItemStack outgoing = event.getTo();
            if (outgoing.getItem() instanceof IDualWieldWeapon && event.getEntityLiving().getOffhandItem().getItem() instanceof IDualWieldWeapon) {
                ((IDualWieldWeapon) event.getEntityLiving().getOffhandItem().getItem()).updateOff(event.getEntityLiving(), event.getEntityLiving().getOffhandItem());
            }
        }else if (event.getSlot() == EquipmentSlot.MAINHAND) {
            final ItemStack incoming = event.getTo();
            if (incoming.getItem() instanceof IDualWieldWeapon) {
                ((IDualWieldWeapon) incoming.getItem()).updateMain(event.getEntityLiving(), incoming);
            }
        }
    }
}
