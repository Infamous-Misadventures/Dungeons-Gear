package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.items.artifacts.HarpoonQuiverItem;
import com.infamous.dungeons_gear.items.artifacts.ThunderingQuiverItem;
import com.infamous.dungeons_gear.items.artifacts.TormentQuiverItem;
import com.infamous.dungeons_gear.utilties.*;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class HarpoonShotEnchantment extends DungeonsEnchantment {

    public HarpoonShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @SubscribeEvent
    public static void onArrowJoinWorld(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof AbstractArrowEntity && !event.getWorld().isClientSide()){
            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getEntity();
            if(arrowEntity.getOwner() instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) arrowEntity.getOwner();
                EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.HARPOON_SHOT, livingEntity);
                arrowEntity.addTag(HarpoonQuiverItem.HARPOON_QUIVER);
                arrowEntity.setDeltaMovement(arrowEntity.getDeltaMovement().scale(1.5D));
                arrowEntity.setPierceLevel((byte) (arrowEntity.getPierceLevel() + 1));
            }
        }
    }
}
