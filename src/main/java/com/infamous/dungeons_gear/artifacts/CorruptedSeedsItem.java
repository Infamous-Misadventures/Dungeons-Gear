package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class CorruptedSeedsItem extends ArtifactItem {
    public CorruptedSeedsItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn=c.getPlayer();
        ItemStack itemstack = c.getItem();

        AreaOfEffectHelper.poisonAndSlowNearbyEnemies(c.getWorld(), playerIn);

        itemstack.damageItem(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemstack)));

        ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem(), 400);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "A pouch of poisonous, corrupted seeds which grow into spiky grapple vines, entangling and slowly draining the life from its victims"));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Grow grapple vines, which inflict poison."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Briefly Entangles Mobs"));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Poisons Entangled Mobs"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "7 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "20 Seconds Cooldown"));
    }
}
