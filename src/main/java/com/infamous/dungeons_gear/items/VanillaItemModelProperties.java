package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;

public class VanillaItemModelProperties {
    public VanillaItemModelProperties(){
        Map<Item, Map<ResourceLocation, IItemPropertyGetter>> itemModelsProperties = ItemModelsProperties.PROPERTIES;
        if(itemModelsProperties != null){
            Map<ResourceLocation, IItemPropertyGetter> bowModelProperties = itemModelsProperties.get(Items.BOW);
            Map<ResourceLocation, IItemPropertyGetter> crossbowModelProperties = itemModelsProperties.get(Items.CROSSBOW);

            bowModelProperties.put(new ResourceLocation("pull"),
                    (stack, clientWorld, livingEntity) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return livingEntity.getUseItem() != stack ? 0.0F
                                    : (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
                                    / RangedAttackHelper.getVanillaBowChargeTime(livingEntity, livingEntity.getUseItem());
                        }
                    });
            bowModelProperties.put(new ResourceLocation("pulling"),
                    (stack, clientWorld, livingEntity) -> {
                        return livingEntity != null && livingEntity.isUsingItem()
                                && livingEntity.getUseItem() == stack
                                ? 1.0F
                                : 0.0F;
                    });

            crossbowModelProperties.put(new ResourceLocation("pull"),
                    (stack, clientWorld, livingEntity) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return CrossbowItem.isCharged(stack) ? 0.0F
                                    : (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
                                    / (float) RangedAttackHelper.getVanillaCrossbowChargeTime(livingEntity, stack);
                        }
                    });
            crossbowModelProperties.put(new ResourceLocation("pulling"),
                    (stack, clientWorld, livingEntity) -> {
                        return livingEntity != null && livingEntity.isUsingItem()
                                && livingEntity.getUseItem() == stack && !CrossbowItem.isCharged(stack)
                                ? 1.0F
                                : 0.0F;
                    });
            crossbowModelProperties.put(new ResourceLocation("charged"),
                    (stack, clientWorld, livingEntity) -> {
                        return livingEntity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
                    });
        }
        else{
            DungeonsGear.LOGGER.error("Reflection error for ItemModelsProperties!");
        }
    }
}
