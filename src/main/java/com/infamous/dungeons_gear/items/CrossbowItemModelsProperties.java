package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.ranged.crossbows.*;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;


import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class CrossbowItemModelsProperties {

	public CrossbowItemModelsProperties() {
		// CROSSBOW
		/*
		ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return CrossbowItem.isCharged(stack) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) DungeonsHooks.getCrossbowChargeTime(stack);
					}
				});
		ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack && !CrossbowItem.isCharged(stack)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(Items.CROSSBOW, new ResourceLocation("charged"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
				});
		 */


		// AUTO CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.AUTO_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
										/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.AUTO_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !RapidCrossbowItem.isCharged(p_239426_0_)
									? 1.0F
									: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.AUTO_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && RapidCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// AZURE SEEKER
		ItemModelsProperties.registerProperty(DeferredItemInit.AZURE_SEEKER.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.AZURE_SEEKER.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !DungeonsCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.AZURE_SEEKER.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && DungeonsCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// BUTTERFLY CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !RapidCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BUTTERFLY_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && RapidCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// DOOM CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.DOOM_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.DOOM_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !HeavyCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.DOOM_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && HeavyCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// FERAL SOUL CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !SoulCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.FERAL_SOUL_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && SoulCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// FIREBOLT THROWER
		ItemModelsProperties.registerProperty(DeferredItemInit.FIREBOLT_THROWER.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.FIREBOLT_THROWER.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ExplodingCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.FIREBOLT_THROWER.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ExplodingCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// HARP CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.HARP_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.HARP_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ScatterCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.HARP_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ScatterCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// LIGHTNING HARP CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ScatterCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.LIGHTNING_HARP_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ScatterCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// SLAYER CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.SLAYER_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SLAYER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !HeavyCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SLAYER_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && HeavyCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// THE SLICER
		ItemModelsProperties.registerProperty(DeferredItemInit.THE_SLICER.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return DungeonsCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.THE_SLICER.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !DungeonsCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.THE_SLICER.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && DungeonsCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// VOIDCALLER
		ItemModelsProperties.registerProperty(DeferredItemInit.VOIDCALLER.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.VOIDCALLER.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !SoulCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.VOIDCALLER.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && SoulCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// EXPLODING CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.EXPLODING_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.EXPLODING_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ExplodingCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.EXPLODING_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ExplodingCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// HEAVY CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.HEAVY_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return HeavyCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.HEAVY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !HeavyCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.HEAVY_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && HeavyCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// RAPID CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.RAPID_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return RapidCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.RAPID_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !RapidCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.RAPID_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && RapidCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// SCATTER CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.SCATTER_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ScatterCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SCATTER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ScatterCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SCATTER_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ScatterCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// SOUL CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return SoulCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !SoulCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && SoulCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// IMPLODING CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.IMPLODING_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return ExplodingCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.IMPLODING_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !ExplodingCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.IMPLODING_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && ExplodingCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// BABY CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.BABY_CROSSBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(stack) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(stack);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BABY_CROSSBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
							&& livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							&& !DualCrossbowItem.isCharged(stack))

							|| ((livingEntity != null
							&& livingEntity.isHandActive()
									&& livingEntity.getActiveHand() == Hand.MAIN_HAND
							&& livingEntity.getActiveItemStack().getItem() instanceof DualCrossbowItem
							&& livingEntity.getHeldItemOffhand().getItem() instanceof DualCrossbowItem
							&& !DualCrossbowItem.isCharged(stack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BABY_CROSSBOW.get(), new ResourceLocation("charged"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
				});

		// DUAL CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.DUAL_CROSSBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return DualCrossbowItem.isCharged(stack) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(stack);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.DUAL_CROSSBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return (
							(livingEntity != null
									&& livingEntity.isHandActive()
									&& livingEntity.getActiveItemStack() == stack
									&& !DualCrossbowItem.isCharged(stack))

									|| ((livingEntity != null
									&& livingEntity.isHandActive()
									&& livingEntity.getActiveHand() == Hand.MAIN_HAND
									&& livingEntity.getActiveItemStack().getItem() instanceof DualCrossbowItem
									&& livingEntity.getHeldItemOffhand().getItem() instanceof DualCrossbowItem
									&& !DualCrossbowItem.isCharged(stack))))

							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.DUAL_CROSSBOW.get(), new ResourceLocation("charged"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && DualCrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
				});

		/*
		// BURST CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.BURST_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BURST_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !BurstCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BURST_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && BurstCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		// SOUL HUNTER CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !BurstCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_HUNTER_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && BurstCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});
		// CORRUPTED CROSSBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.CORRUPTED_CROSSBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return BurstCrossbowItem.isCharged(p_239427_0_) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ (float) RangedAttackHelper.getModdedCrossbowChargeTime(p_239427_0_);
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.CORRUPTED_CROSSBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_ && !BurstCrossbowItem.isCharged(p_239426_0_)
							? 1.0F
							: 0.0F;
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.CORRUPTED_CROSSBOW.get(), new ResourceLocation("charged"),
				(p_239425_0_, p_239425_1_, p_239425_2_) -> {
					return p_239425_2_ != null && BurstCrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
				});

		 */
	}
}