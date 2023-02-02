package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.items.interfaces.SummoningArtifact;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TastyBoneItem extends ArtifactItem implements SummoningArtifact<Wolf> {
    public TastyBoneItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
        procOnItemUse = true;
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        return this.procSummoningArtifact(itemUseContext);
    }

    @Override
    public void onSummoned(Player player, Entity summonEntity) {
        if (summonEntity instanceof Wolf wolf) {
            wolf.tame(player);
        }
        SoundHelper.playCreatureSound(player, SoundEvents.WOLF_AMBIENT);
    }

    @Override
    public EntityType<Wolf> getSummonType() {
        return EntityType.WOLF;
    }
}
