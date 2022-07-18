package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

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
        addDualProperty(ItemRegistry.THE_LAST_LAUGH.get());
    }

    private static void addDualProperty(Item i) {
        ItemProperties.register(i, new ResourceLocation("dual"),
                (stack, world, entity, todo) -> {
                    if (entity == null) {
                        return 1.0F;
                    } else {
                        return entity.getOffhandItem() == stack || entity.getMainHandItem() == stack ? 0.0F
                                : 1.0F;
                    }
                });
    }
}
