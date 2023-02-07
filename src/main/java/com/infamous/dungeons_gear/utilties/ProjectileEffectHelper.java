package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCaster;
import com.infamous.dungeons_libraries.capabilities.soulcaster.SoulCasterHelper;
import com.infamous.dungeons_libraries.items.gearconfig.CrossbowGear;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.Predicate;

import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToSecondEnemyPredicate;

public class ProjectileEffectHelper {

    public static final Random RANDOM = new Random();

    public static void ricochetArrowTowardsOtherEntity(LivingEntity attacker, LivingEntity victim, AbstractArrow arrowEntity, int distance) {
        Level world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, victim.getBoundingBox().inflate(distance), getCanApplyToSecondEnemyPredicate(attacker, victim));
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
        double euclideanDist = Mth.sqrt((float) (towardsX * towardsX + towardsZ * towardsZ));
        double towardsY = target.getY(0.3333333333333333D) - arrowEntity.getY() + euclideanDist * (double) 0.2F;
        setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
        //
    }

    public static void fireBonusShotTowardsOtherEntity(LivingEntity attacker, int distance, double bonusShotDamageMultiplier, float arrowVelocity) {
        Level world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, new AABB(attacker.getX() - distance, attacker.getY() - distance, attacker.getZ() - distance,
                attacker.getX() + distance, attacker.getY() + distance, attacker.getZ() + distance), getCanApplyToEnemyPredicate(attacker));
        if (nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.distanceToSqr(attacker)));
        LivingEntity target = nearbyEntities.get(0);
        if (target != null) {
            ArrowItem arrowItem = (ArrowItem) Items.ARROW;
            AbstractArrow arrowEntity = arrowItem.createArrow(world, new ItemStack(Items.ARROW), attacker);
            arrowEntity.setBaseDamage(arrowEntity.getBaseDamage() * bonusShotDamageMultiplier);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getX() - attacker.getX();
            double towardsZ = target.getZ() - attacker.getZ();
            double euclideanDist = Mth.sqrt((float) (towardsX * towardsX + towardsZ * towardsZ));
            double towardsY = target.getY(0.3333333333333333D) - arrowEntity.getY() + euclideanDist * (double) 0.2F;
            arrowEntity.shootFromRotation(attacker, attacker.getXRot(), attacker.getYRot(), 0.0F, arrowVelocity * 3.0F, 1.0F);
            setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
            arrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            //arrowEntity.addTag("BonusProjectile"); // Commented this out because it should no longer be used, if a user reports a bug about this, uncomment this
            attacker.level.addFreshEntity(arrowEntity);
        }
    }

    public static void fireBurstBowstringShots(LivingEntity attacker, int distance, double damageMultiplier, float arrowVelocity, int arrowsToFire) {
        Level world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, new AABB(attacker.getX() - distance, attacker.getY() - distance, attacker.getZ() - distance,
                attacker.getX() + distance, attacker.getY() + distance, attacker.getZ() + distance), getCanApplyToEnemyPredicate(attacker));
        if (nearbyEntities.isEmpty()) return;

        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.distanceToSqr(attacker)));
        int amount = Math.min(arrowsToFire, nearbyEntities.size());
        for (int i = 0; i < amount; i++) {
            LivingEntity target = nearbyEntities.get(i);
            ArrowItem arrowItem = (ArrowItem) Items.ARROW;
            AbstractArrow arrowEntity = arrowItem.createArrow(world, new ItemStack(Items.ARROW), attacker);
            arrowEntity.setBaseDamage(arrowEntity.getBaseDamage() * damageMultiplier);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getX() - attacker.getX();
            double towardsZ = target.getZ() - attacker.getZ();
            double euclideanDist = Mth.sqrt((float) (towardsX * towardsX + towardsZ * towardsZ));
            double towardsY = target.getY(0.3333333333333333D) - arrowEntity.getY() + euclideanDist * (double) 0.2F;
            arrowEntity.shootFromRotation(attacker, attacker.getXRot(), attacker.getYRot(), 0.0F, arrowVelocity * 3.0F, 1.0F);
            setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
            //
            arrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            attacker.level.addFreshEntity(arrowEntity);
        }
    }

    public static void fireSnowballAtNearbyEnemy(LivingEntity attacker, int distance) {
        Level world = attacker.getCommandSenderWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, new AABB(attacker.getX() - distance, attacker.getY() - distance, attacker.getZ() - distance,
                attacker.getX() + distance, attacker.getY() + distance, attacker.getZ() + distance), getCanApplyToEnemyPredicate(attacker));
        if (nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.distanceToSqr(attacker)));
        LivingEntity target = nearbyEntities.get(0);
        if (target != null) {
            Snowball snowballEntity = new Snowball(world, attacker);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getX() - attacker.getX();
            double towardsZ = target.getZ() - attacker.getZ();
            double euclideanDist = Mth.sqrt((float) (towardsX * towardsX + towardsZ * towardsZ));
            double towardsY = target.getY(0.3333333333333333D) - snowballEntity.getY() + euclideanDist * (double) 0.2F;
            snowballEntity.shootFromRotation(attacker, attacker.getXRot(), attacker.getYRot(), 0.0F, 1.5F, 1.0F);
            setProjectileTowards(snowballEntity, towardsX, towardsY, towardsZ, 0);
            //
            attacker.level.addFreshEntity(snowballEntity);
        }
    }

    public static void ricochetArrowLikeShield(AbstractArrow arrowEntity, LivingEntity entity) {
        //int k = entity.getFireTimer();
        // set fire timer
        //entity.setRemainingFireTicks(k);
        arrowEntity.setDeltaMovement(arrowEntity.getDeltaMovement().scale(-0.1D));
        arrowEntity.setYRot(arrowEntity.getYRot() + 180.0F);
        arrowEntity.yRotO += 180.0F;
        if (!arrowEntity.level.isClientSide && arrowEntity.getDeltaMovement().lengthSqr() < 1.0E-7D) {
            if (arrowEntity.pickup == AbstractArrow.Pickup.ALLOWED) {
                // arrowEntity.getArrowStack() => new ItemStack(Items.ARROW)
                arrowEntity.spawnAtLocation(new ItemStack(Items.ARROW), 0.1F);
            }

            arrowEntity.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    private static AbstractArrow createChainReactionProjectile(Level world, LivingEntity attacker, ItemStack ammoStack, AbstractArrow originalArrow) {
        ArrowItem arrowItem = (ArrowItem) (ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
        AbstractArrow abstractArrowEntity = arrowItem.createArrow(world, ammoStack, attacker);
        if (attacker instanceof Player) {
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

    public static void fireChainReactionProjectiles(Level world, LivingEntity attacker, LivingEntity victim, float v, float v1, AbstractArrow originalArrow) {
        float[] randomSoundPitches = getRandomSoundPitches(victim.getRandom());
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

    public static float[] getRandomSoundPitches(RandomSource rand) {
        boolean flag = rand.nextBoolean();
        return new float[]{1.0F, getRandomSoundPitch(flag, rand), getRandomSoundPitch(!flag, rand)};
    }

    private static float getRandomSoundPitch(boolean flagIn, RandomSource random) {
        float f = flagIn ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void fireChainReactionProjectileFromVictim(Level world, LivingEntity attacker, LivingEntity victim, ItemStack projectileStack, float soundPitch, float v1, float v2, float centerOffset, AbstractArrow originalArrow) {
        if (!world.isClientSide) {
            AbstractArrow projectile;
            projectile = createChainReactionProjectile(world, attacker, projectileStack, originalArrow);
            projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            Vec3 upVector = victim.getUpVector(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(upVector), centerOffset, true);
            Vec3 lookVector = victim.getViewVector(1.0F);
            Vector3f vector3f = new Vector3f(lookVector);
            vector3f.transform(quaternion);
            projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), v1, v2);
            world.addFreshEntity(projectile);
            world.playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.CROSSBOW_SHOOT, attacker.getSoundSource(), 1.0F, soundPitch);
        }
    }

    public static boolean soulsCriticalBoost(Player attacker, ItemStack mainhand) {
        SoulCaster soulCasterCapability = SoulCasterHelper.getSoulCasterCapability(attacker);

        float soulsLimit = 50.0F;
        float numSouls = Math.min(soulCasterCapability.getSouls(), soulsLimit);
        if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.ENIGMA_RESONATOR.get())) {
            int enigmaResonatorLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.ENIGMA_RESONATOR.get(), mainhand);
            float soulsCriticalBoostChanceCap;
            soulsCriticalBoostChanceCap = 0.1F + 0.05F * enigmaResonatorLevel;
            float soulsCriticalBoostRand = attacker.getRandom().nextFloat();
            return soulsCriticalBoostRand <= Math.min(numSouls / soulsLimit, soulsCriticalBoostChanceCap);
        }
        return false;
    }

    //Chief: it's a copy-paste of ProjectileEntity::shoot that creates a new Random. Why?
    //Infamous: This one doesn't require a velocity parameter, but I imagine I still added this unnecessarily
    //Infamous: Removed the Random instantiation and made it a constant
    public static void setProjectileTowards(Projectile projectileEntity, double x, double y, double z, float inaccuracy) {
        Vec3 vector3d = (new Vec3(x, y, z))
                .normalize()
                .add(RANDOM.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
                        RANDOM.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
                        RANDOM.nextGaussian() * (double) 0.0075F * (double) inaccuracy);
        projectileEntity.setDeltaMovement(vector3d);
        float f = Mth.sqrt((float) vector3d.horizontalDistanceSqr());
        projectileEntity.setYRot((float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
        projectileEntity.setXRot((float) (Mth.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI)));
        projectileEntity.yRotO = projectileEntity.getYRot();
        projectileEntity.xRotO = projectileEntity.getXRot();
    }

    public static List<LivingEntity> rayTraceEntities(Level worldIn, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<LivingEntity> filter) {
        List<LivingEntity> rayTraceEntities = new ArrayList<>();
        List<LivingEntity> nearbyEntities = worldIn.getEntitiesOfClass(LivingEntity.class, boundingBox, filter);
        for (LivingEntity nearbyEntity : nearbyEntities) {
            AABB nearbyEntityBB = nearbyEntity.getBoundingBox().inflate(0.3F);
            Optional<Vec3> entityClip = nearbyEntityBB.clip(startVec, endVec);
            if (entityClip.isPresent()) {
                rayTraceEntities.add(nearbyEntity);
            }
        }

        return rayTraceEntities;
    }
}
