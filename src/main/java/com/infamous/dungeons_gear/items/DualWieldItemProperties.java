package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.registry.ItemInit;
import net.minecraft.world.item.Item;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class DualWieldItemProperties {
    public DualWieldItemProperties() {
        addDualProperty(ItemInit.DUAL_CROSSBOW.get());
        addDualProperty(ItemInit.BABY_CROSSBOW.get());
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
