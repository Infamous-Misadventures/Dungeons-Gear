package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.BreakItemMessage;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;

public class SatchelOfElixirsItem extends ArtifactItem{

    public SatchelOfElixirsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ArtifactUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        if(playerIn == null) return new ActionResult<>(ActionResultType.FAIL, itemstack);

        if(!c.getLevel().isClientSide){
            for(int i = 0; i < 2; i++){
                ItemStack elixirToDrop = LootTableHelper.generateItemStack((ServerWorld) playerIn.level, playerIn.blockPosition(), new ResourceLocation(MODID, "items/satchel_of_elixirs"), playerIn.getRandom());
                ItemEntity elixirAsEntity = new ItemEntity(playerIn.level, playerIn.getX(), playerIn.getY(), playerIn.getZ(), elixirToDrop);
                playerIn.level.addFreshEntity(elixirAsEntity);
            }
        }

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }



    @Override
    public int getCooldownInSeconds() {
        return 30;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
