package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.BreakItemMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.fml.network.PacketDistributor;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;

public class GhostCloakItem extends ArtifactItem {
    public GhostCloakItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ArtifactUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        EffectInstance invisibility = new EffectInstance(Effects.INVISIBILITY, 60);
        playerIn.addEffect(invisibility);

        EffectInstance glowing = new EffectInstance(Effects.GLOWING, 60);
        playerIn.addEffect(glowing);

        EffectInstance swiftness = new EffectInstance(Effects.MOVEMENT_SPEED, 60);
        playerIn.addEffect(swiftness);

        EffectInstance resistance = new EffectInstance(Effects.DAMAGE_RESISTANCE, 60,3);
        playerIn.addEffect(resistance);

        //ICombo comboCap = playerIn.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
        //comboCap.setGhostForm(true);

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
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
