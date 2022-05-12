package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.integration.curios.CuriosIntegration;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.capabilities.timers.ITimers;
import com.infamous.dungeons_libraries.capabilities.timers.TimersHelper;
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
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.FINAL_SHOUT;

import net.minecraft.enchantment.Enchantment.Rarity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

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
        if (victim != null && victim.isAlive() && victim instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) victim;
            float currentHealth = player.getHealth();
            float maxHealth = player.getMaxHealth();
            float damageDealt = event.getAmount();
            ITimers timers = TimersHelper.getTimersCapability(player);
            if (currentHealth - damageDealt <= (0.25F * maxHealth)) {
                if (timers != null && timers.getEnchantmentTimer(FINAL_SHOUT) == 0 && ModEnchantmentHelper.hasEnchantment(player, FINAL_SHOUT)) {
                    int proc = 0;
                    for(ItemStack is : CuriosIntegration.getArtifacts(player))
                        if (is.getItem() instanceof ArtifactItem && !(is.getItem() instanceof AbstractBeaconItem)) {
                            ActionResult<ItemStack> procResult = ((ArtifactItem) is.getItem()).procArtifact(new ArtifactUseContext(player, Hand.MAIN_HAND, new BlockRayTraceResult(player.position(), Direction.UP, player.blockPosition(), false)));
                            if(procResult.getResult().consumesAction() && !player.level.isClientSide) ArtifactItem.triggerSynergy(player, is);
                            proc++;
                        }
                    if (proc > 0) {
                        timers.setEnchantmentTimer(FINAL_SHOUT, 240 - 40 * Math.min(EnchantmentHelper.getEnchantmentLevel(FINAL_SHOUT, player), 6));
                    }
                }else if(timers != null && timers.getEnchantmentTimer(FINAL_SHOUT) < 0 && ModEnchantmentHelper.hasEnchantment(player, FINAL_SHOUT)){
                    timers.setEnchantmentTimer(FINAL_SHOUT, 0);
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
