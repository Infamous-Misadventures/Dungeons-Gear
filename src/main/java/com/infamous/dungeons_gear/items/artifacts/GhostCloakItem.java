package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class GhostCloakItem extends ArtifactItem {
    public GhostCloakItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
        Player playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        MobEffectInstance invisibility = new MobEffectInstance(MobEffects.INVISIBILITY, 60);
        playerIn.addEffect(invisibility);

        MobEffectInstance glowing = new MobEffectInstance(MobEffects.GLOWING, 60);
        playerIn.addEffect(glowing);

        MobEffectInstance swiftness = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60);
        playerIn.addEffect(swiftness);

        MobEffectInstance resistance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 3);
        playerIn.addEffect(resistance);

        //DualWield comboCap = playerIn.getCapability(DualWieldProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
        //comboCap.setGhostForm(true);

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 6;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
