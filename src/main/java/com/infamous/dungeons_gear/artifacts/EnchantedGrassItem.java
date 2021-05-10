package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
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
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class EnchantedGrassItem extends ArtifactItem {

    public EnchantedGrassItem(Properties properties) {
        super(properties);
        procOnItemUse=true;
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        if (world.isRemote) {
            return ActionResult.resultSuccess(itemUseContext.getItem());
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
                    if(summonerCap.getSummonedSheep() == null){
                        SheepEntity sheepEntity = EntityType.SHEEP.create(world);
                        if (sheepEntity!= null) {
                            ISummonable summon = CapabilityHelper.getSummonableCapability(sheepEntity);
                            if(summon != null){

                                summon.setSummoner(itemUseContextPlayer.getUniqueID());
                                summonerCap.setSummonedSheep(sheepEntity.getUniqueID());

                                createEnchantedSheep(world, itemUseContextPlayer, blockPos, sheepEntity);

                                itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemUseContextItem)));
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
            }
            return ActionResult.resultConsume(itemUseContextItem);
        }
    }

    private void createEnchantedSheep(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, SheepEntity sheepEntity) {
        this.setSheepEnchantmentAndAI(sheepEntity);
        sheepEntity.setLocationAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        world.playSound((PlayerEntity)null, itemUseContextPlayer.getPosX(), itemUseContextPlayer.getPosY(), itemUseContextPlayer.getPosZ(), SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.AMBIENT, 64.0F, 1.0F);

        world.addEntity(sheepEntity);
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

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "Just as there are powerful heroes who answer the call to fight, there are powerful enchanted sheep who will join the fight when summoned."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Randomly summons one of three sheep allies that can grant either speed, poison, or fire effects."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
    }
}
