package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.goals.LlamaFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.LlamaOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.LlamaOwnerHurtTargetGoal;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.PacketDistributor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class WonderfulWheatItem extends ArtifactItem {
    public WonderfulWheatItem(Properties p_i48487_1_) {
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
                    if(summonerCap.getSummonedLlama() == null){
                        LlamaEntity llamaEntity = EntityType.LLAMA.create(world);
                        if (llamaEntity!= null) {
                            ISummonable summon = CapabilityHelper.getSummonableCapability(llamaEntity);
                            if(summon != null){

                                summon.setSummoner(itemUseContextPlayer.getUniqueID());
                                summonerCap.setSummonedLlama(llamaEntity.getUniqueID());

                                createLlama(world, itemUseContextPlayer, blockPos, llamaEntity);

                                itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemUseContextItem)));

                            }
                        }
                    } else{
                        if(world instanceof ServerWorld){
                            Entity entity = ((ServerWorld)world).getEntityByUuid(summonerCap.getSummonedLlama());
                            if(entity instanceof LlamaEntity){
                                LlamaEntity llamaEntity = (LlamaEntity) entity;
                                llamaEntity.teleportKeepLoaded((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D);
                            }
                        }
                    }
                }
            }

            return ActionResult.resultConsume(itemUseContextItem);
        }
    }

    private void createLlama(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, LlamaEntity llamaEntity) {
        llamaEntity.setTamedBy(itemUseContextPlayer);
        llamaEntity.setVariant(2);

        Method setStrength = ObfuscationReflectionHelper.findMethod(LlamaEntity.class, "func_190706_p", Integer.TYPE);
        try {
            setStrength.invoke(llamaEntity, 5);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //llamaEntity.setStrength(5);
        ModifiableAttributeInstance maxHealth = llamaEntity.getAttribute(Attributes.MAX_HEALTH);
        if(maxHealth != null)
            maxHealth.setBaseValue(30.0D);
        Inventory horseChest = ObfuscationReflectionHelper.getPrivateValue(AbstractHorseEntity.class, llamaEntity, "field_110296_bG");
        if (horseChest != null) {
            horseChest.setInventorySlotContents(1, new ItemStack(Items.RED_CARPET.asItem()));
        }

        llamaEntity.setLocationAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        llamaEntity.targetSelector.addGoal(1, new LlamaOwnerHurtByTargetGoal(llamaEntity));
        llamaEntity.targetSelector.addGoal(2, new LlamaOwnerHurtTargetGoal(llamaEntity));
        llamaEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(llamaEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
            return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
        }));
        llamaEntity.goalSelector.addGoal(2, new LlamaFollowOwnerGoal(llamaEntity, 2.1D, 10.0F, 2.0F, false));

        world.playSound((PlayerEntity)null, itemUseContextPlayer.getPosX(), itemUseContextPlayer.getPosY(), itemUseContextPlayer.getPosZ(), SoundEvents.ENTITY_LLAMA_ANGRY, SoundCategory.AMBIENT, 64.0F, 1.0F);

        world.addEntity(llamaEntity);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "This item has the faint smell of an open field, baked bread, and freshly cut crops."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Summons a llama to aid you in battle."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
    }
}
