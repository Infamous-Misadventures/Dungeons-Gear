package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.ranged.crossbows.AbstractDungeonsCrossbowItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static net.minecraft.entity.Entity.horizontalMag;

public class ProjectileEffectHelper {
    public static void ricochetArrowTowardsOtherEntity(LivingEntity attacker, LivingEntity victim, AbstractArrowEntity arrowEntity, int distance) {
        World world = attacker.getEntityWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, victim.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, victim, nearbyEntity));
        if (nearbyEntities.isEmpty()) return;
        LivingEntity target = null;
        double dist = 26052020;
        for (LivingEntity le : nearbyEntities) {
            if (target == null || victim.getDistanceSq(le) < dist) {
                target = le;
                dist = victim.getDistanceSq(le);
            }
        }
        byte pierceLevel = arrowEntity.getPierceLevel();
        pierceLevel++;
        arrowEntity.setPierceLevel(pierceLevel);
        // borrowed from AbstractSkeletonEntity
        double towardsX = target.getPosX() - victim.getPosX();
        double towardsZ = target.getPosZ() - victim.getPosZ();
        double euclideanDist = (double) MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
        double towardsY = target.getPosYHeight(0.3333333333333333D) - arrowEntity.getPosY() + euclideanDist * (double) 0.2F;
        setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
        //
    }

    public static void fireBonusShotTowardsOtherEntity(LivingEntity attacker, int distance, double bonusShotDamageMultiplier, float arrowVelocity) {
        World world = attacker.getEntityWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.getDistanceSq(attacker)));
        LivingEntity target = nearbyEntities.get(0);
        if (target != null) {
            ArrowItem arrowItem = (ArrowItem) ((ArrowItem) (Items.ARROW));
            AbstractArrowEntity arrowEntity = arrowItem.createArrow(world, new ItemStack(Items.ARROW), attacker);
            arrowEntity.setDamage(arrowEntity.getDamage() * bonusShotDamageMultiplier);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getPosX() - attacker.getPosX();
            double towardsZ = target.getPosZ() - attacker.getPosZ();
            double euclideanDist = (double) MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getPosYHeight(0.3333333333333333D) - arrowEntity.getPosY() + euclideanDist * (double) 0.2F;
            arrowEntity.func_234612_a_(attacker, attacker.rotationPitch, attacker.rotationYaw, 0.0F, arrowVelocity * 3.0F, 1.0F);
            setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
            //
            arrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            arrowEntity.addTag("BonusProjectile");
            attacker.world.addEntity(arrowEntity);
        }
    }

    public static void fireSnowballAtNearbyEnemy(LivingEntity attacker, int distance) {
        World world = attacker.getEntityWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.getDistanceSq(attacker)));
        LivingEntity target = nearbyEntities.get(0);
        if (target != null) {
            SnowballEntity snowballEntity = new SnowballEntity(world, attacker);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getPosX() - attacker.getPosX();
            double towardsZ = target.getPosZ() - attacker.getPosZ();
            double euclideanDist = (double) MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getPosYHeight(0.3333333333333333D) - snowballEntity.getPosY() + euclideanDist * (double) 0.2F;
            snowballEntity.func_234612_a_(attacker, attacker.rotationPitch, attacker.rotationYaw, 0.0F, 1.5F, 1.0F);
            setProjectileTowards(snowballEntity, towardsX, towardsY, towardsZ, 0);
            //
            attacker.world.addEntity(snowballEntity);
        }
    }

    public static void ricochetArrowLikeShield(AbstractArrowEntity arrowEntity, LivingEntity entity) {
        //int k = entity.getFireTimer();
        // set fire timer
        //entity.func_241209_g_(k);
        arrowEntity.setMotion(arrowEntity.getMotion().scale(-0.1D));
        arrowEntity.rotationYaw += 180.0F;
        arrowEntity.prevRotationYaw += 180.0F;
        if (!arrowEntity.world.isRemote && arrowEntity.getMotion().lengthSquared() < 1.0E-7D) {
            if (arrowEntity.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                // arrowEntity.getArrowStack() => new ItemStack(Items.ARROW)
                arrowEntity.entityDropItem(new ItemStack(Items.ARROW), 0.1F);
            }

            arrowEntity.remove();
        }
    }

    private static AbstractArrowEntity createChainReactionProjectile(World world, LivingEntity attacker, ItemStack ammoStack, AbstractArrowEntity originalArrow) {
        ArrowItem arrowItem = (ArrowItem) ((ArrowItem) (ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW));
        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(world, ammoStack, attacker);
        if (attacker instanceof PlayerEntity) {
            abstractArrowEntity.setIsCritical(true);
        }

        abstractArrowEntity.setHitSound(SoundEvents.ITEM_CROSSBOW_HIT);
        abstractArrowEntity.setShotFromCrossbow(true);
        abstractArrowEntity.addTag("ChainReactionProjectile");
        Set<String> originalArrowTags = originalArrow.getTags();
        for (String tag : originalArrowTags) {
            abstractArrowEntity.addTag(tag);
        }
        return abstractArrowEntity;
    }

    public static void fireChainReactionProjectiles(World world, LivingEntity attacker, LivingEntity victim, float v, float v1, AbstractArrowEntity originalArrow) {
        float[] randomSoundPitches = AbstractDungeonsCrossbowItem.getRandomSoundPitches(victim.getRNG());
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
        if (!world.isRemote) {
            AbstractArrowEntity projectile;
            projectile = createChainReactionProjectile(world, attacker, projectileStack, originalArrow);
            projectile.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            Vector3d upVector = victim.getUpVector(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(upVector), centerOffset, true);
            Vector3d lookVector = victim.getLook(1.0F);
            Vector3f vector3f = new Vector3f(lookVector);
            vector3f.transform(quaternion);
            projectile.shoot((double) vector3f.getX(), (double) vector3f.getY(), (double) vector3f.getZ(), v1, v2);
            world.addEntity(projectile);
            world.playSound((PlayerEntity) null, victim.getPosX(), victim.getPosY(), victim.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
        }
    }

    public static boolean soulsCriticalBoost(PlayerEntity attacker, ItemStack mainhand) {
        int numSouls = Math.min(attacker.experienceTotal, 50);
        boolean uniqueWeaponFlag = mainhand.getItem() == DeferredItemInit.FERAL_SOUL_CROSSBOW.get()
                || mainhand.getItem() == DeferredItemInit.SOUL_HUNTER_CROSSBOW.get();
        if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.ENIGMA_RESONATOR)) {
            int enigmaResonatorLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.ENIGMA_RESONATOR, mainhand);
            float soulsCriticalBoostChanceCap;
            soulsCriticalBoostChanceCap = 0.1F + 0.05F * enigmaResonatorLevel;
            float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
            if (soulsCriticalBoostRand <= Math.min(numSouls / 50.0, soulsCriticalBoostChanceCap)) {
                return true;
            }
        }
        if (uniqueWeaponFlag) {
            float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
            return soulsCriticalBoostRand <= Math.min(numSouls / 50.0, 0.15F);
        }
        return false;
    }

    //it's a copy-paste of ProjectileEntity::shoot that creates a new Random. Why?
    public static void setProjectileTowards(ProjectileEntity projectileEntity, double x, double y, double z, float inaccuracy) {
        Random random = new Random();
        Vector3d vector3d = (new Vector3d(x, y, z))
                .normalize()
                .add(random.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
                        random.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
                        random.nextGaussian() * (double) 0.0075F * (double) inaccuracy);
        projectileEntity.setMotion(vector3d);
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        projectileEntity.rotationYaw = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
        projectileEntity.rotationPitch = (float) (MathHelper.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI));
        projectileEntity.prevRotationYaw = projectileEntity.rotationYaw;
        projectileEntity.prevRotationPitch = projectileEntity.rotationPitch;
    }
}
