package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCasterHelper;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class SoulHealerItem extends ArtifactItem implements ISoulConsumer {
    public SoulHealerItem(Properties properties) {
        super(properties);
        procOnItemUse = true;
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemInHand();

        if (SoulCasterHelper.consumeSouls(playerIn, itemstack)) {
            if ((playerIn.getHealth() < playerIn.getMaxHealth())) {
                float currentHealth = playerIn.getHealth();
                float maxHealth = playerIn.getMaxHealth();
                float lostHealth = maxHealth - currentHealth;
                float toHeal = Math.min(lostHealth, Math.min(maxHealth / 5, CapabilityHelper.getComboCapability(playerIn).getSouls() * 0.01f));
                if (playerIn.isCreative() || CapabilityHelper.getComboCapability(playerIn).consumeSouls(toHeal * 100)) {
                    playerIn.heal(toHeal);
                    itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemstack)));
                }
                ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
            } else {
                float healedAmount = AreaOfEffectHelper.healMostInjuredAlly(playerIn, 12);
                if (healedAmount > 0) {
                    itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemstack)));
                    ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
                }
            }
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return 20;
    }
}
