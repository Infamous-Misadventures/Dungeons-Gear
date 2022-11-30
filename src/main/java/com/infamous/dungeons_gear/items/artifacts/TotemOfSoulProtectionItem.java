package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.entities.TotemOfSoulProtectionEntity;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.registry.ModEntityTypes;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCasterHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;

public class TotemOfSoulProtectionItem extends ArtifactItem implements ISoulConsumer {
    public TotemOfSoulProtectionItem(Properties properties) {
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
                if(SoulCasterHelper.consumeSouls(itemUseContextPlayer, this.getActivationCost(itemUseContextItem))){
                    TotemOfSoulProtectionEntity totemOfSoulProtectionEntity = ModEntityTypes.TOTEM_OF_SOUL_PROTECTION.get().create(itemUseContextPlayer.level);
                    if(totemOfSoulProtectionEntity != null) {
                        totemOfSoulProtectionEntity.moveTo(blockPos, 0, 0);
                        totemOfSoulProtectionEntity.setOwner(itemUseContextPlayer);
                        itemUseContextPlayer.level.addFreshEntity(totemOfSoulProtectionEntity);
                        itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemUseContextItem)));
                        ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
                    }
                }
            }
        }
        return InteractionResultHolder.consume(itemUseContext.getItemStack());
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

    @Override
    public int getDurationInSeconds() {
        return 5;
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return 5;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
    }

    private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(SOUL_GATHERING.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 1, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
