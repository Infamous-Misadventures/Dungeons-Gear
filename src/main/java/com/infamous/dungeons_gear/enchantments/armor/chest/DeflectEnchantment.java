package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class DeflectEnchantment extends DungeonsEnchantment {

    public DeflectEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof ProtectionEnchantment);
    }

    @SubscribeEvent
    public static void onDeflectImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if(!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        if(arrow.getPierceLevel()>0) return;
        LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
        if(ModEnchantmentHelper.hasEnchantment(victim, ArmorEnchantmentList.DEFLECT)){
            int deflectLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.DEFLECT, victim);
            double originalDamage = arrow.getBaseDamage();
            double deflectChance;
            deflectChance = deflectLevel * DungeonsGearConfig.DEFLECT_CHANCE_PER_LEVEL.get();
            float deflectRand = victim.getRandom().nextFloat();
            if(deflectRand <= deflectChance){
                event.setCanceled(true);
                arrow.setBaseDamage(originalDamage * 0.5D);
                arrow.yRot += 180.0F;
                arrow.yRotO += 180.0F;
            }
        }
    }
}
