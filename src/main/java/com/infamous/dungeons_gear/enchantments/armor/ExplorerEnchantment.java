package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ExplorerEnchantment extends HealthAbilityEnchantment {

    public ExplorerEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET});
    }

    @SubscribeEvent
    public static void onPlayerSpawn(PlayerEvent.PlayerRespawnEvent event){
        Player player = event.getPlayer();
        if(!player.level.isClientSide){
            Combo comboCap = ComboHelper.getComboCapability(player);
            if (comboCap != null) {
                comboCap.setLastExplorerCheckpoint(player.blockPosition());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive() && !player.level.isClientSide) {
            Combo comboCap = ComboHelper.getComboCapability(player);
            if (comboCap != null) {
                BlockPos lastExplorerCheckpoint = comboCap.getLastExplorerCheckpoint();
                BlockPos currentPos = player.blockPosition();
                if(currentPos.distSqr(lastExplorerCheckpoint) >= 10000){
                    comboCap.setLastExplorerCheckpoint(currentPos);
                    int explorerLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.EXPLORER, player);
                    if(explorerLevel > 0){
                        float maxHealth = player.getMaxHealth();
                        float multiplier = explorerLevel / 3.0F;
                        float healAmount = maxHealth * (0.01F * multiplier);
                        player.heal(healAmount);
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
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealthAbilityEnchantment);
    }
}
