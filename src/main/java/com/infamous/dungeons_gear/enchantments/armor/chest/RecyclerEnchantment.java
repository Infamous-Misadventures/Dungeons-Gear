package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class RecyclerEnchantment extends DropsEnchantment {

    public RecyclerEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof DropsEnchantment);
    }


    @SubscribeEvent
    public static void onPlayerDamaged(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (player.isAlive()) {
            if (event.getSource().getDirectEntity() instanceof AbstractArrow) {
                Combo comboCap = ComboHelper.getComboCapability(player);
                if (ModEnchantmentHelper.hasEnchantment(player, EnchantmentInit.RECYCLER.get())) {
                    int arrowsInCounter = comboCap.getArrowsInCounter();
                    arrowsInCounter++;
                    comboCap.setArrowsInCounter(arrowsInCounter);

                    int recyclerLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RECYCLER.get(), player);
                    if (comboCap.getArrowsInCounter() >= 40 - 7 * recyclerLevel) {
                        ItemEntity arrowDrop = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(Items.ARROW, 10));
                        player.level.addFreshEntity(arrowDrop);
                        comboCap.setArrowsInCounter(0);
                    }
                }
            }
        }
    }
}
