package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.items.interfaces.SummoningArtifact;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GolemKitItem extends ArtifactItem implements SummoningArtifact<IronGolem> {
    public GolemKitItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
        procOnItemUse = true;
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        return this.procSummoningArtifact(itemUseContext);
    }

    @Override
    public void onSummoned(Player player, Entity summonEntity) {
        if (summonEntity instanceof IronGolem ironGolem) {
            ironGolem.setPlayerCreated(true);
        }
        SoundHelper.playCreatureSound(player, SoundEvents.IRON_GOLEM_REPAIR);
    }

    @Override
    public EntityType<IronGolem> getSummonType() {
        return EntityType.IRON_GOLEM;
    }

}
