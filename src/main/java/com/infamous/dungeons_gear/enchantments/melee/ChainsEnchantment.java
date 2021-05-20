package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
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

@Mod.EventBusSubscriber(modid = MODID)
public class ChainsEnchantment extends DungeonsEnchantment {

    public ChainsEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        float chance = user.getRNG().nextFloat();
        if(chance <=  0.3F){
            AreaOfEffectHelper.chainNearbyEntities(user, (LivingEntity)target, 1.5F, level);
        }
    }

    @SubscribeEvent
    public static void onChainsAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getTrueSource() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        if(hasChainsBuiltIn(mainhand)){
            float chance = attacker.getRNG().nextFloat();
            if(chance <=  0.3F) {
                AreaOfEffectHelper.chainNearbyEntities(attacker, victim, 1.5F, 1);
            }
        }
    }

    private static boolean hasChainsBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasChainsBuiltIn(mainhand);
    }
}
