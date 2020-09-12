package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.utilties.EnchantUtils;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.utilties.AbilityUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.RangedWeaponList.NOCTURNAL_BOW;

@Mod.EventBusSubscriber(modid= MODID)
public class TempoTheftEnchantment extends Enchantment {

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
        if(!EnchantUtils.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!EnchantUtils.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.func_234616_v_();
        LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
        int tempoTheftLevel = EnchantUtils.enchantmentTagToLevel(arrow, RangedEnchantmentList.TEMPO_THEFT);
        boolean uniqueWeaponFlag = arrow.getTags().contains("NocturnalBow");
        if(tempoTheftLevel > 0 || uniqueWeaponFlag){
            if(uniqueWeaponFlag) tempoTheftLevel++;
            AbilityUtils.stealSpeedFromTarget(shooter, victim, tempoTheftLevel);
        }
    }
}
