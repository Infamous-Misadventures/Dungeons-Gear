package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Master;
import com.infamous.dungeons_libraries.summon.SummonHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SUMMON_CAP;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;

import net.minecraft.world.item.Item.Properties;

public class WonderfulWheatItem extends ArtifactItem {
    public WonderfulWheatItem(Properties p_i48487_1_) {
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
                Entity summoned = SummonHelper.summonEntity(itemUseContextPlayer, itemUseContextPlayer.blockPosition(), EntityType.LLAMA);
                if (summoned != null) {
                    if (summoned instanceof Llama) {
                        updatellama(itemUseContextPlayer, (Llama) summoned);
                    }
                    SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.LLAMA_AMBIENT);
                    itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemUseContextItem)));
                    ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
                } else {
                    if (world instanceof ServerLevel) {
                        List<Entity> llamaEntities = summonerCap.getSummonedMobs().stream().filter(entity -> entity.getType() == EntityType.LLAMA).collect(Collectors.toList());
                        llamaEntities.forEach(entity -> {
                            if (entity instanceof Llama) {
                                Llama llamaEntity = (Llama) entity;
                                llamaEntity.teleportToWithTicket((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                            }
                        });
                    }
                }
            }

            return InteractionResultHolder.consume(itemUseContextItem);
        }
    }

    private void updatellama(Player itemUseContextPlayer, Llama llamaEntity) {
        llamaEntity.tameWithName(itemUseContextPlayer);
        llamaEntity.setVariant(2);
        llamaEntity.setStrength(5);
        AttributeInstance maxHealth = llamaEntity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null)
            maxHealth.setBaseValue(30.0D);
        llamaEntity.inventory.setItem(1, new ItemStack(Items.RED_CARPET.asItem()));
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
