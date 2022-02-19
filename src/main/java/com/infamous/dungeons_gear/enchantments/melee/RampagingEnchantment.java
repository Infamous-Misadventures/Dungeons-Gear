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
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.RAMPAGING_CHANCE;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.RAMPAGING_DURATION;

import net.minecraft.enchantment.Enchantment.Rarity;

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
        if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
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
        boolean uniqueWeaponFlag = hasRampagingBuiltIn(mainhand);
        if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.RAMPAGING)){
            int rampagingLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.RAMPAGING, mainhand);
            applyEffect(attacker, rampagingLevel);
        } else if(uniqueWeaponFlag){
            applyEffect(attacker, 1);
        }
    }

    private static void applyEffect(LivingEntity entity, int rampagingLevel) {
        float rampagingRand = entity.getRandom().nextFloat();
        if(rampagingRand <= RAMPAGING_CHANCE.get()) {
            entity.addEffect(new EffectInstance(Effects.DIG_SPEED, rampagingLevel * RAMPAGING_DURATION.get(), 4));
        }
    }

    private static boolean hasRampagingBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasRampagingBuiltIn(mainhand);
    }

}
