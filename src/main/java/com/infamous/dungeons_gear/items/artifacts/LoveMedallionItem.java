package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.PacketBreakItem;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMaster;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMinion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.LOVE_MEDALLION_BLACKLIST;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;

public class LoveMedallionItem extends ArtifactItem {
    public LoveMedallionItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ArtifactUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

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
                minionCapability.setTemporary(true);
                minionCapability.setRevertsOnExpiration(true);
                minionCapability.setMinionTimer(200);
                MinionMasterHelper.addMinionGoals(mobEntity);
            }
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
