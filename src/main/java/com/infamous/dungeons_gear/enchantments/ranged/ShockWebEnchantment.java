package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AbilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

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
    public boolean canApply(ItemStack stack) {
        return ENABLED && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ENABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canGenerateInLoot() {
        return ENABLED && super.canGenerateInLoot();
    }

    @Override
    public boolean canVillagerTrade() {
        return ENABLED && super.canVillagerTrade();
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return ENABLED && DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (enchantment != Enchantments.PUNCH || enchantment != Enchantments.POWER);
    }

    @SubscribeEvent
    public static void onShockWebImpact(ProjectileImpactEvent.Arrow event){
        if(!ENABLED) return;
        DungeonsGear.LOGGER.info("Firing shock web impact");
        AbstractArrowEntity eventArrow = event.getArrow();
        int shockWebLevel = ModEnchantmentHelper.enchantmentTagToLevel(eventArrow, RangedEnchantmentList.SHOCK_WEB);
        DungeonsGear.LOGGER.info("Shock web level is {}!", shockWebLevel);
        Entity shooter = eventArrow.func_234616_v_();
        DungeonsGear.LOGGER.info("Shooter is {}!", shooter);
        World world = eventArrow.world;
        if(shockWebLevel > 0 && shooter instanceof LivingEntity){
            double searchRadius = 16.0D;
            AxisAlignedBB boundingBox = eventArrow.getBoundingBox().grow(searchRadius, searchRadius, searchRadius);
            List<AbstractArrowEntity> nearbyArrows = world.getEntitiesWithinAABB(AbstractArrowEntity.class, boundingBox,
                    nearbyArrow -> nearbyArrow != eventArrow);
            if(nearbyArrows.isEmpty()) return;
            DungeonsGear.LOGGER.info("Found {} arrows!", nearbyArrows.size());

            int shockWebsCreated = 0;
            for(AbstractArrowEntity nearbyArrow : nearbyArrows){
                if(shockWebsCreated >= shockWebLevel) break;

                BlockPos fromPos = eventArrow.getPosition();
                DungeonsGear.LOGGER.info("From Block: {}", world.getBlockState(fromPos));
                Vector3d from = eventArrow.getPositionVec();
                BlockPos toPos = nearbyArrow.getPosition();
                DungeonsGear.LOGGER.info("To Block: {}", world.getBlockState(toPos));
                Vector3d to = nearbyArrow.getPositionVec();
                RayTraceResult traceBetweenArrows = world.rayTraceBlocks(new RayTraceContext(from, to, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, eventArrow));
                if (traceBetweenArrows.getType() != RayTraceResult.Type.MISS) {
                    to = traceBetweenArrows.getHitVec();
                }
                List<LivingEntity> entitiesToShock = ProjectileEffectHelper.rayTraceEntities(world, from, to, boundingBox,
                        (nearbyEntity) -> AbilityHelper.canApplyToEnemy((LivingEntity)shooter, nearbyEntity));
                if(entitiesToShock.isEmpty()) continue;
                DungeonsGear.LOGGER.info("Found {} targets!", entitiesToShock.size());

                for(LivingEntity target : entitiesToShock){
                    ElectricShockDamageSource shockDamageSource = new ElectricShockDamageSource(shooter);
                    target.attackEntityFrom(shockDamageSource, 5.0F);
                }
                shockWebsCreated++;
            }
        }
    }
}
