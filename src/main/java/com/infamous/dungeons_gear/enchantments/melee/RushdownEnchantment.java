package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
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

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class RushdownEnchantment extends DungeonsEnchantment {

    public RushdownEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onRushdownKill(LivingDeathEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            ItemStack mainhand = attacker.getMainHandItem();
            if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.RUSHDOWN)){
                int rushdownLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.RUSHDOWN, mainhand);
                EffectInstance speed = new EffectInstance(Effects.MOVEMENT_SPEED, rushdownLevel * 20, 4);
                attacker.addEffect(speed);
            }
        }
    }

}
