package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_libraries.capabilities.minionmaster.IMinion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMaster;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;


import net.minecraft.item.Item.Properties;

import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMinionCapability;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;

public class TastyBoneItem extends ArtifactItem {
    public TastyBoneItem(Properties p_i48487_1_) {
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
                IMaster summonerCap = getMasterCapability(itemUseContextPlayer);
                if (summonerCap != null) {
                    if(summonerCap.getSummonedWolf() == null){
                        WolfEntity wolfEntity = EntityType.WOLF.create(world);
                        if (wolfEntity!= null) {
                            IMinion summon = getMinionCapability(wolfEntity);
                            if(summon != null){

                                summon.setMaster(itemUseContextPlayer.getUUID());

                                createWolf(world, itemUseContextPlayer, blockPos, wolfEntity);

                                summonerCap.setSummonedWolf(wolfEntity.getUUID());

                                itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemUseContextItem)));

                            }
                        }
                    } else{
                        if(world instanceof ServerWorld) {
                            Entity entity = ((ServerWorld) world).getEntity(summonerCap.getSummonedWolf());
                            if (entity instanceof WolfEntity) {
                                WolfEntity wolfEntity = (WolfEntity) entity;
                                wolfEntity.teleportToWithTicket((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                            }
                        }
                    }
                }
            }
            return ActionResult.consume(itemUseContextItem);
        }
    }

    private void createWolf(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, WolfEntity wolfEntity) {
        wolfEntity.tame(itemUseContextPlayer);
        wolfEntity.moveTo((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);


        wolfEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(wolfEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
            return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
        }));

        SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.WOLF_AMBIENT);

        world.addFreshEntity(wolfEntity);
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
