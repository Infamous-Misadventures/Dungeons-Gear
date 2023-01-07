package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class RefreshmentEnchantment extends DropsEnchantment {

    public static final int REFRESHMENT_GOAL = 45;

    public RefreshmentEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                !(enchantment instanceof DropsEnchantment);
    }

    @SubscribeEvent
    public static void onRefreshmentKill(LivingDeathEvent event) {
        DamageSource damageSource = event.getSource();
        if (damageSource.getEntity() instanceof Player) {
            Player killerPlayer = (Player) damageSource.getEntity();
            if (!PlayerAttackHelper.isProbablyNotMeleeDamage(damageSource)) {
                ItemStack mainhand = killerPlayer.getMainHandItem();
                int refreshmentLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.REFRESHMENT.get(), mainhand);
                if (refreshmentLevel > 0) {
                    updateRefreshment(killerPlayer, refreshmentLevel);
                }
            } else if (damageSource.isProjectile()) {
                Entity immediateSource = damageSource.getDirectEntity();
                if (immediateSource instanceof AbstractArrow) {
                    AbstractArrow arrowEntity = (AbstractArrow) immediateSource;
                    int refreshmentLevel = ArrowHelper.enchantmentTagToLevel(arrowEntity, EnchantmentInit.REFRESHMENT.get());
                    if (refreshmentLevel > 0) {
                        updateRefreshment(killerPlayer, refreshmentLevel);
                    }
                }
            }
        }
    }

    private static void updateRefreshment(Player player, int refreshmentLevel) {
        Combo comboCap = ComboHelper.getComboCapability(player);
        if (refreshmentLevel <= 0) return;
        comboCap.setRefreshmentCounter(comboCap.getRefreshmentCounter() + refreshmentLevel);

        if (comboCap.getRefreshmentCounter() >= REFRESHMENT_GOAL) {
            Inventory playerInventory = player.getInventory();
            for (int slotId = 0; slotId < playerInventory.getContainerSize(); slotId++) {
                ItemStack currentStack = playerInventory.getItem(slotId);
                if (currentStack.getItem() instanceof BottleItem) {
                    ItemStack healthPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING);
                    playerInventory.setItem(slotId, healthPotion);
                    comboCap.setRefreshmentCounter(comboCap.getRefreshmentCounter() - REFRESHMENT_GOAL);
                    return;
                }
            }
        }
    }

}
