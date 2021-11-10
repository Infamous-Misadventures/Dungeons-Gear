package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

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
                        for (ItemStack is : player.inventory.offhand)
                            if (is.getItem() instanceof ArtifactItem && !(is.getItem() instanceof AbstractBeaconItem)) {
                                ActionResult<ItemStack> procResult = ((ArtifactItem) is.getItem()).procArtifact(new ItemUseContext(player, Hand.OFF_HAND, new BlockRayTraceResult(player.position(), Direction.UP, player.blockPosition(), false)));
                                if(procResult.getResult().consumesAction() && !player.level.isClientSide) ArtifactItem.triggerSynergy(player, is);
                                proc++;
                            }
                        for (ItemStack is : player.inventory.items)
                            if (is.getItem() instanceof ArtifactItem && !(is.getItem() instanceof AbstractBeaconItem)) {
                                ActionResult<ItemStack> procResult = ((ArtifactItem) is.getItem()).procArtifact(new ItemUseContext(player.level, player, Hand.MAIN_HAND, is, new BlockRayTraceResult(player.position(), Direction.UP, player.blockPosition(), false)));
                                if(procResult.getResult().consumesAction() && !player.level.isClientSide) ArtifactItem.triggerSynergy(player, is);

                                if (++proc == 3) break;
                            }
                        if (proc > 0) {
                            comboCap.setLastShoutTimer(240 - 40 * Math.min(EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.FINAL_SHOUT, player), 6));
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
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealthAbilityEnchantment);
    }
}
