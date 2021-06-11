package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
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

public class LightningRodItem extends ArtifactItem implements ISoulGatherer {
    public LightningRodItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItem();

        if(playerIn.isCreative() || CapabilityHelper.getComboCapability(playerIn).consumeSouls(getActivationCost(itemstack))){
            //AbilityUtils.castLightningBoltAtBlockPos(itemUseContextPlayer, blockPos);
            AreaOfEffectHelper.electrifyNearbyEnemies(playerIn, 5, 5, Integer.MAX_VALUE);
            SoundHelper.playLightningStrikeSounds(playerIn);
            itemstack.damageItem(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemstack)));
            ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem());
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 2;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 15;
    }
}
