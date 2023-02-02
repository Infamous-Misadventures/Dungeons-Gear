package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.items.interfaces.SummoningArtifact;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WonderfulWheatItem extends ArtifactItem implements SummoningArtifact<Llama> {
    public WonderfulWheatItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
        procOnItemUse = true;
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        return this.procSummoningArtifact(itemUseContext);
    }

    @Override
    public void onSummoned(Player player, Entity summonEntity) {
        if (summonEntity instanceof Llama llama) {
            llama.tameWithName(player);
            llama.setVariant(2);
            llama.setStrength(5);
            AttributeInstance maxHealth = llama.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth != null)
                maxHealth.setBaseValue(30.0D);
            llama.inventory.setItem(1, new ItemStack(Items.RED_CARPET.asItem()));
        }
        SoundHelper.playCreatureSound(player, SoundEvents.LLAMA_AMBIENT);
    }

    @Override
    public EntityType<Llama> getSummonType() {
        return EntityType.LLAMA;
    }
}
