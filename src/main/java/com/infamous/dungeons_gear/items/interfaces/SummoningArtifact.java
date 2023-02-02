package com.infamous.dungeons_gear.items.interfaces;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Master;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import com.infamous.dungeons_libraries.summon.SummonHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;

public interface SummoningArtifact<E extends Entity> {
    default InteractionResultHolder<ItemStack> procSummoningArtifact(ArtifactUseContext itemUseContext) {
        Level world = itemUseContext.getLevel();
        if (world.isClientSide || itemUseContext.isHitMiss()) {
            return InteractionResultHolder.success(itemUseContext.getItemStack());
        } else {
            ItemStack itemStack = itemUseContext.getItemStack();
            Player player = itemUseContext.getPlayer();
            BlockPos clickedPos = itemUseContext.getClickedPos();
            Direction clickedFace = itemUseContext.getClickedFace();
            BlockState blockState = world.getBlockState(clickedPos);

            BlockPos blockPos;
            if (blockState.getCollisionShape(world, clickedPos).isEmpty()) {
                blockPos = clickedPos;
            } else {
                blockPos = clickedPos.relative(clickedFace);
            }

            if (player != null) {
                Master summonerCap = getMasterCapability(player);
                Entity summonEntity = SummonHelper.summonEntity(player, player.blockPosition(), this.getSummonType());
                if (summonEntity != null) {
                    this.onSummoned(player, summonEntity);
                    itemStack.hurtAndBreak(1, player, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemStack)));
                    ArtifactItem.putArtifactOnCooldown(player, itemStack.getItem());
                } else {
                    if (world instanceof ServerLevel) {
                        List<Entity> summonedMobsOfType = summonerCap.getSummonedMobs().stream().filter(entity -> entity.getType() == this.getSummonType()).toList();
                        summonedMobsOfType.forEach(entity -> entity.teleportToWithTicket((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D));
                    }
                }
            }
            return InteractionResultHolder.consume(itemStack);
        }
    }

    void onSummoned(Player player, Entity summonEntity);

    EntityType<E> getSummonType();
}
