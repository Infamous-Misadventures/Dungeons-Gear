package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.entities.FireworksDisplayEntity;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class FireworksDisplayItem extends ArtifactItem {
    public FireworksDisplayItem(Properties properties) {
        super(properties);
        procOnItemUse=true;
    }


    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        Level world = itemUseContext.getLevel();
        if (world.isClientSide || itemUseContext.isHitMiss()) {
            return InteractionResultHolder.success(itemUseContext.getItemStack());
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItemStack();
            Player itemUseContextPlayer = itemUseContext.getPlayer();
            BlockPos itemUseContextPos = itemUseContext.getClickedPos();
            Direction itemUseContextFace = itemUseContext.getClickedFace();
            BlockState blockState = world.getBlockState(itemUseContextPos);

            BlockPos blockPos;
            if (blockState.getCollisionShape(world, itemUseContextPos).isEmpty()) {
                blockPos = itemUseContextPos;
            } else {
                blockPos = itemUseContextPos.relative(itemUseContextFace);
            }
            if(itemUseContextPlayer != null) {
                FireworksDisplayEntity totemEntity = ModEntityTypes.FIREWORKS_DISPLAY.get().create(itemUseContextPlayer.level);
                if(totemEntity != null) {
                    totemEntity.moveTo(blockPos, 0, 0);
                    totemEntity.setOwner(itemUseContextPlayer);
                    itemUseContextPlayer.level.addFreshEntity(totemEntity);
//                    itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemUseContextItem)));
                    ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
                }
            }
        }
        return InteractionResultHolder.consume(itemUseContext.getItemStack());
    }

    @Override
    public int getCooldownInSeconds() {
        return 60;
    }

    @Override
    public int getDurationInSeconds() {
        return 5;
    }
}
