package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.items.artifacts.HarpoonQuiverItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class HarpoonShotEnchantment extends DungeonsEnchantment {

    public HarpoonShotEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
            EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @SubscribeEvent
    public static void onArrowJoinWorld(EntityJoinLevelEvent event){
        if(event.getEntity() instanceof AbstractArrow && !event.getLevel().isClientSide()){
            AbstractArrow arrowEntity = (AbstractArrow) event.getEntity();
            if(arrowEntity.getOwner() instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) arrowEntity.getOwner();
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.HARPOON_SHOT.get(), livingEntity);
                if(enchantmentLevel > 0) {
                    arrowEntity.addTag(HarpoonQuiverItem.HARPOON_QUIVER);
                    arrowEntity.setDeltaMovement(arrowEntity.getDeltaMovement().scale(1.5D));
                    arrowEntity.setPierceLevel((byte) (arrowEntity.getPierceLevel() + enchantmentLevel));
                }
            }
        }
    }
}
