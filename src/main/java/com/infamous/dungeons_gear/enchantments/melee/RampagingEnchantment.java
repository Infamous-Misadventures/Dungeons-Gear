package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
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

@Mod.EventBusSubscriber(modid= MODID)
public class RampagingEnchantment extends DungeonsEnchantment {

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
            boolean uniqueWeaponFlag = hasRampagingBuiltIn(mainhand);
            if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.RAMPAGING)){
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

    private static boolean hasRampagingBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasRampagingBuiltIn(mainhand);
    }

}
