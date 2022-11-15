package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.BreakItemMessage;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCasterHelper;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

import static com.infamous.dungeons_gear.utilties.AOECloudHelper.spawnSoulProtectionCloudAtPos;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;

public class TotemOfSoulProtectionItem extends ArtifactItem implements ISoulConsumer {
    public TotemOfSoulProtectionItem(Properties properties) {
        super(properties);
        procOnItemUse=true;
    }

    public ActionResult<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        World world = itemUseContext.getLevel();
        if (world.isClientSide || itemUseContext.isHitMiss()) {
            return ActionResult.success(itemUseContext.getItemStack());
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItemStack();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
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
                    spawnSoulProtectionCloudAtPos(itemUseContextPlayer, blockPos, 100);
                    itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemUseContextItem)));

                    ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
                }
            }
        }
        return ActionResult.consume(itemUseContext.getItemStack());
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
