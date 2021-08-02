package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class SatchelOfElixirsItem extends ArtifactItem{

    public SatchelOfElixirsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItem();

        if(playerIn == null) return new ActionResult<>(ActionResultType.FAIL, itemstack);

        if(!c.getWorld().isRemote){
            for(int i = 0; i < 2; i++){
                ItemStack elixirToDrop = LootTableHelper.generateItemStack((ServerWorld) playerIn.world, playerIn.getPosition(), new ResourceLocation(MODID, "items/satchel_of_elixirs"), playerIn.getRNG());
                ItemEntity elixirAsEntity = new ItemEntity(playerIn.world, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), elixirToDrop);
                playerIn.world.addEntity(elixirAsEntity);
            }
        }

        itemstack.damageItem(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
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
