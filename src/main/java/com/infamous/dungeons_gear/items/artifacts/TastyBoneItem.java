package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Master;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.summon.SummonHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SUMMON_CAP;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;

public class TastyBoneItem extends ArtifactItem {
    public TastyBoneItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
        procOnItemUse = true;
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

            if (itemUseContextPlayer != null) {
                Master summonerCap = getMasterCapability(itemUseContextPlayer);
                Entity summoned = SummonHelper.summonEntity(itemUseContextPlayer, itemUseContextPlayer.blockPosition(), EntityType.WOLF);
                if (summoned != null) {
                    if (summoned instanceof Wolf) {
                        updateWolf(itemUseContextPlayer, (Wolf) summoned);
                    }
                    SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.WOLF_AMBIENT);
                    itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemUseContextItem)));
                    ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
                } else {
                    if (world instanceof ServerLevel) {
                        List<Entity> wolfEntities = summonerCap.getSummonedMobs().stream().filter(entity -> entity.getType() == EntityType.WOLF).collect(Collectors.toList());
                        wolfEntities.forEach(entity -> {
                            if (entity instanceof Wolf) {
                                Wolf wolfEntity = (Wolf) entity;
                                wolfEntity.teleportToWithTicket((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                            }
                        });
                    }
                }
            }
            return InteractionResultHolder.consume(itemUseContextItem);
        }
    }

    private void updateWolf(Player playerEntity, Wolf wolfEntity) {
        wolfEntity.tame(playerEntity);
    }

    @Override
    public int getCooldownInSeconds() {
        return 30;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
    }

    private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(SUMMON_CAP.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 2, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
