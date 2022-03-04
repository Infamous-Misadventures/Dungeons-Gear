package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMinion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMaster;
import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.goals.*;
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
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.UUID;

import net.minecraft.item.Item.Properties;

import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMinionCapability;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;

public class EnchantedGrassItem extends ArtifactItem {

    public EnchantedGrassItem(Properties properties) {
        super(properties);
        procOnItemUse=true;
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext itemUseContext) {
        World world = itemUseContext.getLevel();
        if (world.isClientSide) {
            return ActionResult.success(itemUseContext.getItemInHand());
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItemInHand();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
            Hand itemUseContextHand = itemUseContext.getHand();
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
                    if(summonerCap.getSummonedSheep() == null){
                        SheepEntity sheepEntity = EntityType.SHEEP.create(world);
                        if (sheepEntity!= null) {
                            IMinion summon = getMinionCapability(sheepEntity);
                            if(summon != null){

                                summon.setMaster(itemUseContextPlayer.getUUID());
                                summonerCap.setSummonedSheep(sheepEntity.getUUID());

                                createEnchantedSheep(world, itemUseContextPlayer, blockPos, sheepEntity);

                                itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemUseContextItem)));
                            }
                        }
                    } else{
                        if(world instanceof ServerWorld){
                            Entity entity = ((ServerWorld)world).getEntity(summonerCap.getSummonedSheep());
                            if(entity instanceof SheepEntity){
                                SheepEntity sheepEntity = (SheepEntity) entity;
                                sheepEntity.teleportToWithTicket((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D);
                            }
                        }
                    }
                }
            }
            return ActionResult.consume(itemUseContextItem);
        }
    }

    private void createEnchantedSheep(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos, SheepEntity sheepEntity) {
        this.setSheepEnchantmentAndAI(sheepEntity);
        sheepEntity.moveTo((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);

        SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.SHEEP_AMBIENT);

        world.addFreshEntity(sheepEntity);
    }

    private void setSheepEnchantmentAndAI(SheepEntity sheepEntity){
        int sheepEnchantment = sheepEntity.getRandom().nextInt(3);
        sheepEntity.goalSelector.addGoal(2, new SheepFollowOwnerGoal(sheepEntity, 2.1D, 10.0F, 2.0F, false));
        if(sheepEnchantment == 0){
            sheepEntity.setColor(DyeColor.RED);
            sheepEntity.addTag("Fire");
            sheepEntity.goalSelector.addGoal(1, new SheepMeleeAttackGoal(sheepEntity, 1.0D, true));
            sheepEntity.targetSelector.addGoal(1, new SheepOwnerHurtByTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(2, new SheepOwnerHurtTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(sheepEntity, LivingEntity.class, 5, false, false,
                    (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
        }
        else if(sheepEnchantment == 1){
            sheepEntity.setColor(DyeColor.GREEN);
            sheepEntity.addTag("Poison");
            sheepEntity.goalSelector.addGoal(1, new SheepMeleeAttackGoal(sheepEntity, 1.0D, true));
            sheepEntity.targetSelector.addGoal(1, new SheepOwnerHurtByTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(2, new SheepOwnerHurtTargetGoal(sheepEntity));
            sheepEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(sheepEntity, LivingEntity.class, 5, false, false,
                    (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));
        }
        else{
            sheepEntity.setColor(DyeColor.BLUE);
            sheepEntity.addTag("Speed");
        }
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

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
    }

    private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(SOUL_GATHERING.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 1, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
