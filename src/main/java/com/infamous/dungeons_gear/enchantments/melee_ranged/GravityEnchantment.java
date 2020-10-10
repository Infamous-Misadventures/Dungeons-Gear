package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.RangedWeaponList.IMPLODING_CROSSBOW;
import static com.infamous.dungeons_gear.items.RangedWeaponList.VOIDCALLER;
import static com.infamous.dungeons_gear.items.WeaponList.HAMMER_OF_GRAVITY;

@Mod.EventBusSubscriber(modid = MODID)
public class GravityEnchantment extends Enchantment {

    public GravityEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        ItemStack mainhand = user.getHeldItemMainhand();
        boolean uniqueWeaponFlag = mainhand.getItem() == HAMMER_OF_GRAVITY || mainhand.getItem() == VOIDCALLER || mainhand.getItem() == IMPLODING_CROSSBOW;
        if(uniqueWeaponFlag) level++;
        AreaOfEffectHelper.pullInNearbyEntities(user, (LivingEntity)target, level * 3);
    }

    @SubscribeEvent
    public static void onHammerOfGravityAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getTrueSource() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        if((mainhand.getItem() == HAMMER_OF_GRAVITY
                && !ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.GRAVITY))){
            AreaOfEffectHelper.pullInNearbyEntities(attacker, victim, 3);
        }
    }

    @SubscribeEvent
    public static void onGravityCrossbowImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        //if(!EnchantUtils.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.func_234616_v_();
        int gravityLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrow, MeleeRangedEnchantmentList.GRAVITY);
        boolean uniqueWeaponFlag = arrow.getTags().contains("Voidcaller") || arrow.getTags().contains("ImplodingCrossbow");
        if(uniqueWeaponFlag
                && !(gravityLevel > 0)){
            if(rayTraceResult instanceof EntityRayTraceResult){
                EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) rayTraceResult;
                if(entityRayTraceResult.getEntity() instanceof LivingEntity){
                    LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
                    AreaOfEffectHelper.pullInNearbyEntities(shooter, victim, 3);
                }
            }
            if(rayTraceResult instanceof BlockRayTraceResult){
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)rayTraceResult;
                BlockPos blockPos = blockRayTraceResult.getPos();
                AreaOfEffectHelper.pullInNearbyEntitiesAtPos(shooter, blockPos, 3);
            }
        }else if(gravityLevel > 0){
            if(uniqueWeaponFlag) gravityLevel++;
            if(rayTraceResult instanceof BlockRayTraceResult){
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)rayTraceResult;
                BlockPos blockPos = blockRayTraceResult.getPos();
                AreaOfEffectHelper.pullInNearbyEntitiesAtPos(shooter, blockPos, 3 * gravityLevel);
            }
        }
    }
}
