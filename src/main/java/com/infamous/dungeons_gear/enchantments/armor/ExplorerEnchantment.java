package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ExplorerEnchantment extends HealthAbilityEnchantment {

    public ExplorerEnchantment() {
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
                comboCap.setLastExplorerCheckpoint(player.getPosition());
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
                BlockPos lastExplorerCheckpoint = comboCap.getLastExplorerCheckpoint();
                BlockPos currentPos = player.getPosition();
                if(currentPos.distanceSq(lastExplorerCheckpoint.getX(), lastExplorerCheckpoint.getY(), lastExplorerCheckpoint.getZ(), true) >= 10000){
                    comboCap.setLastExplorerCheckpoint(currentPos);
                    int explorerLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.EXPLORER, player);
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
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealthAbilityEnchantment);
    }
}
