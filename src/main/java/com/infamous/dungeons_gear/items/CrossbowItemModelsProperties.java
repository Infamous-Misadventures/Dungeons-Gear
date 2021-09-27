package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.ranged.crossbows.*;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;

public class CrossbowItemModelsProperties {

	public CrossbowItemModelsProperties() {
		// CROSSBOW
		/*
		ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return CrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) DungeonsHooks.getCrossbowChargeTime(itemStack);
					}
				});
		ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !CrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		 */


		// AUTO CROSSBOW
		ItemModelsProperties.register(ItemRegistry.AUTO_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
										/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.AUTO_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !RapidCrossbowItem.isCharged(itemStack)
									? 1.0F
									: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.AUTO_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && RapidCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// AZURE SEEKER
		ItemModelsProperties.register(ItemRegistry.AZURE_SEEKER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.AZURE_SEEKER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !DungeonsCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.AZURE_SEEKER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && DungeonsCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// BUTTERFLY CROSSBOW
		ItemModelsProperties.register(ItemRegistry.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !RapidCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && RapidCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// DOOM CROSSBOW
		ItemModelsProperties.register(ItemRegistry.DOOM_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.DOOM_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !HeavyCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.DOOM_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && HeavyCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// FERAL SOUL CROSSBOW
		ItemModelsProperties.register(ItemRegistry.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !SoulCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && SoulCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// FIREBOLT THROWER
		ItemModelsProperties.register(ItemRegistry.FIREBOLT_THROWER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.FIREBOLT_THROWER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !ExplodingCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.FIREBOLT_THROWER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ExplodingCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// HARP CROSSBOW
		ItemModelsProperties.register(ItemRegistry.HARP_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.HARP_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !ScatterCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.HARP_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ScatterCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// LIGHTNING HARP CROSSBOW
		ItemModelsProperties.register(ItemRegistry.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !ScatterCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ScatterCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// SLAYER CROSSBOW
		ItemModelsProperties.register(ItemRegistry.SLAYER_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.SLAYER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !HeavyCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.SLAYER_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && HeavyCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// THE SLICER
		ItemModelsProperties.register(ItemRegistry.THE_SLICER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.THE_SLICER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !DungeonsCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.THE_SLICER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && DungeonsCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// VOIDCALLER
		ItemModelsProperties.register(ItemRegistry.VOIDCALLER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.VOIDCALLER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !SoulCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.VOIDCALLER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && SoulCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// EXPLODING CROSSBOW
		ItemModelsProperties.register(ItemRegistry.EXPLODING_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.EXPLODING_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !ExplodingCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.EXPLODING_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ExplodingCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// HEAVY CROSSBOW
		ItemModelsProperties.register(ItemRegistry.HEAVY_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.HEAVY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !HeavyCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.HEAVY_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && HeavyCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// RAPID CROSSBOW
		ItemModelsProperties.register(ItemRegistry.RAPID_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.RAPID_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !RapidCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.RAPID_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && RapidCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// SCATTER CROSSBOW
		ItemModelsProperties.register(ItemRegistry.SCATTER_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.SCATTER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !ScatterCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.SCATTER_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ScatterCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// SOUL CROSSBOW
		ItemModelsProperties.register(ItemRegistry.SOUL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.SOUL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !SoulCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.SOUL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && SoulCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// IMPLODING CROSSBOW
		ItemModelsProperties.register(ItemRegistry.IMPLODING_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.IMPLODING_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !ExplodingCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.IMPLODING_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ExplodingCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// BABY CROSSBOW
		ItemModelsProperties.register(ItemRegistry.BABY_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.BABY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
							&& livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack
							&& !DualCrossbowItem.isCharged(itemStack))

							|| ((livingEntity != null
							&& livingEntity.isUsingItem()
									&& livingEntity.getUsedItemHand() == Hand.MAIN_HAND
							&& livingEntity.getUseItem().getItem() instanceof DualCrossbowItem
							&& livingEntity.getOffhandItem().getItem() instanceof DualCrossbowItem
							&& !DualCrossbowItem.isCharged(itemStack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.BABY_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// DUAL CROSSBOW
		ItemModelsProperties.register(ItemRegistry.DUAL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.DUAL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
									&& livingEntity.isUsingItem()
									&& livingEntity.getUseItem() == itemStack
									&& !DualCrossbowItem.isCharged(itemStack))

									|| ((livingEntity != null
									&& livingEntity.isUsingItem()
									&& livingEntity.getUsedItemHand() == Hand.MAIN_HAND
									&& livingEntity.getUseItem().getItem() instanceof DualCrossbowItem
									&& livingEntity.getOffhandItem().getItem() instanceof DualCrossbowItem
									&& !DualCrossbowItem.isCharged(itemStack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.DUAL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});


		// BURST CROSSBOW
		ItemModelsProperties.register(ItemRegistry.BURST_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.BURST_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !BurstCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.BURST_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && BurstCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// SOUL HUNTER CROSSBOW
		ItemModelsProperties.register(ItemRegistry.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !BurstCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && BurstCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// CORRUPTED CROSSBOW
		ItemModelsProperties.register(ItemRegistry.CORRUPTED_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.CORRUPTED_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !BurstCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.CORRUPTED_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && BurstCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// COG CROSSBOW
		ItemModelsProperties.register(ItemRegistry.COG_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return CogCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.COG_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !CogCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.COG_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && CogCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// PRIDE OF THE PIGLINS
		ItemModelsProperties.register(ItemRegistry.PRIDE_OF_THE_PIGLINS.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return CogCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.PRIDE_OF_THE_PIGLINS.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !CogCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.PRIDE_OF_THE_PIGLINS.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && CogCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});


		// HARPOON CROSSBOW
		ItemModelsProperties.register(ItemRegistry.HARPOON_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HarpoonCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.HARPOON_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !HarpoonCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.HARPOON_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && HarpoonCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// NAUTICAL CROSSBOW
		ItemModelsProperties.register(ItemRegistry.NAUTICAL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HarpoonCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.register(ItemRegistry.NAUTICAL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == itemStack && !HarpoonCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.register(ItemRegistry.NAUTICAL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && HarpoonCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

	}
}