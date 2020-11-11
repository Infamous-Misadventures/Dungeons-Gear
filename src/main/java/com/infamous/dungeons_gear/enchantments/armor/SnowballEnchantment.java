package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class SnowballEnchantment extends PulseEnchantment {

    public SnowballEnchantment() {
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
            int snowballNearbyTimer = comboCap.getSnowballNearbyTimer();

            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.SNOWBALL)){
                int snowballLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.SNOWBALL, player);
                if(snowballNearbyTimer <= 0){
                    ProjectileEffectHelper.fireSnowballAtNearbyEnemy(player, 10);
                    comboCap.setSnowballNearbyTimer(Math.max(100 - snowballLevel * 40, 0));
                }
                else{
                    if(snowballNearbyTimer == 100) snowballNearbyTimer -= Math.max(100, 20 + snowballLevel * 20);
                    comboCap.setSnowballNearbyTimer(Math.max(snowballNearbyTimer - 1, 0));
                }
            }
            else{
                if(snowballNearbyTimer != 100){
                    comboCap.setSnowballNearbyTimer(100);
                }
            }
        }
    }
}
