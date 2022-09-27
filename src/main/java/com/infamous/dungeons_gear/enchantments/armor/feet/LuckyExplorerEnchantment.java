package com.infamous.dungeons_gear.enchantments.armor.feet;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.IEmeraldsEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class LuckyExplorerEnchantment extends DropsEnchantment implements IEmeraldsEnchantment {

    public LuckyExplorerEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_FEET, ARMOR_SLOT);
    }

    @SubscribeEvent
    public static void onPlayerSpawn(PlayerEvent.PlayerRespawnEvent event){
        PlayerEntity player = event.getPlayer();
        if(!player.level.isClientSide){
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap != null) {
                comboCap.setLastLuckyExplorerCheckpoint(player.blockPosition());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null || player.isSpectator()) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive() && !player.level.isClientSide) {
            int luckyExplorerLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.LUCKY_EXPLORER, player);
            if (luckyExplorerLevel > 0) {
                ICombo comboCap = CapabilityHelper.getComboCapability(player);
                if (comboCap != null) {
                    BlockPos lastLuckyExplorerCheckpoint = comboCap.getLastLuckyExplorerCheckpoint();
                    BlockPos currentPos = player.blockPosition();
                    if (currentPos.distSqr(lastLuckyExplorerCheckpoint.getX(), lastLuckyExplorerCheckpoint.getY(), lastLuckyExplorerCheckpoint.getZ(), true) >= 36) {
                        comboCap.setLastLuckyExplorerCheckpoint(currentPos);
                        int rollCount = 1 + (luckyExplorerLevel - 1) * 2;
                        for (int i = 0; i < rollCount; i++) {
                            ItemStack itemStack = LootTableHelper.generateItemStack((ServerWorld) player.level, player.blockPosition(), new ResourceLocation(MODID, "enchantments/lucky_explorer"), player.getRandom());
                            ItemEntity luckyExplorerDrop = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), itemStack);
                            player.level.addFreshEntity(luckyExplorerDrop);
                        }
                    }
                }
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get()
                || (!(enchantment instanceof DropsEnchantment) && !(enchantment instanceof IEmeraldsEnchantment));
    }

}
