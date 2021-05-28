package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class StunningEnchantment extends DungeonsEnchantment {

    public StunningEnchantment() {
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
        if(chance <=  level * 0.05){
            EffectInstance stunned = new EffectInstance(CustomEffects.STUNNED, 60);
            EffectInstance nausea = new EffectInstance(Effects.NAUSEA, 60);
            EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 60, 5);
            ((LivingEntity)target).addPotionEffect(stunned);
            ((LivingEntity)target).addPotionEffect(nausea);
            ((LivingEntity)target).addPotionEffect(slowness);
        }
    }

    @SubscribeEvent
    public static void onHighlandAxeAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getTrueSource() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        if(hasStunningBuiltIn(mainhand)){
            float chance = attacker.getRNG().nextFloat();
            if(chance <= 0.05) {
                EffectInstance stunned = new EffectInstance(CustomEffects.STUNNED, 60);
                EffectInstance nausea = new EffectInstance(Effects.NAUSEA, 60);
                EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 60, 5);
                victim.addPotionEffect(stunned);
                victim.addPotionEffect(nausea);
                victim.addPotionEffect(slowness);
            }
        }
    }

    private static boolean hasStunningBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasStunningBuiltIn(mainhand);
    }
}
