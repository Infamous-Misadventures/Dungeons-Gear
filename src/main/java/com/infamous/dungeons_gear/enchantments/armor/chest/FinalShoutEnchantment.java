package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.capabilities.timers.Timers;
import com.infamous.dungeons_libraries.capabilities.timers.TimersHelper;
import com.infamous.dungeons_libraries.integration.curios.CuriosIntegration;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.FINAL_SHOUT;

@Mod.EventBusSubscriber(modid = MODID)
public class FinalShoutEnchantment extends HealthAbilityEnchantment {

    public FinalShoutEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent event) {
        LivingEntity victim = event.getEntityLiving();
        if (victim != null && victim.isAlive() && victim instanceof Player) {
            Player player = (Player) victim;
            float currentHealth = player.getHealth();
            float maxHealth = player.getMaxHealth();
            float damageDealt = event.getAmount();
            Timers timers = TimersHelper.getTimersCapability(player);
            if (currentHealth - damageDealt <= (0.25F * maxHealth)) {
                if (timers != null && timers.getEnchantmentTimer(FINAL_SHOUT) == 0 && ModEnchantmentHelper.hasEnchantment(player, FINAL_SHOUT)) {
                    int proc = 0;
                    for(ItemStack is : CuriosIntegration.getArtifacts(player))
                        if (is.getItem() instanceof ArtifactItem && !(is.getItem() instanceof AbstractBeaconItem)) {
                            InteractionResultHolder<ItemStack> procResult = ((ArtifactItem) is.getItem()).procArtifact(new ArtifactUseContext(player, InteractionHand.MAIN_HAND, new BlockHitResult(player.position(), Direction.UP, player.blockPosition(), false)));
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
