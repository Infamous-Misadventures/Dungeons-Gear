package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class EnchantedGrassArtifact extends Item implements IArtifact {

    public EnchantedGrassArtifact(Properties properties) {
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
                ISummoner summonerCap = itemUseContextPlayer.getCapability(SummonerProvider.SUMMONER_CAPABILITY).orElseThrow(IllegalStateException::new);
                if(summonerCap.getSummonedSheep() == null){
                    SheepEntity sheepEntity = EntityType.SHEEP.create(world);
                    if (sheepEntity!= null) {
                        this.setSheepEnchantmentAndAI(sheepEntity);
                        sheepEntity.setLocationAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

                        world.playSound((PlayerEntity)null, itemUseContextPlayer.getPosX(), itemUseContextPlayer.getPosY(), itemUseContextPlayer.getPosZ(), SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.AMBIENT, 64.0F, 1.0F);

                        world.addEntity(sheepEntity);
                        ISummonable summon = sheepEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
                        summon.setSummoner(itemUseContextPlayer.getUniqueID());

                        summonerCap.setSummonedSheep(sheepEntity.getUniqueID());

                        if(!itemUseContextPlayer.isCreative()){
                            itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> {
                                entity.sendBreakAnimation(itemUseContextHand);
                            });
                        }
                    }
                } else{
                    if(world instanceof ServerWorld){
                        Entity entity = ((ServerWorld)world).getEntityByUuid(summonerCap.getSummonedSheep());
                        if(entity instanceof SheepEntity){
                            SheepEntity sheepEntity = (SheepEntity) entity;
                            sheepEntity.teleportKeepLoaded((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D);
                        }
                    }
                }
            }
            return ActionResultType.CONSUME;
        }
    }

    private void setSheepEnchantmentAndAI(SheepEntity sheepEntity){
        int sheepEnchantment = sheepEntity.getRNG().nextInt(3);
        sheepEntity.goalSelector.addGoal(2, new SheepFollowOwnerGoal(sheepEntity, 2.1D, 10.0F, 2.0F, false));
        if(sheepEnchantment == 0){
            sheepEntity.setFleeceColor(DyeColor.RED);
            sheepEntity.addTag("Fire");
            sheepEntity.goalSelector.addGoal(1, new SheepMeleeAttackGoal(sheepEntity, 1.0D, true));
            sheepEntity.targetSelector.addGoal(1, new SheepOwnerHurtByTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(2, new SheepOwnerHurtTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(sheepEntity, LivingEntity.class, 5, false, false,
                    (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
        }
        else if(sheepEnchantment == 1){
            sheepEntity.setFleeceColor(DyeColor.GREEN);
            sheepEntity.addTag("Poison");
            sheepEntity.goalSelector.addGoal(1, new SheepMeleeAttackGoal(sheepEntity, 1.0D, true));
            sheepEntity.targetSelector.addGoal(1, new SheepOwnerHurtByTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(2, new SheepOwnerHurtTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(sheepEntity, LivingEntity.class, 5, false, false,
                    (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
        }
        else{
            sheepEntity.setFleeceColor(DyeColor.BLUE);
            sheepEntity.addTag("Speed");
        }
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.ENCHANTED_GRASS){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "Just as there are powerful heroes who answer the call to fight, there are powerful enchanted sheep who will join the fight when summoned."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Randomly summons one of three sheep allies that can grant either speed, poison, or fire effects."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
        }
    }
}
