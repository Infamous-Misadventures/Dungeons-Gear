package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.artifacts.fallingblocks.SummonedFallingBlockEntity;
import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;


public class IceWandItem extends Item implements IArtifact {
    public IceWandItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItem();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
            Hand itemUseContextHand = itemUseContext.getHand();
            BlockPos itemUseContextPos = itemUseContext.getPos();
            Direction itemUseContextFace = itemUseContext.getFace();
            BlockState blockState = world.getBlockState(itemUseContextPos);

            BlockPos blockPos;
            if (blockState.getCollisionShape(world, itemUseContextPos).isEmpty()) {
                blockPos = itemUseContextPos;
            } else {
                blockPos = itemUseContextPos.offset(itemUseContextFace);
            }
                if(itemUseContextPlayer != null) {
                    for(int i = 0; i < 9; i++){
                        double xshift = 0;
                        double zshift = 0;

                        // positive x shift
                        if(i == 1 || i == 2 || i == 8){
                            xshift = 1.0D;
                        }
                        // negative x shift
                        if(i == 4 || i == 5 || i == 6){
                            xshift = -1.0D;
                        }
                        // positive z shift
                        if(i == 2 || i == 3 || i == 4){
                            zshift = 1.0D;
                        }
                        // negative z shift
                        if(i == 6 || i == 7 || i == 8){
                            zshift = -1.0D;
                        }
                        SummonedFallingBlockEntity fallingBlockEntity = new SummonedFallingBlockEntity(world, (double)blockPos.getX() + 0.5D + xshift, (double)blockPos.getY() + 4.0D, (double)blockPos.getZ() + 0.5D + zshift, Blocks.ICE.getDefaultState(), itemUseContextPlayer);
                        fallingBlockEntity.fallTime = 1;
                        fallingBlockEntity.setHurtEntities(true);
                        world.addEntity(fallingBlockEntity);
                    }

                    if(!itemUseContextPlayer.isCreative()){
                        itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> {
                            entity.sendBreakAnimation(itemUseContextHand);
                        });
                    }
                    setArtifactCooldown(itemUseContextPlayer, itemUseContextItem.getItem(), 400);
                }
        }
        return ActionResultType.CONSUME;
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.ICE_WAND){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Ice Wand was trapped in a tomb of ice for ages, sealed away by those who feared its power."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Creates large ice blocks that can crush your foes."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Stuns Mobs"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "20 Seconds Cooldown"));
        }
    }
}
