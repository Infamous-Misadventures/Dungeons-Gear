package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.items.ArtifactList;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;


public class TastyBoneItem extends ArtifactItem {
    public TastyBoneItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
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

            if(itemUseContextPlayer != null){
                ISummoner summonerCap = CapabilityHelper.getSummonerCapability(itemUseContextPlayer);
                if (summonerCap != null) {
                    if(summonerCap.getSummonedWolf() == null){
                        WolfEntity wolfEntity = EntityType.WOLF.create(world);
                        if (wolfEntity!= null) {
                            ISummonable summon = CapabilityHelper.getSummonableCapability(wolfEntity);
                            if(summon != null){

                                summon.setSummoner(itemUseContextPlayer.getUniqueID());

                                createWolf(world, itemUseContextPlayer, blockPos, wolfEntity);

                                summonerCap.setSummonedWolf(wolfEntity.getUniqueID());

                                if(!itemUseContextPlayer.isCreative()){
                                    itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> {
                                        entity.sendBreakAnimation(itemUseContextHand);
                                    });
                                }
                            }
                        }
                    } else{
                        if(world instanceof ServerWorld) {
                            Entity entity = ((ServerWorld) world).getEntityByUuid(summonerCap.getSummonedWolf());
                            if (entity instanceof WolfEntity) {
                                WolfEntity wolfEntity = (WolfEntity) entity;
                                wolfEntity.teleportKeepLoaded((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                            }
                        }
                    }
                }
            }
            return ActionResultType.CONSUME;
        }
    }

    private void createWolf(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, WolfEntity wolfEntity) {
        wolfEntity.setTamedBy(itemUseContextPlayer);
        wolfEntity.setLocationAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);


        wolfEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(wolfEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
            return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
        }));

        world.playSound((PlayerEntity)null, itemUseContextPlayer.getPosX(), itemUseContextPlayer.getPosY(), itemUseContextPlayer.getPosZ(), SoundEvents.ENTITY_WOLF_HOWL, SoundCategory.AMBIENT, 64.0F, 1.0F);

        world.addEntity(wolfEntity);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.TASTY_BONE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "You hear distant howling as you hold the Tasty Bone in your hand."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Summons a wolf to aid you in battle."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
        }
    }
}
