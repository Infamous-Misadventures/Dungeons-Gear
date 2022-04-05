package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMinion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMaster;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.goals.LlamaFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.LlamaOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.LlamaOwnerHurtTargetGoal;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.UUID;


import net.minecraft.item.Item.Properties;

import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SUMMON_CAP;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMinionCapability;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;

public class WonderfulWheatItem extends ArtifactItem {
    public WonderfulWheatItem(Properties p_i48487_1_) {
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
                    if(summonerCap.getSummonedLlama() == null){
                        LlamaEntity llamaEntity = EntityType.LLAMA.create(world);
                        if (llamaEntity!= null) {
                            IMinion summon = getMinionCapability(llamaEntity);
                            if(summon != null){

                                summon.setMaster(itemUseContextPlayer.getUUID());
                                summonerCap.setSummonedLlama(llamaEntity.getUUID());

                                createLlama(world, itemUseContextPlayer, blockPos, llamaEntity);

                                itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemUseContextItem)));

                            }
                        }
                    } else{
                        if(world instanceof ServerWorld){
                            Entity entity = ((ServerWorld)world).getEntity(summonerCap.getSummonedLlama());
                            if(entity instanceof LlamaEntity){
                                LlamaEntity llamaEntity = (LlamaEntity) entity;
                                llamaEntity.teleportToWithTicket((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D);
                            }
                        }
                    }
                }
            }

            return ActionResult.consume(itemUseContextItem);
        }
    }

    private void createLlama(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, LlamaEntity llamaEntity) {
        llamaEntity.tameWithName(itemUseContextPlayer);
        llamaEntity.setVariant(2);
        llamaEntity.setStrength(5);
        ModifiableAttributeInstance maxHealth = llamaEntity.getAttribute(Attributes.MAX_HEALTH);
        if(maxHealth != null)
            maxHealth.setBaseValue(30.0D);
        llamaEntity.inventory.setItem(1, new ItemStack(Items.RED_CARPET.asItem()));
        llamaEntity.moveTo((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        llamaEntity.targetSelector.addGoal(1, new LlamaOwnerHurtByTargetGoal(llamaEntity));
        llamaEntity.targetSelector.addGoal(2, new LlamaOwnerHurtTargetGoal(llamaEntity));
        llamaEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(llamaEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
            return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
        }));
        llamaEntity.goalSelector.addGoal(2, new LlamaFollowOwnerGoal(llamaEntity, 2.1D, 10.0F, 2.0F, false));

        SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.LLAMA_AMBIENT);

        world.addFreshEntity(llamaEntity);
    }

    @OnlyIn(Dist.CLIENT)
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

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
    }

    private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(SUMMON_CAP.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 2, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
