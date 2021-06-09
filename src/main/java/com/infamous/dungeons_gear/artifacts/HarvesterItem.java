package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.utilties.AreaOfEffectHelper.causeMagicExplosionAttack;

public class HarvesterItem extends ArtifactItem implements ISoulGatherer {
    public HarvesterItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItem();

        if (playerIn.isCreative() || CapabilityHelper.getComboCapability(playerIn).consumeSouls(getActivationCost(itemstack))) {
            SoundHelper.playGenericExplodeSound(playerIn);
            AOECloudHelper.spawnExplosionCloud(playerIn, playerIn, 3.0F);
            causeMagicExplosionAttack(playerIn, playerIn, 15, 3.0F);

            itemstack.damageItem(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemstack)));
            ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem());
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 40;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
