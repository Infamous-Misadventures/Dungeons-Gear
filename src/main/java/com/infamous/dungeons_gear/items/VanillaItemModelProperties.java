package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;

public class VanillaItemModelProperties {
    public VanillaItemModelProperties(){
        Map<Item, Map<ResourceLocation, IItemPropertyGetter>> itemModelsProperties =
                ObfuscationReflectionHelper.getPrivateValue(ItemModelsProperties.class, null, "field_239415_f_");
        if(itemModelsProperties != null){
            Map<ResourceLocation, IItemPropertyGetter> bowModelProperties = itemModelsProperties.get(Items.BOW);
            Map<ResourceLocation, IItemPropertyGetter> crossbowModelProperties = itemModelsProperties.get(Items.CROSSBOW);

            bowModelProperties.put(new ResourceLocation("pull"),
                    (stack, clientWorld, livingEntity) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return livingEntity.getActiveItemStack() != stack ? 0.0F
                                    : (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
                                    / RangedAttackHelper.getVanillaBowChargeTime(livingEntity.getActiveItemStack());
                        }
                    });
            bowModelProperties.put(new ResourceLocation("pulling"),
                    (stack, clientWorld, livingEntity) -> {
                        return livingEntity != null && livingEntity.isHandActive()
                                && livingEntity.getActiveItemStack() == stack
                                ? 1.0F
                                : 0.0F;
                    });

            crossbowModelProperties.put(new ResourceLocation("pull"),
                    (stack, clientWorld, livingEntity) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return CrossbowItem.isCharged(stack) ? 0.0F
                                    : (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
                                    / (float) RangedAttackHelper.getVanillaCrossbowChargeTime(stack);
                        }
                    });
            crossbowModelProperties.put(new ResourceLocation("pulling"),
                    (stack, clientWorld, livingEntity) -> {
                        return livingEntity != null && livingEntity.isHandActive()
                                && livingEntity.getActiveItemStack() == stack && !CrossbowItem.isCharged(stack)
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
