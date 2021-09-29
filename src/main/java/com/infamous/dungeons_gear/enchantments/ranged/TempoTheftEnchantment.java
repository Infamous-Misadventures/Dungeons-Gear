package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class TempoTheftEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_TEMPO_THEFT_TAG = "IntrinsicTempoTheft";

    public TempoTheftEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onNocturnalBowImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if(!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.getOwner();
        LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
        int tempoTheftLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.TEMPO_THEFT);
        boolean uniqueWeaponFlag = arrow.getTags().contains(INTRINSIC_TEMPO_THEFT_TAG);
        if(tempoTheftLevel > 0 || uniqueWeaponFlag){
            if(uniqueWeaponFlag) tempoTheftLevel++;
            if (shooter == victim) return;
            EffectInstance speed = new EffectInstance(Effects.MOVEMENT_SPEED, 80, tempoTheftLevel);
            EffectInstance slowness = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 80, tempoTheftLevel);
            shooter.addEffect(speed);
            victim.addEffect(slowness);
        }
    }
}
