package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.utilties.AbilityUtils;
import com.infamous.dungeons_gear.utilties.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
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
        return !(enchantment instanceof PulseEnchantment);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if(player == null) return;
        if(event.phase == TickEvent.Phase.START) return;
        if(player.isAlive()){
            ICombo comboCap = player.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
            int snowballNearbyTimer = comboCap.getSnowballNearbyTimer();

            if(EnchantUtils.hasEnchantment(player, ArmorEnchantmentList.SNOWBALL)){
                int snowballLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.SNOWBALL, player);
                if(snowballNearbyTimer <= 0){
                    AbilityUtils.fireSnowballAtNearbyEnemy(player, 10);
                    if(snowballLevel == 1) comboCap.setSnowballNearbyTimer(100);
                    if(snowballLevel == 2) comboCap.setSnowballNearbyTimer(60);
                    if(snowballLevel == 3) comboCap.setSnowballNearbyTimer(20);
                }
                else{
                    if(snowballLevel == 2 && snowballNearbyTimer  == 100) snowballNearbyTimer -= 40;
                    if(snowballLevel == 3 && snowballNearbyTimer  == 100) snowballNearbyTimer -= 80;
                    comboCap.setSnowballNearbyTimer(snowballNearbyTimer - 1);
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
