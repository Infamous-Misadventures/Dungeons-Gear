package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.PulseEnchantment;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class ChillingEnchantment extends PulseEnchantment {

    public ChillingEnchantment() {
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
            int freezeNearbyTimer = comboCap.getFreezeNearbyTimer();

            boolean uniqueArmorFlag = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == ItemRegistry.FROST_ARMOR.get() ||
                    player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == ItemRegistry.FROST_ARMOR_HELMET.get();
            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.CHILLING) || uniqueArmorFlag){
                if(freezeNearbyTimer <= 0){
                    int chillingLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.CHILLING, player);
                    if(uniqueArmorFlag) chillingLevel++;
                    AreaOfEffectHelper.freezeNearbyEnemies(player, chillingLevel - 1, 1.5F);
                    comboCap.setFreezeNearbyTimer(40);
                }
                else{
                    comboCap.setFreezeNearbyTimer(freezeNearbyTimer - 1);
                }
            }
            else{
                if(freezeNearbyTimer != 40){
                    comboCap.setFreezeNearbyTimer(40);
                }
            }
        }
    }
}
