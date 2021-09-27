package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.capabilities.bow.IBow;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.minecraft.item.Item.Properties;

public abstract class AbstractDungeonsCrossbowItem extends CrossbowItem implements IRangedWeapon {
    public boolean isLoadingStart = false;
    public boolean isLoadingMiddle = false;
    private int defaultChargeTime;
    private boolean isUnique;

    public AbstractDungeonsCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder
                .durability(DungeonsGearConfig.CROSSBOW_DURABILITY.get())
        );
        this.defaultChargeTime = defaultChargeTimeIn;
        this.isUnique = isUniqueIn;
    }

    public static float getArrowVelocity(ItemStack stack) {
        boolean fastProjectiles = ((IRangedWeapon) stack.getItem()).shootsFasterArrows(stack);
        boolean firesHarpoons = ((IRangedWeapon) stack.getItem()).firesHarpoons(stack);
        float additionalVelocity = 0.0F;
        if(fastProjectiles) additionalVelocity += 1.6F;
        if(firesHarpoons) additionalVelocity += 1.6F;
        if (containsChargedProjectile(stack, Items.FIREWORK_ROCKET)) {
            return 1.6F + additionalVelocity;
        } else {
            return 3.15F + additionalVelocity;
        }
    }

    public static float[] getRandomSoundPitches(Random rand) {
        boolean flag = rand.nextBoolean();
        return new float[]{1.0F, getRandomSoundPitch(flag), getRandomSoundPitch(!flag)};
    }

    private static float getRandomSoundPitch(boolean flagIn) {
        float f = flagIn ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static boolean canAddChargedProjectile(LivingEntity livingEntity, ItemStack stack, ItemStack stack1, boolean b, boolean b1) {
        if (stack1.isEmpty()) {
            return false;
        } else {
            boolean flag = b1 && stack1.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !b1 && !b) {
                itemstack = stack1.split(1);
                if (stack1.isEmpty() && livingEntity instanceof PlayerEntity) {
                    ((PlayerEntity) livingEntity).inventory.removeItem(stack1);
                }
            } else {
                itemstack = stack1.copy();
            }

            addChargedProjectile(stack, itemstack);
            return true;
        }
    }

    public int getDefaultChargeTime() {
        return this.defaultChargeTime;
    }

    @Override
    public void onUseTick(World world, LivingEntity livingEntity, ItemStack stack, int timeLeft) {
        if (!world.isClientSide) {
            int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);

            SoundEvent quickChargeSoundEvent = this.getCrossbowSoundEvent(quickChargeLevel);
            SoundEvent loadingMiddleSoundEvent = quickChargeLevel == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float chargeTime = (float) (stack.getUseDuration() - timeLeft) / (float) this.getCrossbowChargeTime(stack);
            if (chargeTime < 0.2F) {
                this.isLoadingStart = false;
                this.isLoadingMiddle = false;
            }

            if (chargeTime >= 0.2F && !this.isLoadingStart && chargeTime < 1.0F) {
                this.isLoadingStart = true;
                world.playSound((PlayerEntity) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), quickChargeSoundEvent, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }

            if (chargeTime >= 0.5F && loadingMiddleSoundEvent != null && !this.isLoadingMiddle && chargeTime < 1.0F) {
                this.isLoadingMiddle = true;
                world.playSound((PlayerEntity) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), loadingMiddleSoundEvent, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }
        }

    }

    @Override
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = this.getCrossbowCharge(i, stack);
        if (f >= 1.0F && !isCharged(stack) && hasAmmo(entityLiving, stack)) {
            setCharged(stack, true);
            SoundCategory soundcategory = entityLiving instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            worldIn.playSound((PlayerEntity) null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    public float getCrossbowCharge(int useTime, ItemStack stack) {
        float crossbowChargeTime = this.getCrossbowChargeTime(stack);
        float f = (float) useTime / crossbowChargeTime;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public int getCrossbowChargeTime(ItemStack stack) {
        int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        if (this.hasQuickChargeBuiltIn(stack)) quickChargeLevel++;
        int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);
        if (this.hasAccelerateBuiltIn(stack)) accelerateLevel++;

        IBow weaponCap = CapabilityHelper.getWeaponCapability(stack);
        int minTime = 1;
        if (weaponCap == null) return Math.max(this.getDefaultChargeTime() - 5 * quickChargeLevel, minTime);
        int crossbowChargeTime = weaponCap.getCrossbowChargeTime();
        long lastFiredTime = weaponCap.getLastFiredTime();

        if (accelerateLevel > 0 && lastFiredTime > 0) {
            return Math.max(crossbowChargeTime - 5 * quickChargeLevel, minTime);
        } else {
            return Math.max(this.getDefaultChargeTime() - 5 * quickChargeLevel, minTime);
        }
    }

    public SoundEvent getCrossbowSoundEvent(int i) {
        switch (i) {
            case 1:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.CROSSBOW_LOADING_START;
        }
    }

    public void fireCrossbowProjectiles(World world, LivingEntity livingEntity, Hand hand, ItemStack stack, float velocityIn, float inaccuracyIn) {
        List<ItemStack> list = AbstractDungeonsCrossbowItem.getChargedProjectiles(stack);
        float[] randomSoundPitches = AbstractDungeonsCrossbowItem.getRandomSoundPitches(livingEntity.getRandom());

        for (int i = 0; i < list.size(); ++i) {
            ItemStack currentProjectile = list.get(i);
            boolean playerInCreativeMode = livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.instabuild;
            if (!currentProjectile.isEmpty()) {
                if (i == 0) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, 0.0F);
                } else if (i == 1) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, -10.0F);
                } else if (i == 2) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, 10.0F);
                } else if (i == 3) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 2], playerInCreativeMode, velocityIn, inaccuracyIn, -20.0F);
                } else if (i == 4) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 2], playerInCreativeMode, velocityIn, inaccuracyIn, 20.0F);
                } else if (i == 5) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 4], playerInCreativeMode, velocityIn, inaccuracyIn, -30.0F);
                } else if (i == 6) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 4], playerInCreativeMode, velocityIn, inaccuracyIn, 30.0F);
                }
            }
        }

        AbstractDungeonsCrossbowItem.onCrossbowShot(world, livingEntity, stack);
    }

    private AbstractArrowEntity createCrossbowArrow(World world, LivingEntity livingEntity, ItemStack stack, ItemStack stack1) {
        ArrowItem arrowItem = (ArrowItem) ((ArrowItem) (stack1.getItem() instanceof ArrowItem ? stack1.getItem() : Items.ARROW));
        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(world, stack1, livingEntity);
        if (livingEntity instanceof PlayerEntity) {
            abstractArrowEntity.setCritArrow(true);
        }

        abstractArrowEntity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractArrowEntity.setShotFromCrossbow(true);
        int piercingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, stack);
        if (this.hasPiercingBuiltIn(stack)) piercingLevel++;
        if (piercingLevel > 0) {
            abstractArrowEntity.setPierceLevel((byte) piercingLevel);
        }

        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        if (this.shootsHeavyArrows(stack)) powerLevel++;
        if (powerLevel > 0) {
            abstractArrowEntity.setBaseDamage(abstractArrowEntity.getBaseDamage() + (double) powerLevel * 0.5D + 0.5D);
        }

        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        if (this.shootsHeavyArrows(stack)) punchLevel++;
        if (punchLevel > 0) {
            abstractArrowEntity.setKnockback(punchLevel);
        }

        RangedAttackHelper.addWeaponTags(abstractArrowEntity, stack);
        return abstractArrowEntity;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return getCrossbowChargeTime(stack) + 3;
    }

    public Rarity getRarity(ItemStack itemStack) {

        if (this.isUnique) {
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return true;
    }

    // FORMER CROSSBOWITEM STATIC METHODS MADE NON-STATIC
    void fireProjectile(World worldIn, LivingEntity shooter, Hand handIn, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
        if (!worldIn.isClientSide) {
            boolean flag = projectile.getItem() == Items.FIREWORK_ROCKET;
            ProjectileEntity projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(worldIn, projectile, shooter, shooter.getX(), shooter.getEyeY() - (double) 0.15F, shooter.getZ(), true);
            } else {
                projectileentity = createCrossbowArrow(worldIn, shooter, crossbow, projectile);
                if (isCreativeMode || projectileAngle != 0.0F) {
                    ((AbstractArrowEntity) projectileentity).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }
            }

            if (shooter instanceof ICrossbowUser) {
                ICrossbowUser icrossbowuser = (ICrossbowUser) shooter;
                icrossbowuser.shootCrossbowProjectile(Objects.requireNonNull(icrossbowuser.getTarget()), crossbow, projectileentity, projectileAngle);
            } else {
                Vector3d vector3d1 = shooter.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), projectileAngle, true);
                Vector3d vector3d = shooter.getViewVector(1.0F);
                Vector3f vector3f = new Vector3f(vector3d);
                vector3f.transform(quaternion);
                projectileentity.shoot((double) vector3f.x(), (double) vector3f.y(), (double) vector3f.z(), velocity, inaccuracy);
            }

            damageItem(flag?3:1, crossbow, shooter, handIn);
            worldIn.addFreshEntity(projectileentity);
            worldIn.playSound((PlayerEntity) null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
        }
    }

    protected void damageItem(int amount, ItemStack crossbow, LivingEntity shooter, Hand handIn){
        crossbow.hurtAndBreak(amount, shooter, (p_220017_1_) -> {
            p_220017_1_.broadcastBreakEvent(handIn);
        });
    }

    protected static boolean hasAmmo(LivingEntity entityIn, ItemStack stack) {
        int multishotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
        AbstractDungeonsCrossbowItem adci=((AbstractDungeonsCrossbowItem) stack.getItem());
        if (adci.hasMultishotBuiltIn(stack)) multishotLevel++;
        if (adci.hasExtraMultishot(stack)) multishotLevel++;
        int arrowsToFire = 1 + multishotLevel * 2;
        boolean flag = entityIn instanceof PlayerEntity && ((PlayerEntity) entityIn).abilities.instabuild;
        ItemStack itemstack = entityIn.getProjectile(stack);
        ItemStack itemstack1 = itemstack.copy();

        for (int i = 0; i < arrowsToFire; ++i) {
            if (i > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!canAddChargedProjectile(entityIn, stack, itemstack, i > 0, flag)) {
                return false;
            }
        }

        return true;
    }
}
