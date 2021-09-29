package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_libraries.capabilities.summoning.ISummonable;
import com.infamous.dungeons_libraries.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.goals.GolemOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.GolemOwnerHurtTargetGoal;
import com.infamous.dungeons_gear.goals.IronGolemFollowOwnerGoal;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;


import net.minecraft.item.Item.Properties;

import static com.infamous.dungeons_libraries.utils.CapabilityHelper.getSummonableCapability;
import static com.infamous.dungeons_libraries.utils.CapabilityHelper.getSummonerCapability;

public class GolemKitItem extends ArtifactItem {
    public GolemKitItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
        procOnItemUse=true;
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext itemUseContext) {
        World world = itemUseContext.getLevel();
        if (world.isClientSide) {
            return ActionResult.success(itemUseContext.getItemInHand());
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItemInHand();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
            BlockPos itemUseContextPos = itemUseContext.getClickedPos();
            Direction itemUseContextFace = itemUseContext.getClickedFace();
            BlockState blockState = world.getBlockState(itemUseContextPos);

            BlockPos blockPos;
            if (blockState.getCollisionShape(world, itemUseContextPos).isEmpty()) {
                blockPos = itemUseContextPos;
            } else {
                blockPos = itemUseContextPos.relative(itemUseContextFace);
            }

            if(itemUseContextPlayer != null){
                ISummoner summonerCap = getSummonerCapability(itemUseContextPlayer);
                if (summonerCap != null) {
                    if(summonerCap.getSummonedGolem() == null){
                        IronGolemEntity ironGolemEntity = EntityType.IRON_GOLEM.create(world);
                        if (ironGolemEntity!= null) {
                            ISummonable summonable = getSummonableCapability(ironGolemEntity);
                            if(summonable != null){

                                summonable.setSummoner(itemUseContextPlayer.getUUID());
                                summonerCap.setSummonedGolem(ironGolemEntity.getUUID());

                                createIronGolem(world, itemUseContextPlayer, blockPos, ironGolemEntity);

                                itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemUseContextItem)));

                            }
                        }
                    } else{
                        if(world instanceof ServerWorld) {
                            Entity entity = ((ServerWorld) world).getEntity(summonerCap.getSummonedGolem());
                            if (entity instanceof IronGolemEntity) {
                                IronGolemEntity ironGolemEntity = (IronGolemEntity) entity;
                                ironGolemEntity.teleportToWithTicket((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                            }
                        }
                    }
                }
            }
            return ActionResult.consume(itemUseContextItem);
        }
    }

    private void createIronGolem(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, IronGolemEntity ironGolemEntity) {
        ironGolemEntity.setPlayerCreated(true);
        ironGolemEntity.moveTo((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        ironGolemEntity.goalSelector.addGoal(2, new IronGolemFollowOwnerGoal(ironGolemEntity, 2.1D, 10.0F, 2.0F, false));

        ironGolemEntity.targetSelector.addGoal(1, new GolemOwnerHurtByTargetGoal(ironGolemEntity));
        ironGolemEntity.targetSelector.addGoal(2, new GolemOwnerHurtTargetGoal(ironGolemEntity));

        SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.IRON_GOLEM_REPAIR);
        world.addFreshEntity(ironGolemEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
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
