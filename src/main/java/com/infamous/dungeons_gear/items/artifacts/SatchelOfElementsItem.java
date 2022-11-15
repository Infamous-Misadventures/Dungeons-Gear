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

public class SatchelOfElementsItem extends ArtifactItem{

    public SatchelOfElementsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ArtifactUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        if(playerIn == null) return new ActionResult<>(ActionResultType.FAIL, itemstack);

        if(!c.getLevel().isClientSide){
            int limit = 7;
            if(!AreaOfEffectHelper.applyElementalEffectsToNearbyEnemies(playerIn, limit, 8)){
                new ActionResult<>(ActionResultType.FAIL, itemstack);
            }
        }

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }



    @Override
    public int getCooldownInSeconds() {
        return 10;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
