package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class ThunderingQuiverItem extends ArtifactItem {
    public static final String THUNDERING_ARROW = "ThunderingArrow";

    public ThunderingQuiverItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItem();

        ICombo comboCap = CapabilityHelper.getComboCapability(playerIn);
        if (comboCap == null) return new ActionResult<>(ActionResultType.FAIL, itemstack);
        comboCap.setThunderingArrowsCount(5);

        itemstack.damageItem(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemstack)));
        ArtifactItem.setArtifactCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 30;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
