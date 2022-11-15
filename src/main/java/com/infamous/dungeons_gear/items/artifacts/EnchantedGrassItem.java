package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMaster;
import com.infamous.dungeons_libraries.summon.SummonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;
import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;

public class EnchantedGrassItem extends ArtifactItem {

    public EnchantedGrassItem(Properties properties) {
        super(properties);
        procOnItemUse=true;
    }

    public ActionResult<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        World world = itemUseContext.getLevel();
        if (world.isClientSide || itemUseContext.isHitMiss()) {
            return ActionResult.success(itemUseContext.getItemStack());
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItemStack();
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
                    Entity summoned = SummonHelper.summonEntity(itemUseContextPlayer, itemUseContextPlayer.blockPosition(), EntityType.SHEEP);
                    if(summoned != null) {
                        if(summoned instanceof SheepEntity) {
                            setSheepEnchantment((SheepEntity) summoned);
                        }
                        SoundHelper.playCreatureSound(itemUseContextPlayer, SoundEvents.SHEEP_AMBIENT);
                        itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemUseContextItem)));
                        ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
                    } else{
                        if(world instanceof ServerWorld){
                            List<Entity> sheepEntities = summonerCap.getSummonedMobs().stream().filter(entity -> entity.getType() == EntityType.SHEEP).collect(Collectors.toList());
                            sheepEntities.forEach(entity -> {
                                if (entity instanceof SheepEntity) {
                                    SheepEntity sheepEntity = (SheepEntity) entity;
                                    sheepEntity.teleportToWithTicket((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.05D, (double) blockPos.getZ() + 0.5D);
                                }
                            });
                        }
                    }
                }
            }
            return ActionResult.consume(itemUseContextItem);
        }
    }

    private void setSheepEnchantment(SheepEntity sheepEntity){
        int sheepEnchantment = sheepEntity.getRandom().nextInt(3);
        if(sheepEnchantment == 0) {
            sheepEntity.setColor(DyeColor.RED);
            sheepEntity.addTag("Fire");
        }else if(sheepEnchantment == 1){
            sheepEntity.setColor(DyeColor.GREEN);
            sheepEntity.addTag("Poison");
        } else{
            sheepEntity.setColor(DyeColor.BLUE);
            sheepEntity.addTag("Speed");
        }
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
