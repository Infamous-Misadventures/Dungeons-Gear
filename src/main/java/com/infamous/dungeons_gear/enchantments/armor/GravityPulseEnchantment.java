package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class GravityPulseEnchantment extends PulseEnchantment {

    public GravityPulseEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof PulseEnchantment);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if(player == null) return;
        if(event.phase == TickEvent.Phase.START) return;
        if(player.isAlive()){
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if(comboCap == null) return;
            int gravityPulseTimer = comboCap.getGravityPulseTimer();

            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.GRAVITY_PULSE)){
                if(gravityPulseTimer <= 0){
                    int gravityPulseLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.GRAVITY_PULSE, player);
                    AreaOfEffectHelper.pullInNearbyEntities(player, player, 1.5F + 1.5F * gravityPulseLevel, ParticleTypes.PORTAL);
                    comboCap.setGravityPulseTimer(100);
                }
                else{
                    comboCap.setGravityPulseTimer(gravityPulseTimer - 1);
                }
            }
            else{
                if(gravityPulseTimer != 100){
                    comboCap.setFreezeNearbyTimer(100);
                }
            }
        }
    }
}
