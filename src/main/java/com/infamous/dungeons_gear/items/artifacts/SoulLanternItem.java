package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.entities.SoulWizardEntity;
import com.infamous.dungeons_gear.items.interfaces.SummoningArtifact;
import com.infamous.dungeons_gear.registry.EntityTypeInit;
import com.infamous.dungeons_gear.registry.SoundEventInit;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SoulLanternItem extends ArtifactItem implements SummoningArtifact<SoulWizardEntity> {
    public SoulLanternItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
        procOnItemUse = true;
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        return this.procSummoningArtifact(itemUseContext);
    }

    @Override
    public void onSummoned(Player player, Entity summonEntity) {
        SoundHelper.playCreatureSound(player, SoundEventInit.SOUL_WIZARD_APPEAR.get());
    }

    @Override
    public EntityType<SoulWizardEntity> getSummonType() {
        return EntityTypeInit.SOUL_WIZARD.get();
    }
}
