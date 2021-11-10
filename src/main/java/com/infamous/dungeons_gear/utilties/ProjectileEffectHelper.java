package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.items.ranged.crossbows.AbstractDungeonsCrossbowItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Predicate;

import static net.minecraft.entity.Entity.getHorizontalDistanceSqr;

public class ProjectileEffectHelper {

    public static final Random RANDOM = new Random();

    public static void ricochetArrowTowardsOtherEntity(LivingEntity attacker, LivingEntity victim, AbstractArrowEntity arrowEntity, int distance) {
        World world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesOfClass(LivingEntity.class, victim.getBoundingBox().inflate(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, victim, nearbyEntity));
        if (nearbyEntities.isEmpty()) return;
        LivingEntity target = null;
        double dist = 26052020;
        for (LivingEntity le : nearbyEntities) {
            if (target == null || victim.distanceToSqr(le) < dist) {
                target = le;
                dist = victim.distanceToSqr(le);
            }
        }
        byte pierceLevel = arrowEntity.getPierceLevel();
        pierceLevel++;
        arrowEntity.setPierceLevel(pierceLevel);
        // borrowed from AbstractSkeletonEntity
        double towardsX = target.getX() - victim.getX();
        double towardsZ = target.getZ() - victim.getZ();
        double euclideanDist = (double) MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
        double towardsY = target.getY(0.3333333333333333D) - arrowEntity.getY() + euclideanDist * (double) 0.2F;
        setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
        //
    }

    public static void fireBonusShotTowardsOtherEntity(LivingEntity attacker, int distance, double bonusShotDamageMultiplier, float arrowVelocity) {
        World world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(attacker.getX() - distance, attacker.getY() - distance, attacker.getZ() - distance,
                attacker.getX() + distance, attacker.getY() + distance, attacker.getZ() + distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.distanceToSqr(attacker)));
        LivingEntity target = nearbyEntities.get(0);
        if (target != null) {
            ArrowItem arrowItem = (ArrowItem) ((ArrowItem) (Items.ARROW));
            AbstractArrowEntity arrowEntity = arrowItem.createArrow(world, new ItemStack(Items.ARROW), attacker);
            arrowEntity.setBaseDamage(arrowEntity.getBaseDamage() * bonusShotDamageMultiplier);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getX() - attacker.getX();
            double towardsZ = target.getZ() - attacker.getZ();
            double euclideanDist = (double) MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getY(0.3333333333333333D) - arrowEntity.getY() + euclideanDist * (double) 0.2F;
            arrowEntity.shootFromRotation(attacker, attacker.xRot, attacker.yRot, 0.0F, arrowVelocity * 3.0F, 1.0F);
            setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
            arrowEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            //arrowEntity.addTag("BonusProjectile"); // Commented this out because it should no longer be used, if a user reports a bug about this, uncomment this
            attacker.level.addFreshEntity(arrowEntity);
        }
    }

    public static void fireBurstBowstringShots(LivingEntity attacker, int distance, double damageMultiplier, float arrowVelocity, int arrowsToFire) {
        World world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(attacker.getX() - distance, attacker.getY() - distance, attacker.getZ() - distance,
                attacker.getX() + distance, attacker.getY() + distance, attacker.getZ() + distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.isEmpty()) return;

        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.distanceToSqr(attacker)));
        int amount = Math.min(arrowsToFire, nearbyEntities.size());
        for(int i = 0; i < amount; i++){
            LivingEntity target = nearbyEntities.get(i);
            ArrowItem arrowItem = (ArrowItem) ((ArrowItem) (Items.ARROW));
            AbstractArrowEntity arrowEntity = arrowItem.createArrow(world, new ItemStack(Items.ARROW), attacker);
            arrowEntity.setBaseDamage(arrowEntity.getBaseDamage() * damageMultiplier);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getX() - attacker.getX();
            double towardsZ = target.getZ() - attacker.getZ();
            double euclideanDist = (double) MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getY(0.3333333333333333D) - arrowEntity.getY() + euclideanDist * (double) 0.2F;
            arrowEntity.shootFromRotation(attacker, attacker.xRot, attacker.yRot, 0.0F, arrowVelocity * 3.0F, 1.0F);
            setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
            //
            arrowEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            attacker.level.addFreshEntity(arrowEntity);
        }
    }

    public static void fireSnowballAtNearbyEnemy(LivingEntity attacker, int distance) {
        World world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(attacker.getX() - distance, attacker.getY() - distance, attacker.getZ() - distance,
                attacker.getX() + distance, attacker.getY() + distance, attacker.getZ() + distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.distanceToSqr(attacker)));
        LivingEntity target = nearbyEntities.get(0);
        if (target != null) {
            SnowballEntity snowballEntity = new SnowballEntity(world, attacker);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getX() - attacker.getX();
            double towardsZ = target.getZ() - attacker.getZ();
            double euclideanDist = (double) MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getY(0.3333333333333333D) - snowballEntity.getY() + euclideanDist * (double) 0.2F;
            snowballEntity.shootFromRotation(attacker, attacker.xRot, attacker.yRot, 0.0F, 1.5F, 1.0F);
            setProjectileTowards(snowballEntity, towardsX, towardsY, towardsZ, 0);
            //
            attacker.level.addFreshEntity(snowballEntity);
        }
    }

    public static void ricochetArrowLikeShield(AbstractArrowEntity arrowEntity, LivingEntity entity) {
        //int k = entity.getFireTimer();
        // set fire timer
        //entity.setRemainingFireTicks(k);
        arrowEntity.setDeltaMovement(arrowEntity.getDeltaMovement().scale(-0.1D));
        arrowEntity.yRot += 180.0F;
        arrowEntity.yRotO += 180.0F;
        if (!arrowEntity.level.isClientSide && arrowEntity.getDeltaMovement().lengthSqr() < 1.0E-7D) {
            if (arrowEntity.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                // arrowEntity.getArrowStack() => new ItemStack(Items.ARROW)
                arrowEntity.spawnAtLocation(new ItemStack(Items.ARROW), 0.1F);
            }

            arrowEntity.remove();
        }
    }

    private static AbstractArrowEntity createChainReactionProjectile(World world, LivingEntity attacker, ItemStack ammoStack, AbstractArrowEntity originalArrow) {
        ArrowItem arrowItem = (ArrowItem) ((ArrowItem) (ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW));
        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(world, ammoStack, attacker);
        if (attacker instanceof PlayerEntity) {
            abstractArrowEntity.setCritArrow(true);
        }

        abstractArrowEntity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractArrowEntity.setShotFromCrossbow(true);
        abstractArrowEntity.addTag("ChainReactionProjectile");
        Set<String> originalArrowTags = originalArrow.getTags();
        for (String tag : originalArrowTags) {
            abstractArrowEntity.addTag(tag);
        }
        return abstractArrowEntity;
    }

    public static void fireChainReactionProjectiles(World world, LivingEntity attacker, LivingEntity victim, float v, float v1, AbstractArrowEntity originalArrow) {
        float[] randomSoundPitches = AbstractDungeonsCrossbowItem.getRandomSoundPitches(victim.getRandom());
        for (int i = 0; i < 4; ++i) {
            ItemStack currentProjectile = new ItemStack(Items.ARROW);
            if (!currentProjectile.isEmpty()) {
                if (i == 0) {
                    fireChainReactionProjectileFromVictim(world, attacker, victim, currentProjectile, randomSoundPitches[1], v, v1, 45.0F, originalArrow);
                } else if (i == 1) {
                    fireChainReactionProjectileFromVictim(world, attacker, victim, currentProjectile, randomSoundPitches[2], v, v1, -45.0F, originalArrow);
                } else if (i == 2) {
                    fireChainReactionProjectileFromVictim(world, attacker, victim, currentProjectile, randomSoundPitches[1], v, v1, 135.0F, originalArrow);
                } else if (i == 3) {
                    fireChainReactionProjectileFromVictim(world, attacker, victim, currentProjectile, randomSoundPitches[2], v, v1, -135.0F, originalArrow);
                }
            }
        }
    }

    private static void fireChainReactionProjectileFromVictim(World world, LivingEntity attacker, LivingEntity victim, ItemStack projectileStack, float soundPitch, float v1, float v2, float centerOffset, AbstractArrowEntity originalArrow) {
        if (!world.isClientSide) {
            AbstractArrowEntity projectile;
            projectile = createChainReactionProjectile(world, attacker, projectileStack, originalArrow);
            projectile.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            Vector3d upVector = victim.getUpVector(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(upVector), centerOffset, true);
            Vector3d lookVector = victim.getViewVector(1.0F);
            Vector3f vector3f = new Vector3f(lookVector);
            vector3f.transform(quaternion);
            projectile.shoot((double) vector3f.x(), (double) vector3f.y(), (double) vector3f.z(), v1, v2);
            world.addFreshEntity(projectile);
            world.playSound((PlayerEntity) null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.CROSSBOW_SHOOT, attacker.getSoundSource(), 1.0F, soundPitch);
        }
    }

    public static boolean soulsCriticalBoost(PlayerEntity attacker, ItemStack mainhand) {
        ICombo comboCap = CapabilityHelper.getComboCapability(attacker);
        if(comboCap == null) return false;


        float soulsLimit = 50.0F;
        float numSouls = Math.min(comboCap.getSouls(), soulsLimit);
        boolean uniqueWeaponFlag = hasEnigmaResonatorBuiltIn(mainhand);
        if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.ENIGMA_RESONATOR)) {
            int enigmaResonatorLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeRangedEnchantmentList.ENIGMA_RESONATOR, mainhand);
            float soulsCriticalBoostChanceCap;
            soulsCriticalBoostChanceCap = 0.1F + 0.05F * enigmaResonatorLevel;
            float soulsCriticalBoostRand = attacker.getRandom().nextFloat();
            if (soulsCriticalBoostRand <= Math.min(numSouls / soulsLimit, soulsCriticalBoostChanceCap)) {
                return true;
            }
        }
        if (uniqueWeaponFlag) {
            float soulsCriticalBoostRand = attacker.getRandom().nextFloat();
            return soulsCriticalBoostRand <= Math.min(numSouls / soulsLimit, 0.15F);
        }
        return false;
    }

    private static boolean hasEnigmaResonatorBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IRangedWeapon && ((IRangedWeapon) mainhand.getItem()).hasEnigmaResonatorBuiltIn(mainhand);
    }

    //Chief: it's a copy-paste of ProjectileEntity::shoot that creates a new Random. Why?
    //Infamous: This one doesn't require a velocity parameter, but I imagine I still added this unnecessarily
    //Infamous: Removed the Random instantiation and made it a constant
    public static void setProjectileTowards(ProjectileEntity projectileEntity, double x, double y, double z, float inaccuracy) {
        Vector3d vector3d = (new Vector3d(x, y, z))
                .normalize()
                .add(RANDOM.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
                        RANDOM.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
                        RANDOM.nextGaussian() * (double) 0.0075F * (double) inaccuracy);
        projectileEntity.setDeltaMovement(vector3d);
        float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
        projectileEntity.yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
        projectileEntity.xRot = (float) (MathHelper.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI));
        projectileEntity.yRotO = projectileEntity.yRot;
        projectileEntity.xRotO = projectileEntity.xRot;
    }

    public static List<LivingEntity> rayTraceEntities(World worldIn, Vector3d startVec, Vector3d endVec, AxisAlignedBB boundingBox, Predicate<LivingEntity> filter) {
        List<LivingEntity> rayTraceEntities = new ArrayList<>();
        List<LivingEntity> nearbyEntities = worldIn.getEntitiesOfClass(LivingEntity.class, boundingBox, filter);
        for(LivingEntity nearbyEntity : nearbyEntities) {
            AxisAlignedBB nearbyEntityBB = nearbyEntity.getBoundingBox().inflate((double)0.3F);
            Optional<Vector3d> entityClip = nearbyEntityBB.clip(startVec, endVec);
            if (entityClip.isPresent()) {
                rayTraceEntities.add(nearbyEntity);
            }
        }

        return rayTraceEntities;
    }
}
