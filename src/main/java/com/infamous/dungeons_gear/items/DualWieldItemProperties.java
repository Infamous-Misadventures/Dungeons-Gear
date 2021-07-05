package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class DualWieldItemProperties {
    public DualWieldItemProperties() {
        addDualProperty(ItemRegistry.DUAL_CROSSBOW.get());
        addDualProperty(ItemRegistry.BABY_CROSSBOW.get());
        addDualProperty(ItemRegistry.DAGGER.get());
        addDualProperty(ItemRegistry.MOON_DAGGER.get());
        addDualProperty(ItemRegistry.SHEAR_DAGGER.get());
        addDualProperty(ItemRegistry.FANG_OF_FROST.get());
        addDualProperty(ItemRegistry.GAUNTLET.get());
        addDualProperty(ItemRegistry.FIGHTERS_BINDING.get());
        addDualProperty(ItemRegistry.SOUL_FIST.get());
        addDualProperty(ItemRegistry.MAULER.get());
        addDualProperty(ItemRegistry.SICKLE.get());
        addDualProperty(ItemRegistry.NIGHTMARES_BITE.get());
        ItemModelsProperties.registerProperty(ItemRegistry.THE_LAST_LAUGH.get(), new ResourceLocation("dual"),
                (stack, world, entity) -> {
                    if (entity == null) {
                        return 1.0F;
                    } else {
                        return entity.getHeldItemMainhand() == stack || entity.getHeldItemOffhand() == stack ? 0.0F
                                : 1.0F;
                    }
                });
        ItemModelsProperties.registerProperty(ItemRegistry.THE_LAST_LAUGH.get(), new ResourceLocation("offhand"),
                (stack, world, entity) -> {
                    if (entity == null) {
                        return 0.0F;
                    } else {
                        return entity.getHeldItemOffhand() == stack ? 1.0F
                                : 0.0F;
                    }
                });
    }

    private static void addDualProperty(Item i) {
        ItemModelsProperties.registerProperty(i, new ResourceLocation("dual"),
                (stack, world, entity) -> {
                    if (entity == null) {
                        return 1.0F;
                    } else {
                        return entity.getHeldItemOffhand() == stack || entity.getHeldItemMainhand() == stack ? 0.0F
                                : 1.0F;
                    }
                });
    }
}
