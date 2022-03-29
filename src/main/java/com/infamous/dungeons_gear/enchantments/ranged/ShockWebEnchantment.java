package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
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
    public static void onShockWebImpact(ProjectileImpactEvent.Arrow event){
        if(!ENABLED) return;
        DungeonsGear.LOGGER.info("Firing shock web impact");
        AbstractArrowEntity eventArrow = event.getArrow();
        int shockWebLevel = ArrowHelper.enchantmentTagToLevel(eventArrow, RangedEnchantmentList.SHOCK_WEB);
        DungeonsGear.LOGGER.info("Shock web level is {}!", shockWebLevel);
        Entity shooter = eventArrow.getOwner();
        DungeonsGear.LOGGER.info("Shooter is {}!", shooter);
        World world = eventArrow.level;
        if(shockWebLevel > 0 && shooter instanceof LivingEntity){
            double searchRadius = 16.0D;
            AxisAlignedBB boundingBox = eventArrow.getBoundingBox().inflate(searchRadius, searchRadius, searchRadius);
            List<AbstractArrowEntity> nearbyArrows = world.getEntitiesOfClass(AbstractArrowEntity.class, boundingBox,
                    nearbyArrow -> nearbyArrow != eventArrow);
            if(nearbyArrows.isEmpty()) return;
            DungeonsGear.LOGGER.info("Found {} arrows!", nearbyArrows.size());

            int shockWebsCreated = 0;
            for(AbstractArrowEntity nearbyArrow : nearbyArrows){
                if(shockWebsCreated >= shockWebLevel) break;

                BlockPos fromPos = eventArrow.blockPosition();
                DungeonsGear.LOGGER.info("From Block: {}", world.getBlockState(fromPos));
                Vector3d from = eventArrow.position().add(0, eventArrow.getBbHeight() * 0.5D, 0);
                BlockPos toPos = nearbyArrow.blockPosition();
                DungeonsGear.LOGGER.info("To Block: {}", world.getBlockState(toPos));
                Vector3d to = nearbyArrow.position().add(0, nearbyArrow.getBbHeight() * 0.5D, 0);
                /*
                RayTraceResult traceBetweenArrows = world.rayTraceBlocks(new RayTraceContext(from, to, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, eventArrow));
                if (traceBetweenArrows.getType() != RayTraceResult.Type.MISS) {
                    to = traceBetweenArrows.getHitVec();
                }
                 */
                List<LivingEntity> entitiesToShock = ProjectileEffectHelper.rayTraceEntities(world, from, to, boundingBox,
                        getCanApplyToEnemyPredicate((LivingEntity) shooter));
                if(entitiesToShock.isEmpty()) continue;
                DungeonsGear.LOGGER.info("Found {} targets!", entitiesToShock.size());

                for(LivingEntity target : entitiesToShock){
                    ElectricShockDamageSource shockDamageSource = new ElectricShockDamageSource(shooter);
                    target.hurt(shockDamageSource, 5.0F);
                }
                shockWebsCreated++;
            }
        }
    }
}
