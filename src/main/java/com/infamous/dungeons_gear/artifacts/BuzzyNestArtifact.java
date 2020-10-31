package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.goals.BeeFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtTargetGoal;
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
import net.minecraft.entity.passive.BeeEntity;
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
import java.util.UUID;

public class BuzzyNestArtifact extends ArtifactItem {

    public BuzzyNestArtifact(Properties properties) {
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

            if(itemUseContextPlayer != null){
                ISummoner summonerCap = CapabilityHelper.getSummonerCapability(itemUseContextPlayer);
                if (summonerCap != null) {
                    if (summonerCap.hasNoBuzzyNestBees()) {
                        for(int i = 0; i < 3; i++){
                            BeeEntity beeEntity = EntityType.BEE.create(world);
                            if(beeEntity!= null){
                                ISummonable summonable = CapabilityHelper.getSummonableCapability(beeEntity);
                                if(summonable != null){

                                    summonable.setSummoner(itemUseContextPlayer.getUniqueID());
                                    summonerCap.addBuzzyNestBee(beeEntity.getUniqueID());

                                    createBuzzyNestBee(world, itemUseContextPlayer, blockPos, beeEntity);


                                    if(!itemUseContextPlayer.isCreative()){
                                        itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> {
                                            entity.sendBreakAnimation(itemUseContextHand);
                                        });
                                    }
                                }
                            }
                        }
                    }
                    else{
                        if(world instanceof ServerWorld) {
                            for (int i = 0; i < 3; i++) {
                                UUID beeUUID = summonerCap.getBuzzyNestBees()[i];
                                if (beeUUID == null) continue;
                                Entity entity = ((ServerWorld) world).getEntityByUuid(beeUUID);
                                if (entity instanceof BeeEntity) {
                                    BeeEntity summonedBeeEntity = (BeeEntity) entity;
                                    summonedBeeEntity.teleportKeepLoaded((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                                }
                            }
                        }
                    }
                }
            }
            return ActionResultType.CONSUME;
        }
    }

    private void createBuzzyNestBee(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, BeeEntity beeEntity) {
        beeEntity.setLocationAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

        beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

        world.playSound((PlayerEntity)null, itemUseContextPlayer.getPosX(), itemUseContextPlayer.getPosY(), itemUseContextPlayer.getPosZ(), SoundEvents.ENTITY_BEE_LOOP, SoundCategory.AMBIENT, 64.0F, 1.0F);
        world.addEntity(beeEntity);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.BUZZY_NEST){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "Bee lovers and the bee-loved alike are fans of the Buzzy Nest, but don't be fooled by the cute bees within - they pack a powerful sting!"));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "When the Buzzy Nest is placed on the ground, bees who will fight beside you begin to spawn."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "23 Seconds Cooldown"));
        }
    }
}
