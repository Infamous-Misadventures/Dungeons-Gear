package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.items.ranged.crossbows.*;
import com.infamous.dungeons_libraries.items.gearconfig.CrossbowGear;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;

import static com.infamous.dungeons_gear.registry.ItemRegistry.RANGED_WEAPONS;
import static com.infamous.dungeons_libraries.utils.RangedAttackHelper.getModdedCrossbowChargeTime;

public class CrossbowItemModelsProperties {

	public CrossbowItemModelsProperties() {
		RANGED_WEAPONS.values().forEach(itemRegistryObject ->  {
			if(itemRegistryObject.get() instanceof CrossbowGear && !(itemRegistryObject.get() instanceof DualCrossbowGear)){
				applyCrossbowModelProperties(itemRegistryObject.get());
			}
			if(itemRegistryObject.get() instanceof CrossbowGear && itemRegistryObject.get() instanceof DualCrossbowGear){
				applyDualWieldCrossbowModelProperties(itemRegistryObject.get());
			}
		});
	}

	private float getDualWieldPullingProperty(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
		return (
				(livingEntity != null
						&& livingEntity.isUsingItem()
						&& livingEntity.getUseItem() == itemStack
						&& !DualCrossbowGear.isCharged(itemStack))

						|| ((livingEntity != null
						&& livingEntity.isUsingItem()
						&& livingEntity.getUsedItemHand() == Hand.MAIN_HAND
						&& livingEntity.getUseItem().getItem() instanceof DualCrossbowGear
						&& livingEntity.getOffhandItem().getItem() instanceof DualCrossbowGear
						&& !DualCrossbowGear.isCharged(itemStack))))

				? 1.0F
				: 0.0F;
	}

	private void applyDualWieldCrossbowModelProperties(Item item) {
		ItemModelsProperties.register(item, new ResourceLocation("pull"),
				this::getPullProperty);
		ItemModelsProperties.register(item, new ResourceLocation("pulling"),
				this::getDualWieldPullingProperty);
		ItemModelsProperties.register(item, new ResourceLocation("charged"),
				this::getChargedProperty);
	}

	private void applyCrossbowModelProperties(Item item) {
		ItemModelsProperties.register(item, new ResourceLocation("pull"),
				this::getPullProperty);
		ItemModelsProperties.register(item, new ResourceLocation("pulling"),
				this::getPullingProperty);
		ItemModelsProperties.register(item, new ResourceLocation("charged"),
				this::getChargedProperty);
	}

	private float getPullProperty(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
		if (livingEntity == null) {
			return 0.0F;
		} else {
			return CrossbowGear.isCharged(itemStack) ? 0.0F
					: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
					/ (float) getModdedCrossbowChargeTime(livingEntity, itemStack);
		}
	}

	private float getChargedProperty(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
		return livingEntity != null && CrossbowGear.isCharged(itemStack) ? 1.0F : 0.0F;
	}

	private float getPullingProperty(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity) {
		return livingEntity != null && livingEntity.isUsingItem()
				&& livingEntity.getUseItem() == itemStack && !CrossbowGear.isCharged(itemStack)
				? 1.0F
				: 0.0F;
	}
}