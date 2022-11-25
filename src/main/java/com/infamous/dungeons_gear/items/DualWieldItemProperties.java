package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class DualWieldItemProperties {
    public DualWieldItemProperties() {
        addDualProperty(ItemRegistry.DUAL_CROSSBOW.get());
        addDualProperty(ItemRegistry.BABY_CROSSBOW.get());
    }

    private static void addDualProperty(Item i) {
        ItemModelsProperties.register(i, new ResourceLocation("dual"),
                (stack, world, entity) -> {
                    if (entity == null) {
                        return 1.0F;
                    } else {
                        return entity.getOffhandItem() == stack || entity.getMainHandItem() == stack ? 0.0F
                                : 1.0F;
                    }
                });
    }
}
