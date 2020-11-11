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
		ItemModelsProperties.func_239418_a_(Items.BOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ DungeonsHooks.getBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(Items.BOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		 */

		// ANCIENT BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.ANCIENT_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.ANCIENT_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// BONEBOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.BONEBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
										/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.BONEBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
									? 1.0F
									: 0.0F;
				});

		// BOW OF LOST SOULS
		ItemModelsProperties.func_239418_a_(DeferredItemInit.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// ELITE POWER BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.ELITE_POWER_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.ELITE_POWER_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// GUARDIAN BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.GUARDIAN_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.GUARDIAN_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});



		// HAUNTED BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.HAUNTED_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.HAUNTED_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});


		// HUNTERS PROMISE
		ItemModelsProperties.func_239418_a_(DeferredItemInit.HUNTERS_PROMISE.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.HUNTERS_PROMISE.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// LOVE SPELL BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.LOVE_SPELL_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.LOVE_SPELL_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MASTERS BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.MASTERS_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.MASTERS_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MECHANICAL SHORTBOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// NOCTURNAL BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.NOCTURNAL_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.NOCTURNAL_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// PURPLE STORM
		ItemModelsProperties.func_239418_a_(DeferredItemInit.PURPLE_STORM.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.PURPLE_STORM.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// RED SNAKE
		ItemModelsProperties.func_239418_a_(DeferredItemInit.RED_SNAKE.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.RED_SNAKE.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SABREWING
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SABREWING.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SABREWING.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE GREEN MENACE
		ItemModelsProperties.func_239418_a_(DeferredItemInit.THE_GREEN_MENACE.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.THE_GREEN_MENACE.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE PINK SCOUNDREL
		ItemModelsProperties.func_239418_a_(DeferredItemInit.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TWIN BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.TWIN_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.TWIN_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// HUNTING BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.HUNTING_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.HUNTING_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// LONGBOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.LONGBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.LONGBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SHORTBOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SHORTBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SHORTBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// POWER BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.POWER_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.POWER_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SOUL BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SOUL_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SOUL_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TRICKBOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.TRICKBOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.TRICKBOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SNOW BOW
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SNOW_BOW.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.SNOW_BOW.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// WINTERS TOUCH
		ItemModelsProperties.func_239418_a_(DeferredItemInit.WINTERS_TOUCH.get(), new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(DeferredItemInit.WINTERS_TOUCH.get(), new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});
	}
}