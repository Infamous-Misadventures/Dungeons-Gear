package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.RAMPAGING_CHANCE;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.RAMPAGING_DURATION;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class RampagingEnchantment extends DungeonsEnchantment {

    public RampagingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onRampagingKill(LivingDeathEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrow) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            applyEnchantment(attacker);
        }
    }

    @SubscribeEvent
    public static void onRampagingBreak(BlockEvent.BreakEvent event){
        applyEnchantment(event.getPlayer());
    }

    private static void applyEnchantment(LivingEntity attacker) {
        ItemStack mainhand = attacker.getMainHandItem();
        if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.RAMPAGING)){
            int rampagingLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.RAMPAGING, mainhand);
            applyEffect(attacker, rampagingLevel);
        }
    }

    private static void applyEffect(LivingEntity entity, int rampagingLevel) {
        float rampagingRand = entity.getRandom().nextFloat();
        if(rampagingRand <= RAMPAGING_CHANCE.get()) {
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, rampagingLevel * RAMPAGING_DURATION.get(), 4));
        }
    }

}
