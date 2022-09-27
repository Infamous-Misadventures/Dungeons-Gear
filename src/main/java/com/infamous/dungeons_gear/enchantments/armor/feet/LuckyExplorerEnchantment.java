package com.infamous.dungeons_gear.enchantments.armor.feet;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.IEmeraldsEnchantment;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class LuckyExplorerEnchantment extends DropsEnchantment implements IEmeraldsEnchantment {

    public LuckyExplorerEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, ARMOR_SLOT);
    }

    @SubscribeEvent
    public static void onPlayerSpawn(PlayerEvent.PlayerRespawnEvent event){
        Player player = event.getPlayer();
        if(!player.level.isClientSide){
            Combo comboCap = ComboHelper.getComboCapability(player);
            if (comboCap != null) {
                comboCap.setLastLuckyExplorerCheckpoint(player.blockPosition());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player == null || player.isSpectator()) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive() && !player.level.isClientSide) {
            int luckyExplorerLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.LUCKY_EXPLORER, player);
            if (luckyExplorerLevel > 0) {
                Combo comboCap = ComboHelper.getComboCapability(player);
                if (comboCap != null) {
                    BlockPos lastLuckyExplorerCheckpoint = comboCap.getLastLuckyExplorerCheckpoint();
                    BlockPos currentPos = player.blockPosition();
                    if (currentPos.distSqr(lastLuckyExplorerCheckpoint) >= 36) {
                        comboCap.setLastLuckyExplorerCheckpoint(currentPos);
                        int rollCount = 1 + (luckyExplorerLevel - 1) * 2;
                        for (int i = 0; i < rollCount; i++) {
                            ItemStack itemStack = LootTableHelper.generateItemStack((ServerLevel) player.level, player.blockPosition(), new ResourceLocation(MODID, "enchantments/lucky_explorer"), player.getRandom());
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
