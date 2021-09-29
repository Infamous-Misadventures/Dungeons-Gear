package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_libraries.capabilities.summoning.ISummonable;
import com.infamous.dungeons_libraries.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.goals.BeeFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtTargetGoal;
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
import net.minecraft.entity.passive.BeeEntity;
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
import java.util.UUID;

import net.minecraft.item.Item.Properties;

import static com.infamous.dungeons_libraries.utils.CapabilityHelper.getSummonableCapability;
import static com.infamous.dungeons_libraries.utils.CapabilityHelper.getSummonerCapability;

public class BuzzyNestItem extends ArtifactItem {

    public BuzzyNestItem(Properties properties) {
        super(properties);
        procOnItemUse = true;
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

            if (itemUseContextPlayer != null) {
                ISummoner summonerCap = getSummonerCapability(itemUseContextPlayer);
                if (summonerCap != null) {
                    if (summonerCap.hasNoBuzzyNestBees()) {
                        for (int i = 0; i < 3; i++) {
                            BeeEntity beeEntity = EntityType.BEE.create(world);
                            if (beeEntity != null) {
                                ISummonable summonable = getSummonableCapability(beeEntity);
                                if (summonable != null) {

                                    summonable.setSummoner(itemUseContextPlayer.getUUID());
                                    summonerCap.addBuzzyNestBee(beeEntity.getUUID());

                                    createBuzzyNestBee(world, itemUseContextPlayer, blockPos, beeEntity);


                                    itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemUseContextItem)));

                                }
                            }
                        }
                    } else {
                        if (world instanceof ServerWorld) {
                            for (int i = 0; i < 3; i++) {
                                UUID beeUUID = summonerCap.getBuzzyNestBees()[i];
                                if (beeUUID == null) continue;
                                Entity entity = ((ServerWorld) world).getEntity(beeUUID);
                                if (entity instanceof BeeEntity) {
                                    BeeEntity summonedBeeEntity = (BeeEntity) entity;
                                    summonedBeeEntity.teleportToWithTicket((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                                }
                            }
                        }
                    }
                }
            }
            return ActionResult.consume(itemUseContext.getItemInHand());
        }
    }

    private void createBuzzyNestBee(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, BeeEntity beeEntity) {
        beeEntity.moveTo((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

        beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

        SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.BEE_LOOP);
        world.addFreshEntity(beeEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 23;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
