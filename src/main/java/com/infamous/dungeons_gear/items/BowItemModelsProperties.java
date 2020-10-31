package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.ranged.bows.*;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

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
		ItemModelsProperties.registerProperty(ANCIENT_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ANCIENT_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// BONEBOW
		ItemModelsProperties.registerProperty(BONEBOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
										/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(BONEBOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
									? 1.0F
									: 0.0F;
				});

		// BOW OF LOST SOULS
		ItemModelsProperties.registerProperty(BOW_OF_LOST_SOULS, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(BOW_OF_LOST_SOULS, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// ELITE POWER BOW
		ItemModelsProperties.registerProperty(ELITE_POWER_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ELITE_POWER_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// GUARDIAN BOW
		ItemModelsProperties.registerProperty(GUARDIAN_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(GUARDIAN_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});



		// HAUNTED BOW
		ItemModelsProperties.registerProperty(HAUNTED_BOW, new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(HAUNTED_BOW, new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});


		// HUNTERS PROMISE
		ItemModelsProperties.registerProperty(HUNTERS_PROMISE, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(HUNTERS_PROMISE, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// LOVE SPELL BOW
		ItemModelsProperties.registerProperty(LOVE_SPELL_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(LOVE_SPELL_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MASTERS BOW
		ItemModelsProperties.registerProperty(MASTERS_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(MASTERS_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// MECHANICAL SHORTBOW
		ItemModelsProperties.registerProperty(MECHANICAL_SHORTBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(MECHANICAL_SHORTBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// NOCTURNAL BOW
		ItemModelsProperties.registerProperty(NOCTURNAL_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(NOCTURNAL_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// PURPLE STORM
		ItemModelsProperties.registerProperty(PURPLE_STORM, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(PURPLE_STORM, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// RED SNAKE
		ItemModelsProperties.registerProperty(RED_SNAKE, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(RED_SNAKE, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SABREWING
		ItemModelsProperties.registerProperty(SABREWING, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(SABREWING, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE GREEN MENACE
		ItemModelsProperties.registerProperty(THE_GREEN_MENACE, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(THE_GREEN_MENACE, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// THE PINK SCOUNDREL
		ItemModelsProperties.registerProperty(THE_PINK_SCOUNDREL, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(THE_PINK_SCOUNDREL, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TWIN BOW
		ItemModelsProperties.registerProperty(TWIN_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(TWIN_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// HUNTING BOW
		ItemModelsProperties.registerProperty(HUNTING_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(HUNTING_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// LONGBOW
		ItemModelsProperties.registerProperty(LONGBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(LONGBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SHORTBOW
		ItemModelsProperties.registerProperty(SHORTBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(SHORTBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// POWER BOW
		ItemModelsProperties.registerProperty(POWER_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(POWER_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SOUL BOW
		ItemModelsProperties.registerProperty(SOUL_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(SOUL_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// TRICKBOW
		ItemModelsProperties.registerProperty(TRICKBOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(TRICKBOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// SNOW BOW
		ItemModelsProperties.registerProperty(SNOW_BOW, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(SNOW_BOW, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});

		// WINTERS TOUCH
		ItemModelsProperties.registerProperty(WINTERS_TOUCH, new ResourceLocation("pull"),
				(p_239427_0_, p_239427_1_, p_239427_2_) -> {
					if (p_239427_2_ == null) {
						return 0.0F;
					} else {
						return !(p_239427_2_.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(p_239427_2_.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(WINTERS_TOUCH, new ResourceLocation("pulling"),
				(p_239426_0_, p_239426_1_, p_239426_2_) -> {
					return p_239426_2_ != null && p_239426_2_.isHandActive()
							&& p_239426_2_.getActiveItemStack() == p_239426_0_
							? 1.0F
							: 0.0F;
				});
	}
}