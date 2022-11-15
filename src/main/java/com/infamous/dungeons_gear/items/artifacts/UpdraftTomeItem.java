package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.BreakItemMessage;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.fml.network.PacketDistributor;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;

public class UpdraftTomeItem extends ArtifactItem {
    public UpdraftTomeItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ArtifactUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        AreaOfEffectHelper.levitateNearbyEnemies(playerIn, 8, 5, 0, 5);
        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }



    @Override
    public int getCooldownInSeconds() {
        return 12;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
