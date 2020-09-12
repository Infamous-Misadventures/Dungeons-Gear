package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.ranged.bows.*;
import com.infamous.dungeons_gear.utilties.RangedUtils;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

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

		// BONEBOW
		ItemModelsProperties.func_239418_a_(BONEBOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
										/ RangedUtils.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(BONEBOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
									? 1.0F
									: 0.0F;
				});

		// BOW OF LOST SOULS
		ItemModelsProperties.func_239418_a_(BOW_OF_LOST_SOULS, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(BOW_OF_LOST_SOULS, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// ELITE POWER BOW
		ItemModelsProperties.func_239418_a_(ELITE_POWER_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(ELITE_POWER_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// GUARDIAN BOW
		ItemModelsProperties.func_239418_a_(GUARDIAN_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(GUARDIAN_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// HUNTERS PROMISE
		ItemModelsProperties.func_239418_a_(HUNTERS_PROMISE, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(HUNTERS_PROMISE, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MASTERS BOW
		ItemModelsProperties.func_239418_a_(MASTERS_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(MASTERS_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MECHANICAL SHORTBOW
		ItemModelsProperties.func_239418_a_(MECHANICAL_SHORTBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(MECHANICAL_SHORTBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// NOCTURNAL BOW
		ItemModelsProperties.func_239418_a_(NOCTURNAL_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(NOCTURNAL_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// PURPLE STORM
		ItemModelsProperties.func_239418_a_(PURPLE_STORM, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(PURPLE_STORM, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// RED SNAKE
		ItemModelsProperties.func_239418_a_(RED_SNAKE, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(RED_SNAKE, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SABREWING
		ItemModelsProperties.func_239418_a_(SABREWING, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(SABREWING, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE GREEN MENACE
		ItemModelsProperties.func_239418_a_(THE_GREEN_MENACE, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(THE_GREEN_MENACE, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE PINK SCOUNDREL
		ItemModelsProperties.func_239418_a_(THE_PINK_SCOUNDREL, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(THE_PINK_SCOUNDREL, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TWIN BOW
		ItemModelsProperties.func_239418_a_(TWIN_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(TWIN_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// HUNTING BOW
		ItemModelsProperties.func_239418_a_(HUNTING_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(HUNTING_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// LONGBOW
		ItemModelsProperties.func_239418_a_(LONGBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(LONGBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SHORTBOW
		ItemModelsProperties.func_239418_a_(SHORTBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(SHORTBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// POWER BOW
		ItemModelsProperties.func_239418_a_(POWER_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(POWER_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SOUL BOW
		ItemModelsProperties.func_239418_a_(SOUL_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(SOUL_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TRICKBOW
		ItemModelsProperties.func_239418_a_(TRICKBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(TRICKBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SNOW BOW
		ItemModelsProperties.func_239418_a_(SNOW_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(SNOW_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// WINTERS TOUCH
		ItemModelsProperties.func_239418_a_(WINTERS_TOUCH, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedUtils.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.func_239418_a_(WINTERS_TOUCH, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});
	}
}