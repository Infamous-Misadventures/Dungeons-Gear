package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;

@Mod.EventBusSubscriber(modid = MODID)
public class ShockWebEnchantment extends DungeonsEnchantment {
    // I can't get the clipping logic to work, so I am disabling the functionality of the enchantment for now
    private static final boolean ENABLED = false;

    public ShockWebEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.BOW, ModEnchantmentTypes.WEAPON_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ENABLED && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ENABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isDiscoverable() {
        return ENABLED && super.isDiscoverable();
    }

    @Override
    public boolean isTradeable() {
        return ENABLED && super.isTradeable();
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return ENABLED && DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (enchantment != Enchantments.PUNCH_ARROWS || enchantment != Enchantments.POWER_ARROWS);
    }

    @SubscribeEvent
    public static void onShockWebImpact(ProjectileImpactEvent event) {
        if (!ENABLED) return;
        DungeonsGear.LOGGER.info("Firing shock web impact");
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            int shockWebLevel = ArrowHelper.enchantmentTagToLevel(arrow, EnchantmentInit.SHOCK_WEB.get());
            DungeonsGear.LOGGER.info("Shock web level is {}!", shockWebLevel);
            Entity shooter = arrow.getOwner();
            DungeonsGear.LOGGER.info("Shooter is {}!", shooter);
            Level world = arrow.level;
            if (shockWebLevel > 0 && shooter instanceof LivingEntity) {
                double searchRadius = 16.0D;
                AABB boundingBox = arrow.getBoundingBox().inflate(searchRadius, searchRadius, searchRadius);
                List<AbstractArrow> nearbyArrows = world.getEntitiesOfClass(AbstractArrow.class, boundingBox,
                        nearbyArrow -> nearbyArrow != arrow);
                if (nearbyArrows.isEmpty()) return;
                DungeonsGear.LOGGER.info("Found {} arrows!", nearbyArrows.size());

                int shockWebsCreated = 0;
                for (AbstractArrow nearbyArrow : nearbyArrows) {
                    if (shockWebsCreated >= shockWebLevel) break;

                    BlockPos fromPos = arrow.blockPosition();
                    DungeonsGear.LOGGER.info("From Block: {}", world.getBlockState(fromPos));
                    Vec3 from = arrow.position().add(0, arrow.getBbHeight() * 0.5D, 0);
                    BlockPos toPos = nearbyArrow.blockPosition();
                    DungeonsGear.LOGGER.info("To Block: {}", world.getBlockState(toPos));
                    Vec3 to = nearbyArrow.position().add(0, nearbyArrow.getBbHeight() * 0.5D, 0);
                /*
                RayTraceResult traceBetweenArrows = world.rayTraceBlocks(new RayTraceContext(from, to, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, arrow));
                if (traceBetweenArrows.getType() != RayTraceResult.Type.MISS) {
                    to = traceBetweenArrows.getHitVec();
                }
                 */
                    List<LivingEntity> entitiesToShock = ProjectileEffectHelper.rayTraceEntities(world, from, to, boundingBox,
                            getCanApplyToEnemyPredicate((LivingEntity) shooter));
                    if (entitiesToShock.isEmpty()) continue;
                    DungeonsGear.LOGGER.info("Found {} targets!", entitiesToShock.size());

                    for (LivingEntity target : entitiesToShock) {
                        ElectricShockDamageSource shockDamageSource = new ElectricShockDamageSource(shooter);
                        target.hurt(shockDamageSource, 5.0F);
                    }
                    shockWebsCreated++;
                }
            }
        }
    }
}
