package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.ranged.bows.*;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;


public class BowItemModelsProperties {

	public BowItemModelsProperties() {
		// BOW
		/*
		ItemModelsProperties.registerProperty(Items.BOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ DungeonsHooks.getBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(Items.BOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		 */

		// ANCIENT BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.ANCIENT_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.ANCIENT_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// BONEBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.BONEBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
										/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BONEBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
									? 1.0F
									: 0.0F;
				});

		// BOW OF LOST SOULS
		ItemModelsProperties.registerProperty(DeferredItemInit.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// ELITE POWER BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.ELITE_POWER_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.ELITE_POWER_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// GUARDIAN BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.GUARDIAN_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.GUARDIAN_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});



		// HAUNTED BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.HAUNTED_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.HAUNTED_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});


		// HUNTERS PROMISE
		ItemModelsProperties.registerProperty(DeferredItemInit.HUNTERS_PROMISE.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.HUNTERS_PROMISE.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// LOVE SPELL BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.LOVE_SPELL_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.LOVE_SPELL_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MASTERS BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.MASTERS_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.MASTERS_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MECHANICAL SHORTBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// NOCTURNAL BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.NOCTURNAL_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.NOCTURNAL_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// PURPLE STORM
		ItemModelsProperties.registerProperty(DeferredItemInit.PURPLE_STORM.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.PURPLE_STORM.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// RED SNAKE
		ItemModelsProperties.registerProperty(DeferredItemInit.RED_SNAKE.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.RED_SNAKE.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SABREWING
		ItemModelsProperties.registerProperty(DeferredItemInit.SABREWING.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SABREWING.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE GREEN MENACE
		ItemModelsProperties.registerProperty(DeferredItemInit.THE_GREEN_MENACE.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.THE_GREEN_MENACE.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE PINK SCOUNDREL
		ItemModelsProperties.registerProperty(DeferredItemInit.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TWIN BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.TWIN_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.TWIN_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// HUNTING BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.HUNTING_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.HUNTING_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// LONGBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.LONGBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.LONGBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SHORTBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.SHORTBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SHORTBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// POWER BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.POWER_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.POWER_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SOUL BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SOUL_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TRICKBOW
		ItemModelsProperties.registerProperty(DeferredItemInit.TRICKBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.TRICKBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SNOW BOW
		ItemModelsProperties.registerProperty(DeferredItemInit.SNOW_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.SNOW_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// WINTERS TOUCH
		ItemModelsProperties.registerProperty(DeferredItemInit.WINTERS_TOUCH.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(DeferredItemInit.WINTERS_TOUCH.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});
	}
}