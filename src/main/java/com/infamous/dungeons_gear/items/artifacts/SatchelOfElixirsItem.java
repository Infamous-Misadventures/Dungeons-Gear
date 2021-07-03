package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.init.PotionList;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Arrays;
import java.util.List;

public class SatchelOfElixirsItem extends ArtifactItem{

    public SatchelOfElixirsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItem();

        if(playerIn == null) return new ActionResult<>(ActionResultType.FAIL, itemstack);

        ItemStack strengthPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.SHORT_STRENGTH);
        ItemStack speedPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.SHORT_SWIFTNESS);
        ItemStack shadowBrew = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.SHADOW_BREW);

        List<ItemStack> elixirList = Arrays.asList(strengthPotion, speedPotion, shadowBrew);
        // TODO: Add more potions

        if(!c.getWorld().isRemote){
            for(int i = 0; i < 2; i++){
                ItemStack elixirToDrop = elixirList.get(playerIn.getRNG().nextInt(elixirList.size()));
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
