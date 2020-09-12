package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.utilties.AbilityUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.WeaponList.STORMLANDER;

@Mod.EventBusSubscriber(modid = MODID)
public class ThunderingEnchantment extends Enchantment {

    public ThunderingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        float chance = user.getRNG().nextFloat();
        if(chance <=  0.3F){
            AbilityUtils.electrifyNearbyEnemies(user, 5, 5, Integer.MAX_VALUE);
            //AbilityUtils.castLightningBolt(user, (LivingEntity)target);
        }
    }

    @SubscribeEvent
    public static void onStormlanderAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(event.getSource() instanceof ElectricShockDamageSource) return;
        if(!(event.getSource().getTrueSource() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        if((mainhand.getItem() == STORMLANDER)){
            float chance = attacker.getRNG().nextFloat();
            if(chance <=  0.3F){
                AbilityUtils.electrifyNearbyEnemies(attacker, 5, 5, Integer.MAX_VALUE);
                //AbilityUtils.castLightningBolt(attacker, victim);
            }
        }
    }
}
