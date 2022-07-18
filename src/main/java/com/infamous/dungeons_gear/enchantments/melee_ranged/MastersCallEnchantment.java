package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.PetHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_libraries.utils.ArrowHelper.enchantmentTagToLevel;
import static com.infamous.dungeons_libraries.utils.ArrowHelper.shooterIsLiving;

@Mod.EventBusSubscriber(modid = MODID)
public class MastersCallEnchantment extends DungeonsEnchantment {

    public MastersCallEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    @SubscribeEvent
    public static void onWeaponAttack(LivingAttackEvent event) {
        if (event.getSource().getDirectEntity() != event.getSource().getEntity()) return;
        if (event.getSource() instanceof OffhandAttackDamageSource) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
        if (attacker.getLastHurtMobTimestamp() == attacker.tickCount) return;
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getMainHandItem();
        if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.MASTERS_CALL)) {
            PetHelper.makeNearbyPetsAttackTarget(victim, attacker);
        }
    }

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if(event.getProjectile() instanceof AbstractArrow arrow) {
            if (!shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();

            int enchantLevel = enchantmentTagToLevel(arrow, MeleeRangedEnchantmentList.MASTERS_CALL);

            if (enchantLevel > 0) {
                if (rayTraceResult instanceof EntityHitResult) {
                    EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
                    if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
                        LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
                        PetHelper.makeNearbyPetsAttackTarget(victim, shooter);
                    }
                }
            }
        }
    }

    public int getMaxLevel() {
        return 1;
    }

}
