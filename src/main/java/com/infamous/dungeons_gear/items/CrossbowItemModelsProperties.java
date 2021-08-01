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
		ItemModelsProperties.registerProperty(ItemRegistry.AUTO_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
										/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.AUTO_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !RapidCrossbowItem.isCharged(itemStack)
									? 1.0F
									: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.AUTO_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && RapidCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// AZURE SEEKER
		ItemModelsProperties.registerProperty(ItemRegistry.AZURE_SEEKER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.AZURE_SEEKER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !DungeonsCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.AZURE_SEEKER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && DungeonsCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// BUTTERFLY CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !RapidCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && RapidCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// DOOM CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.DOOM_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.DOOM_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !HeavyCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.DOOM_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && HeavyCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// FERAL SOUL CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !SoulCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && SoulCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// FIREBOLT THROWER
		ItemModelsProperties.registerProperty(ItemRegistry.FIREBOLT_THROWER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.FIREBOLT_THROWER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !ExplodingCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.FIREBOLT_THROWER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ExplodingCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// HARP CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.HARP_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HARP_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !ScatterCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HARP_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ScatterCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// LIGHTNING HARP CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !ScatterCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ScatterCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// SLAYER CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.SLAYER_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SLAYER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !HeavyCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SLAYER_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && HeavyCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// THE SLICER
		ItemModelsProperties.registerProperty(ItemRegistry.THE_SLICER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.THE_SLICER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !DungeonsCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.THE_SLICER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && DungeonsCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// VOIDCALLER
		ItemModelsProperties.registerProperty(ItemRegistry.VOIDCALLER.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.VOIDCALLER.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !SoulCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.VOIDCALLER.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && SoulCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// EXPLODING CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.EXPLODING_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.EXPLODING_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !ExplodingCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.EXPLODING_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ExplodingCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// HEAVY CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.HEAVY_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HEAVY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !HeavyCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HEAVY_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && HeavyCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// RAPID CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.RAPID_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.RAPID_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !RapidCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.RAPID_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && RapidCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// SCATTER CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.SCATTER_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SCATTER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !ScatterCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SCATTER_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ScatterCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// SOUL CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !SoulCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && SoulCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// IMPLODING CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.IMPLODING_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, p_239427_1_, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.IMPLODING_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, p_239426_1_, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !ExplodingCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.IMPLODING_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, p_239425_1_, livingEntity) -> {
					return livingEntity != null && ExplodingCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// BABY CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.BABY_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BABY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
							&& livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack
							&& !DualCrossbowItem.isCharged(itemStack))

							|| ((livingEntity != null
							&& livingEntity.isHandActive()
									&& livingEntity.getActiveHand() == Hand.MAIN_HAND
							&& livingEntity.getActiveItemStack().getItem() instanceof DualCrossbowItem
							&& livingEntity.getHeldItemOffhand().getItem() instanceof DualCrossbowItem
							&& !DualCrossbowItem.isCharged(itemStack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BABY_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// DUAL CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.DUAL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.DUAL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
									&& livingEntity.isHandActive()
									&& livingEntity.getActiveItemStack() == itemStack
									&& !DualCrossbowItem.isCharged(itemStack))

									|| ((livingEntity != null
									&& livingEntity.isHandActive()
									&& livingEntity.getActiveHand() == Hand.MAIN_HAND
									&& livingEntity.getActiveItemStack().getItem() instanceof DualCrossbowItem
									&& livingEntity.getHeldItemOffhand().getItem() instanceof DualCrossbowItem
									&& !DualCrossbowItem.isCharged(itemStack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.DUAL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});


		// BURST CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.BURST_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BURST_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !BurstCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BURST_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && BurstCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// SOUL HUNTER CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !BurstCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && BurstCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// CORRUPTED CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.CORRUPTED_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.CORRUPTED_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !BurstCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.CORRUPTED_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && BurstCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

		// COG CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.COG_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return CogCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.COG_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !CogCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.COG_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && CogCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// PRIDE OF THE PIGLINS
		ItemModelsProperties.registerProperty(ItemRegistry.PRIDE_OF_THE_PIGLINS.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return CogCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.PRIDE_OF_THE_PIGLINS.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !CogCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.PRIDE_OF_THE_PIGLINS.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && CogCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});


		// HARPOON CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.HARPOON_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HarpoonCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HARPOON_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !HarpoonCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HARPOON_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && HarpoonCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});
		// NAUTICAL CROSSBOW
		ItemModelsProperties.registerProperty(ItemRegistry.NAUTICAL_CROSSBOW.get(), new ResourceLocation("pull"),
				(itemStack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return HarpoonCrossbowItem.isCharged(itemStack) ? 0.0F
								: (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(livingEntity, itemStack);
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.NAUTICAL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == itemStack && !HarpoonCrossbowItem.isCharged(itemStack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(ItemRegistry.NAUTICAL_CROSSBOW.get(), new ResourceLocation("charged"),
				(itemStack, clientWorld, livingEntity) -> {
					return livingEntity != null && HarpoonCrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
				});

	}
}