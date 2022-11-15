package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Master;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Minion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.LOVE_MEDALLION_BLACKLIST;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;

import net.minecraft.world.item.Item.Properties;

public class LoveMedallionItem extends ArtifactItem {
    public LoveMedallionItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
        Player playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        applyToNearbyEntities(playerIn, 5, 2,
                (nearbyEntity) -> nearbyEntity instanceof Enemy
                        && !MinionMasterHelper.isMinionEntity(nearbyEntity)
                        && nearbyEntity.isAlive()
                        && nearbyEntity.canChangeDimensions()
                        && !LOVE_MEDALLION_BLACKLIST.get().contains(nearbyEntity.getType().getRegistryName().toString()),
                (LivingEntity nearbyEntity) -> makeMinionOf(playerIn, nearbyEntity));

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    private void makeMinionOf(Player playerIn, LivingEntity nearbyEntity) {
        if (nearbyEntity instanceof Monster) {
            Monster mobEntity = (Monster) nearbyEntity;
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
            Master masterCapability = MinionMasterHelper.getMasterCapability(playerIn);
            Minion minionCapability = MinionMasterHelper.getMinionCapability(nearbyEntity);
            masterCapability.addMinion(mobEntity);
            minionCapability.setMaster(playerIn);
            minionCapability.setTemporary(true);
            minionCapability.setRevertsOnExpiration(true);
            minionCapability.setMinionTimer(200);
            MinionMasterHelper.addMinionGoals(mobEntity);
        }
    }

    @Override
    public int getCooldownInSeconds() {
        return 40;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
