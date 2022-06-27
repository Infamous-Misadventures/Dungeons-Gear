package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMaster;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMinion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.LOVE_MEDALLION_BLACKLIST;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;

public class LoveMedallionItem extends ArtifactItem {
    public LoveMedallionItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ArtifactUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();
        World world = c.getLevel();

        applyToNearbyEntities(playerIn, 5, 2,
                (nearbyEntity) -> nearbyEntity instanceof IMob
                        && !MinionMasterHelper.isMinionEntity(nearbyEntity)
                        && nearbyEntity.isAlive()
                        && nearbyEntity.canChangeDimensions()
                        && !LOVE_MEDALLION_BLACKLIST.get().contains(nearbyEntity.getType().getRegistryName().toString()),
                (LivingEntity nearbyEntity) -> makeMinionOf(playerIn, nearbyEntity));

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    private void makeMinionOf(PlayerEntity playerIn, LivingEntity nearbyEntity) {
        if (nearbyEntity instanceof MonsterEntity) {
            MonsterEntity mobEntity = (MonsterEntity) nearbyEntity;
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
            IMaster masterCapability = MinionMasterHelper.getMasterCapability(playerIn);
            IMinion minionCapability = MinionMasterHelper.getMinionCapability(nearbyEntity);
            if(masterCapability != null && minionCapability != null){
                masterCapability.addMinion(mobEntity);
                minionCapability.setMaster(playerIn);
                minionCapability.setLimitedLifetime(true);
                minionCapability.setLifeTimer(200);
                MinionMasterHelper.addMinionGoals(mobEntity);
            }
        }
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
        return 35;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
