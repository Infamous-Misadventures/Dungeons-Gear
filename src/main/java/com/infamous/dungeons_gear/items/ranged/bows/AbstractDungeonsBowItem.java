package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.capabilities.bow.IBow;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import net.minecraft.item.Item.Properties;

public abstract class AbstractDungeonsBowItem extends BowItem implements IRangedWeapon {
    private float defaultChargeTime;
    private boolean isUnique;

    public AbstractDungeonsBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder
                .durability(DungeonsGearConfig.BOW_DURABILITY.get())
        );
        this.defaultChargeTime = defaultChargeTimeIn;
        this.isUnique = isUniqueIn;
    }

    public float getDefaultChargeTime(){
        return this.defaultChargeTime;
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)livingEntity;
            boolean useInfiniteAmmo = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack itemstack = playerentity.getProjectile(stack);
            int charge = this.getUseDuration(stack) - timeLeft;
            charge = ForgeEventFactory.onArrowLoose(stack, world, playerentity, charge, !itemstack.isEmpty() || useInfiniteAmmo);
            if (charge < 0) {
                return;
            }

            if (!itemstack.isEmpty() || useInfiniteAmmo) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float arrowVelocity = this.getBowArrowVelocity(stack, charge);
                this.fireArrows(stack, world, playerentity, itemstack, arrowVelocity);
            }
        }
    }

    public void fireArrows(ItemStack stack, World world, PlayerEntity playerentity, ItemStack itemstack, float arrowVelocity) {
        int multishotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
        int arrowsToFire = 1;
        if(multishotLevel > 0) arrowsToFire += 2;
        if(this.hasMultishotBuiltIn(stack)) arrowsToFire += 2;
        if(this.hasMultishotWhenCharged(stack) && arrowVelocity == 1.0F) arrowsToFire += 2;

        for(int arrowNumber = 0; arrowNumber < arrowsToFire; arrowNumber++){
            if ((double)arrowVelocity >= 0.1D) {
                boolean hasInfiniteAmmo = playerentity.abilities.instabuild || itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity);
                boolean isAdditionalShot = arrowNumber > 0;
                if (!world.isClientSide) {
                    this.createBowArrow(stack, world, playerentity, itemstack, arrowVelocity, arrowNumber, hasInfiniteAmmo, isAdditionalShot);
                }

                world.playSound((PlayerEntity)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);
                if (!hasInfiniteAmmo && !playerentity.abilities.instabuild && !isAdditionalShot) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        playerentity.inventory.removeItem(itemstack);
                    }
                }

                playerentity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public AbstractArrowEntity createBowArrow(ItemStack stack, World world, PlayerEntity playerentity, ItemStack itemstack, float arrowVelocity, int i, boolean hasInfiniteAmmo, boolean isAdditionalShot) {
        ArrowItem arrowitem = (ArrowItem)((ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW));
        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(world, itemstack, playerentity);
        abstractarrowentity = this.customArrow(abstractarrowentity);
        this.setArrowTrajectory(playerentity, arrowVelocity, i, abstractarrowentity);
        if (arrowVelocity == 1.0F) {
            abstractarrowentity.setCritArrow(true);
        }
        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);

        // Damage Boosters
        if(this.hasPowerBuiltIn(stack)) powerLevel++;
        if(this.shootsStrongChargedArrows(stack) && abstractarrowentity.isCritArrow()) powerLevel++;
        if(this.hasSuperChargedBuiltIn(stack) && abstractarrowentity.isCritArrow()) powerLevel++;
        if(this.shootsHeavyArrows(stack)) powerLevel++;

        if (powerLevel > 0) {
            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)powerLevel * 0.5D + 0.5D);
        }
        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
        if(this.hasPunchBuiltIn(stack)) punchLevel++;
        if(this.hasSuperChargedBuiltIn(stack)) punchLevel++;
        if (punchLevel > 0) {
            abstractarrowentity.setKnockback(punchLevel);
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
            abstractarrowentity.setSecondsOnFire(100);
        }
        stack.hurtAndBreak(1, playerentity, (p_lambda$onPlayerStoppedUsing$0_1_) -> {
            p_lambda$onPlayerStoppedUsing$0_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
        });
        if (hasInfiniteAmmo || playerentity.abilities.instabuild
                && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }
        if(isAdditionalShot){
            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }
        RangedAttackHelper.addWeaponTags(abstractarrowentity, stack);
        world.addFreshEntity(abstractarrowentity);
        return abstractarrowentity;
    }

    public void setArrowTrajectory(PlayerEntity playerentity, float arrowVelocity, int i, AbstractArrowEntity abstractarrowentity) {
        if(i == 0) abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, arrowVelocity * 3.0F, 1.0F);
        if(i == 1) abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 10.0F, 0.0F, arrowVelocity * 3.0F, 1.0F);
        if(i == 2) abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot - 10.0F, 0.0F, arrowVelocity * 3.0F, 1.0F);
        if(i == 3) abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 20.0F, 0.0F, arrowVelocity * 3.0F, 1.0F);
        if(i == 4) abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot - 20.0F, 0.0F, arrowVelocity * 3.0F, 1.0F);
        if(i == 5) abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 30.0F, 0.0F, arrowVelocity * 3.0F, 1.0F);
        if(i == 6) abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot - 30.0F, 0.0F, arrowVelocity * 3.0F, 1.0F);
    }

    public float getBowArrowVelocity(ItemStack stack, int charge) {
        float bowChargeTime = getBowChargeTime(stack);
        float arrowVelocity = (float)charge / bowChargeTime;
        arrowVelocity = (arrowVelocity * arrowVelocity + arrowVelocity * 2.0F) / 3.0F;
        float velocityLimit = 1.0F;
        int overchargeLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.OVERCHARGE, stack);
        if(overchargeLevel > 0){
            velocityLimit += overchargeLevel;
        }
        if (arrowVelocity > velocityLimit) {
            arrowVelocity = velocityLimit;
        }

        return arrowVelocity;
    }

    public float getBowChargeTime(ItemStack stack){
        int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        if(this.hasQuickChargeBuiltIn(stack)) quickChargeLevel++;
        int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);
        if(this.hasAccelerateBuiltIn(stack)) accelerateLevel++;

        IBow weaponCap = CapabilityHelper.getWeaponCapability(stack);
        int minTime = 1;
        if(weaponCap == null) return Math.max(this.getDefaultChargeTime() - 5 * quickChargeLevel, minTime);
        float bowChargeTime = weaponCap.getBowChargeTime();
        long lastFiredTime = weaponCap.getLastFiredTime();

        if(accelerateLevel > 0 && lastFiredTime > 0){
            return Math.max(bowChargeTime - 5 * quickChargeLevel, minTime);
        }
        else {
            return Math.max(this.getDefaultChargeTime() - 5 * quickChargeLevel, minTime);
        }
    }

    public Rarity getRarity(ItemStack itemStack){

        if(this.isUnique){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }
}
