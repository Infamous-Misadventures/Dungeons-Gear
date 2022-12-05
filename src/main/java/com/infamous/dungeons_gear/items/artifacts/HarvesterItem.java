package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCasterHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;

import net.minecraft.world.item.Item.Properties;

public class HarvesterItem extends ArtifactItem implements ISoulConsumer {

    public HarvesterItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
        Player playerIn = c.getPlayer();
        ItemStack itemStack = c.getItemStack();

        if (SoulCasterHelper.consumeSouls(playerIn, this.getActivationCost(itemStack))) {
            SoundHelper.playGenericExplodeSound(playerIn);
            AOECloudHelper.spawnExplosionCloud(playerIn, playerIn, 3.0F);
            AreaOfEffectHelper.causeMagicExplosionAttack(playerIn, playerIn, 15, 3.0F);
            itemStack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemStack)));
            ArtifactItem.putArtifactOnCooldown(playerIn, itemStack.getItem());
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
    }


    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return 40;
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
        builder.put(SOUL_GATHERING.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 1, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
