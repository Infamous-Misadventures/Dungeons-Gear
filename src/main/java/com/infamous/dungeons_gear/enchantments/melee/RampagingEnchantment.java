package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.utilties.EnchantUtils;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.WeaponList.DANCERS_SWORD;
import static com.infamous.dungeons_gear.items.WeaponList.MAULER;

@Mod.EventBusSubscriber(modid= MODID)
public class RampagingEnchantment extends Enchantment {

    public RampagingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onRampagingKill(LivingDeathEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            boolean uniqueWeaponFlag = mainhand.getItem() == DANCERS_SWORD || mainhand.getItem() == MAULER;
            if(EnchantUtils.hasEnchantment(mainhand, MeleeEnchantmentList.RAMPAGING)){
                int rampagingLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.RAMPAGING, mainhand);
                float rampagingRand = attacker.getRNG().nextFloat();
                if(rampagingRand <= 0.1F) {
                    EffectInstance rampage = new EffectInstance(Effects.HASTE, rampagingLevel * 100, 4);
                    attacker.addPotionEffect(rampage);
                }
            }
            if(uniqueWeaponFlag){
                float rampagingRand = attacker.getRNG().nextFloat();
                if(rampagingRand <= 0.1F) {
                    EffectInstance rampage = new EffectInstance(Effects.HASTE, 100, 4);
                    attacker.addPotionEffect(rampage);
                }
            }
        }
    }

}
