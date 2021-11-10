package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class GhostCloakItem extends ArtifactItem {
    public GhostCloakItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemInHand();

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

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
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
