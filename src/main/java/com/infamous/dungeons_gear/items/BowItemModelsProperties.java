package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.registry.ItemRegistry;
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
		ItemModelsProperties.register(ItemRegistry.ANCIENT_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.ANCIENT_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// BONEBOW
		ItemModelsProperties.register(ItemRegistry.BONEBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
										/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.BONEBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
									? 1.0F
									: 0.0F;
				});

		// BOW OF LOST SOULS
		ItemModelsProperties.register(ItemRegistry.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.BOW_OF_LOST_SOULS.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// ELITE POWER BOW
		ItemModelsProperties.register(ItemRegistry.ELITE_POWER_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.ELITE_POWER_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// GUARDIAN BOW
		ItemModelsProperties.register(ItemRegistry.GUARDIAN_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.GUARDIAN_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});



		// HAUNTED BOW
		ItemModelsProperties.register(ItemRegistry.HAUNTED_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.HAUNTED_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});


		// HUNTERS PROMISE
		ItemModelsProperties.register(ItemRegistry.HUNTERS_PROMISE.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.HUNTERS_PROMISE.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// LOVE SPELL BOW
		ItemModelsProperties.register(ItemRegistry.LOVE_SPELL_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.LOVE_SPELL_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// MASTERS BOW
		ItemModelsProperties.register(ItemRegistry.MASTERS_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.MASTERS_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// MECHANICAL SHORTBOW
		ItemModelsProperties.register(ItemRegistry.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.MECHANICAL_SHORTBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// NOCTURNAL BOW
		ItemModelsProperties.register(ItemRegistry.NOCTURNAL_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.NOCTURNAL_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// PURPLE STORM
		ItemModelsProperties.register(ItemRegistry.PURPLE_STORM.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.PURPLE_STORM.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// RED SNAKE
		ItemModelsProperties.register(ItemRegistry.RED_SNAKE.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.RED_SNAKE.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// SABREWING
		ItemModelsProperties.register(ItemRegistry.SABREWING.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.SABREWING.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// THE GREEN MENACE
		ItemModelsProperties.register(ItemRegistry.THE_GREEN_MENACE.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.THE_GREEN_MENACE.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// THE PINK SCOUNDREL
		ItemModelsProperties.register(ItemRegistry.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.THE_PINK_SCOUNDREL.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// TWIN BOW
		ItemModelsProperties.register(ItemRegistry.TWIN_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof DungeonsBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.TWIN_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// HUNTING BOW
		ItemModelsProperties.register(ItemRegistry.HUNTING_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof HuntingBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.HUNTING_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// LONGBOW
		ItemModelsProperties.register(ItemRegistry.LONGBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof LongbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.LONGBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// SHORTBOW
		ItemModelsProperties.register(ItemRegistry.SHORTBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof ShortbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.SHORTBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// POWER BOW
		ItemModelsProperties.register(ItemRegistry.POWER_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof PowerBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.POWER_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// SOUL BOW
		ItemModelsProperties.register(ItemRegistry.SOUL_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof SoulBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.SOUL_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// TRICKBOW
		ItemModelsProperties.register(ItemRegistry.TRICKBOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.TRICKBOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// SUGAR RUSH
		ItemModelsProperties.register(ItemRegistry.SUGAR_RUSH.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof TrickbowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.SUGAR_RUSH.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// SNOW BOW
		ItemModelsProperties.register(ItemRegistry.SNOW_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.SNOW_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// WINTERS TOUCH
		ItemModelsProperties.register(ItemRegistry.WINTERS_TOUCH.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof SnowBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.WINTERS_TOUCH.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// WIND BOW
		ItemModelsProperties.register(ItemRegistry.WIND_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof WindBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.WIND_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// BURST GALE BOW
		ItemModelsProperties.register(ItemRegistry.BURST_GALE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof WindBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.BURST_GALE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// ECHO OF THE VALLEY
		ItemModelsProperties.register(ItemRegistry.ECHO_OF_THE_VALLEY.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof WindBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.ECHO_OF_THE_VALLEY.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// TWISTING VINE BOW
		ItemModelsProperties.register(ItemRegistry.TWISTING_VINE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof TwistingVineBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.TWISTING_VINE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// WEEPING VINE BOW
		ItemModelsProperties.register(ItemRegistry.WEEPING_VINE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof TwistingVineBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.WEEPING_VINE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// BUBBLE BOW
		ItemModelsProperties.register(ItemRegistry.BUBBLE_BOW.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof BubbleBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.BUBBLE_BOW.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});

		// BUBBLE BURSTER
		ItemModelsProperties.register(ItemRegistry.BUBBLE_BURSTER.get(), new ResourceLocation("pull"),
				(stack, clientWorld, livingEntity) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return !(livingEntity.getUseItem().getItem() instanceof BubbleBowItem) ? 0.0F
								: (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks())
								/ RangedAttackHelper.getModdedBowChargeTime(livingEntity, livingEntity.getUseItem());
					}
				});
		ItemModelsProperties.register(ItemRegistry.BUBBLE_BURSTER.get(), new ResourceLocation("pulling"),
				(stack, clientWorld, livingEntity) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack
							? 1.0F
							: 0.0F;
				});
	}
}