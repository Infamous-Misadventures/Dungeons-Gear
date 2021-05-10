package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class FinalShoutEnchantment extends HealthAbilityEnchantment {

    public FinalShoutEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent event) {
        LivingEntity victim = event.getEntityLiving();
        if (victim != null && victim.isAlive()) {
            if (victim instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) victim;
                float currentHealth = player.getHealth();
                float maxHealth = player.getMaxHealth();
                float damageDealt = event.getAmount();
                ICombo comboCap = CapabilityHelper.getComboCapability(player);
                if (currentHealth - damageDealt <= (0.25F * maxHealth)) {
                    if (comboCap != null && comboCap.getLastShoutTimer() == 0 && ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.FINAL_SHOUT)) {
                        int proc = 0;
                        for (ItemStack is : player.inventory.offHandInventory)
                            if (is.getItem() instanceof ArtifactItem && !(is.getItem() instanceof AbstractBeaconItem)) {
                                ((ArtifactItem) is.getItem()).procArtifact(new ItemUseContext(player, Hand.OFF_HAND, new BlockRayTraceResult(player.getPositionVec(), Direction.UP, player.getPosition(), false)));
                                proc++;
                            }
                        for (ItemStack is : player.inventory.mainInventory)
                            if (is.getItem() instanceof ArtifactItem && !(is.getItem() instanceof AbstractBeaconItem)) {
                                ((ArtifactItem) is.getItem()).procArtifact(new ItemUseContext(player.world, player, null, is, new BlockRayTraceResult(player.getPositionVec(), Direction.UP, player.getPosition(), false)));
                                if (++proc == 3) break;
                            }
                        if (proc > 0) {
                            comboCap.setLastShoutTimer(240 - 40 * Math.min(EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.FINAL_SHOUT, player), 6));
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
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealthAbilityEnchantment);
    }
}
