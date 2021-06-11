package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.items.ranged.bows.*;
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
		ItemModelsProperties.registerProperty(ItemRegistry.ANCIENT_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.ANCIENT_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// BONEBOW
		ItemModelsProperties.registerProperty(ItemRegistry.BONEBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
										/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BONEBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
									? 1.0F
									: 0.0F;
				});

		// BOW OF LOST SOULS
		ItemModelsProperties.registerProperty(ItemRegistry.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// ELITE POWER BOW
		ItemModelsProperties.registerProperty(ItemRegistry.ELITE_POWER_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.ELITE_POWER_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// GUARDIAN BOW
		ItemModelsProperties.registerProperty(ItemRegistry.GUARDIAN_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.GUARDIAN_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});



		// HAUNTED BOW
		ItemModelsProperties.registerProperty(ItemRegistry.HAUNTED_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HAUNTED_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});


		// HUNTERS PROMISE
		ItemModelsProperties.registerProperty(ItemRegistry.HUNTERS_PROMISE.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HUNTERS_PROMISE.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// LOVE SPELL BOW
		ItemModelsProperties.registerProperty(ItemRegistry.LOVE_SPELL_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.LOVE_SPELL_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// MASTERS BOW
		ItemModelsProperties.registerProperty(ItemRegistry.MASTERS_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.MASTERS_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// MECHANICAL SHORTBOW
		ItemModelsProperties.registerProperty(ItemRegistry.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// NOCTURNAL BOW
		ItemModelsProperties.registerProperty(ItemRegistry.NOCTURNAL_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.NOCTURNAL_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// PURPLE STORM
		ItemModelsProperties.registerProperty(ItemRegistry.PURPLE_STORM.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.PURPLE_STORM.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// RED SNAKE
		ItemModelsProperties.registerProperty(ItemRegistry.RED_SNAKE.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.RED_SNAKE.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// SABREWING
		ItemModelsProperties.registerProperty(ItemRegistry.SABREWING.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SABREWING.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// THE GREEN MENACE
		ItemModelsProperties.registerProperty(ItemRegistry.THE_GREEN_MENACE.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.THE_GREEN_MENACE.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// THE PINK SCOUNDREL
		ItemModelsProperties.registerProperty(ItemRegistry.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// TWIN BOW
		ItemModelsProperties.registerProperty(ItemRegistry.TWIN_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.TWIN_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// HUNTING BOW
		ItemModelsProperties.registerProperty(ItemRegistry.HUNTING_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.HUNTING_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// LONGBOW
		ItemModelsProperties.registerProperty(ItemRegistry.LONGBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.LONGBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// SHORTBOW
		ItemModelsProperties.registerProperty(ItemRegistry.SHORTBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SHORTBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// POWER BOW
		ItemModelsProperties.registerProperty(ItemRegistry.POWER_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.POWER_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// SOUL BOW
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SOUL_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// TRICKBOW
		ItemModelsProperties.registerProperty(ItemRegistry.TRICKBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.TRICKBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// SUGAR RUSH
		ItemModelsProperties.registerProperty(ItemRegistry.SUGAR_RUSH.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SUGAR_RUSH.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// SNOW BOW
		ItemModelsProperties.registerProperty(ItemRegistry.SNOW_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.SNOW_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// WINTERS TOUCH
		ItemModelsProperties.registerProperty(ItemRegistry.WINTERS_TOUCH.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.WINTERS_TOUCH.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// WIND BOW
		ItemModelsProperties.registerProperty(ItemRegistry.WIND_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof WindBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.WIND_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// BURST GALE BOW
		ItemModelsProperties.registerProperty(ItemRegistry.BURST_GALE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof WindBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BURST_GALE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// ECHO OF THE VALLEY
		ItemModelsProperties.registerProperty(ItemRegistry.ECHO_OF_THE_VALLEY.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof WindBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.ECHO_OF_THE_VALLEY.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// TWISTING VINE BOW
		ItemModelsProperties.registerProperty(ItemRegistry.TWISTING_VINE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof TwistingVineBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.TWISTING_VINE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// WEEPING VINE BOW
		ItemModelsProperties.registerProperty(ItemRegistry.WEEPING_VINE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof TwistingVineBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.WEEPING_VINE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// BUBBLE BOW
		ItemModelsProperties.registerProperty(ItemRegistry.BUBBLE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof BubbleBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BUBBLE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});

		// BUBBLE BURSTER
		ItemModelsProperties.registerProperty(ItemRegistry.BUBBLE_BURSTER.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getActiveItemStack().getItem() instanceof BubbleBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getItemInUseCount())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
					}
				});
		ItemModelsProperties.registerProperty(ItemRegistry.BUBBLE_BURSTER.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isHandActive()
							&& livingEntity.getActiveItemStack() == stack
							? 1.0F
							: 0.0F;
				});
	}
}