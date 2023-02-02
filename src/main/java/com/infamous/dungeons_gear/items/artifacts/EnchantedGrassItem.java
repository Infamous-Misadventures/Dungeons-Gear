package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.items.interfaces.SummoningArtifact;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class EnchantedGrassItem extends ArtifactItem implements SummoningArtifact<Sheep> {

    public EnchantedGrassItem(Properties properties) {
        super(properties);
        procOnItemUse = true;
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        return this.procSummoningArtifact(itemUseContext);
    }

    @Override
    public void onSummoned(Player player, Entity summonEntity) {
        if (summonEntity instanceof Sheep sheep) {
            int sheepEnchantment = sheep.getRandom().nextInt(3);
            if (sheepEnchantment == 0) {
                sheep.setColor(DyeColor.RED);
                sheep.addTag("Fire");
            } else if (sheepEnchantment == 1) {
                sheep.setColor(DyeColor.GREEN);
                sheep.addTag("Poison");
            } else {
                sheep.setColor(DyeColor.BLUE);
                sheep.addTag("Speed");
            }
        }
        SoundHelper.playCreatureSound(player, SoundEvents.SHEEP_AMBIENT);
    }

    @Override
    public EntityType<Sheep> getSummonType() {
        return EntityType.SHEEP;
    }
}
