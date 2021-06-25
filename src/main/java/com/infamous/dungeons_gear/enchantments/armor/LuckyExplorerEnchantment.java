package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.IEmeraldsEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class LuckyExplorerEnchantment extends DropsEnchantment implements IEmeraldsEnchantment {

    public LuckyExplorerEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    @SubscribeEvent
    public static void onPlayerSpawn(PlayerEvent.PlayerRespawnEvent event){
        PlayerEntity player = event.getPlayer();
        if(!player.world.isRemote){
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap != null) {
                comboCap.setLastLuckyExplorerCheckpoint(player.getPosition());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive() && !player.world.isRemote) {
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap != null) {
                BlockPos lastLuckyExplorerCheckpoint = comboCap.getLastLuckyExplorerCheckpoint();
                BlockPos currentPos = player.getPosition();
                if(currentPos.distanceSq(lastLuckyExplorerCheckpoint.getX(), lastLuckyExplorerCheckpoint.getY(), lastLuckyExplorerCheckpoint.getZ(), true) >= 36){
                    comboCap.setLastLuckyExplorerCheckpoint(currentPos);
                    int luckyExplorerLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.LUCKY_EXPLORER, player);
                    if(luckyExplorerLevel > 0){
                        int emeraldCount = 1 + (luckyExplorerLevel - 1) * 2;
                        ItemEntity emeraldDrop = new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Items.EMERALD, emeraldCount));
                        player.world.addEntity(emeraldDrop);
                    }
                }
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get()
                || (!(enchantment instanceof DropsEnchantment) && !(enchantment instanceof IEmeraldsEnchantment));
    }
}
