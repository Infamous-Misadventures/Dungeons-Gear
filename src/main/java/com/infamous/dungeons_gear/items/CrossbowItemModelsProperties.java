package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.ranged.crossbows.*;
import com.infamous.dungeons_gear.utilties.RangedUtils;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;


import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class CrossbowItemModelsProperties {

	public CrossbowItemModelsProperties() {
		// CROSSBOW
		/*
		ItemModelsProperties.func_239418_a_(Items.CROSSBOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return CrossbowItem.isCharged(stack) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) DungeonsHooks.getCrossbowChargeTime(stack);
					}
				});
		ItemModelsProperties.func_239418_a_(Items.CROSSBOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack && !CrossbowItem.isCharged(stack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(Items.CROSSBOW, new ResourceLocation("charged"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
				});
		 */


		// AUTO CROSSBOW
		ItemModelsProperties.func_239418_a_(AUTO_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
										/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(AUTO_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !RapidCrossbowItem.isCharged(p_239426_0_)
									? 1.0F
									: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(AUTO_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && RapidCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// AZURE SEEKER
		ItemModelsProperties.func_239418_a_(AZURE_SEEKER, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(AZURE_SEEKER, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !DungeonsCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(AZURE_SEEKER, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && DungeonsCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// BUTTERFLY CROSSBOW
		ItemModelsProperties.func_239418_a_(BUTTERFLY_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(BUTTERFLY_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !RapidCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(BUTTERFLY_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && RapidCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// DOOM CROSSBOW
		ItemModelsProperties.func_239418_a_(DOOM_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(DOOM_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !HeavyCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(DOOM_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && HeavyCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// FERAL SOUL CROSSBOW
		ItemModelsProperties.func_239418_a_(FERAL_SOUL_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(FERAL_SOUL_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !SoulCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(FERAL_SOUL_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && SoulCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// FIREBOLT THROWER
		ItemModelsProperties.func_239418_a_(FIREBOLT_THROWER, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(FIREBOLT_THROWER, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ExplodingCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(FIREBOLT_THROWER, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ExplodingCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// HARP CROSSBOW
		ItemModelsProperties.func_239418_a_(HARP_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(HARP_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ScatterCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(HARP_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ScatterCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// LIGHTNING HARP CROSSBOW
		ItemModelsProperties.func_239418_a_(LIGHTNING_HARP_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(LIGHTNING_HARP_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ScatterCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(LIGHTNING_HARP_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ScatterCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// SLAYER CROSSBOW
		ItemModelsProperties.func_239418_a_(SLAYER_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(SLAYER_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !HeavyCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(SLAYER_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && HeavyCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// THE SLICER
		ItemModelsProperties.func_239418_a_(THE_SLICER, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(THE_SLICER, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !DungeonsCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(THE_SLICER, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && DungeonsCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// VOIDCALLER
		ItemModelsProperties.func_239418_a_(VOIDCALLER, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(VOIDCALLER, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !SoulCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(VOIDCALLER, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && SoulCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// EXPLODING CROSSBOW
		ItemModelsProperties.func_239418_a_(EXPLODING_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(EXPLODING_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ExplodingCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(EXPLODING_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ExplodingCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// HEAVY CROSSBOW
		ItemModelsProperties.func_239418_a_(HEAVY_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(HEAVY_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !HeavyCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(HEAVY_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && HeavyCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// RAPID CROSSBOW
		ItemModelsProperties.func_239418_a_(RAPID_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(RAPID_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !RapidCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(RAPID_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && RapidCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// SCATTER CROSSBOW
		ItemModelsProperties.func_239418_a_(SCATTER_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(SCATTER_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ScatterCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(SCATTER_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ScatterCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// SOUL CROSSBOW
		ItemModelsProperties.func_239418_a_(SOUL_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(SOUL_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !SoulCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(SOUL_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && SoulCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// IMPLODING CROSSBOW
		ItemModelsProperties.func_239418_a_(IMPLODING_CROSSBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.func_239418_a_(IMPLODING_CROSSBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ExplodingCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(IMPLODING_CROSSBOW, new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ExplodingCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// BABY CROSSBOW
		ItemModelsProperties.func_239418_a_(BABY_CROSSBOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(stack) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(stack);
					}
				});
		ItemModelsProperties.func_239418_a_(BABY_CROSSBOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
							&& livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							&& !DualCrossbowItem.isCharged(stack))

							|| ((livingEntity != null
							&& livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack().getItem() instanceof DualCrossbowItem
							&& livingEntity.getHeldItemOffhand().getItem() instanceof DualCrossbowItem
							&& !DualCrossbowItem.isCharged(stack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(BABY_CROSSBOW, new ResourceLocation("charged"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
				});

		// DUAL CROSSBOW
		ItemModelsProperties.func_239418_a_(DUAL_CROSSBOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(stack) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedUtils.getModdedCrossbowChargeTime(stack);
					}
				});
		ItemModelsProperties.func_239418_a_(DUAL_CROSSBOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
									&& livingEntity.isHandActive()
									&& livingEntity.getActiveItemStack() == stack
									&& !DualCrossbowItem.isCharged(stack))

									|| ((livingEntity != null
									&& livingEntity.isHandActive()
									&& livingEntity.getActiveItemStack().getItem() instanceof DualCrossbowItem
									&& livingEntity.getHeldItemOffhand().getItem() instanceof DualCrossbowItem
									&& !DualCrossbowItem.isCharged(stack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.func_239418_a_(DUAL_CROSSBOW, new ResourceLocation("charged"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
				});
	}
}