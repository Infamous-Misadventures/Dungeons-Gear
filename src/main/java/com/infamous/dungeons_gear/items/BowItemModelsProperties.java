package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_libraries.items.gearconfig.BowGear;
import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static com.infamous.dungeons_gear.registry.ItemRegistry.RANGED_WEAPONS;

public class BowItemModelsProperties {

	public BowItemModelsProperties() {
		RANGED_WEAPONS.values().forEach(itemRegistryObject ->  {
			if(itemRegistryObject.get() instanceof BowGear){
				applyBowModelProperties(itemRegistryObject.get());
			}
		});
	}

	private void applyBowModelProperties(Item item) {
		ItemModelsProperties.register(item, new ResourceLocation("pull"),
				this::getPullProperty);
		ItemModelsProperties.register(item, new ResourceLocation("pulling"),
				this::getPullingProperty);
	}

	private float getPullingProperty(ItemStack stack, ClientWorld clientWorld, LivingEntity livingEntity) {
		return livingEntity != null && livingEntity.isUsingItem()
				&& livingEntity.getUseItem() == stack
				? 1.0F
				: 0.0F;
	}

	private float getPullProperty(ItemStack stack, ClientWorld clientWorld, LivingEntity livingEntity) {
		if (livingEntity == null) {
			return 0.0F;
		} else {
			return !(livingEntity.getUseItem().getItem() instanceof BowGear) ? 0.0F
					: (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
					/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
		}
	}
}