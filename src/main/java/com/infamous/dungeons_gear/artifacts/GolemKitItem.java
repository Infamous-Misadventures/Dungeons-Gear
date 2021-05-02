package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.goals.GolemOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.GolemOwnerHurtTargetGoal;
import com.infamous.dungeons_gear.goals.IronGolemFollowOwnerGoal;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;


public class GolemKitItem extends ArtifactItem {
    public GolemKitItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
        procOnItemUse=true;
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        if (world.isRemote) {
            return ActionResult.resultSuccess(itemUseContext.getItem());
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItem();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
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
                    if(summonerCap.getSummonedGolem() == null){
                        IronGolemEntity ironGolemEntity = EntityType.IRON_GOLEM.create(world);
                        if (ironGolemEntity!= null) {
                            ISummonable summonable = CapabilityHelper.getSummonableCapability(ironGolemEntity);
                            if(summonable != null){

                                summonable.setSummoner(itemUseContextPlayer.getUniqueID());
                                summonerCap.setSummonedGolem(ironGolemEntity.getUniqueID());

                                createIronGolem(world, itemUseContextPlayer, blockPos, ironGolemEntity);

                                itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemUseContextItem)));

                            }
                        }
                    } else{
                        if(world instanceof ServerWorld) {
                            Entity entity = ((ServerWorld) world).getEntityByUuid(summonerCap.getSummonedGolem());
                            if (entity instanceof IronGolemEntity) {
                                IronGolemEntity ironGolemEntity = (IronGolemEntity) entity;
                                ironGolemEntity.teleportKeepLoaded((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                            }
                        }
                    }
                }
            }
            return ActionResult.resultConsume(itemUseContextItem);
        }
    }

    private void createIronGolem(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, IronGolemEntity ironGolemEntity) {
        ironGolemEntity.setPlayerCreated(true);
        ironGolemEntity.setLocationAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        ironGolemEntity.goalSelector.addGoal(2, new IronGolemFollowOwnerGoal(ironGolemEntity, 2.1D, 10.0F, 2.0F, false));

        ironGolemEntity.targetSelector.addGoal(1, new GolemOwnerHurtByTargetGoal(ironGolemEntity));
        ironGolemEntity.targetSelector.addGoal(2, new GolemOwnerHurtTargetGoal(ironGolemEntity));

        world.playSound((PlayerEntity)null, itemUseContextPlayer.getPosX(), itemUseContextPlayer.getPosY(), itemUseContextPlayer.getPosZ(), SoundEvents.ENTITY_IRON_GOLEM_HURT, SoundCategory.AMBIENT, 64.0F, 1.0F);
        world.addEntity(ironGolemEntity);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "Iron Golems have always protected the Villagers of the Overworld. Their numbers are dwindling as a result of the Arch-Illager's war."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Summons an Iron Golem to aid you in battle."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
    }
}
