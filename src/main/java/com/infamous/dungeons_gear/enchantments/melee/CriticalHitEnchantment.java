package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.WeaponList.HAWKBRAND;
import static com.infamous.dungeons_gear.items.WeaponList.MASTERS_KATANA;

@Mod.EventBusSubscriber(modid= MODID)
public class CriticalHitEnchantment extends DamageBoostEnchantment {

    public CriticalHitEnchantment() {
        super(Enchantment.Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.COMMON.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }

    @SubscribeEvent
    public static void onVanillaNonCriticalHit(CriticalHitEvent event){
        if(event.getPlayer() != null && !event.isVanillaCritical()){
            PlayerEntity attacker = (PlayerEntity) event.getPlayer();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            boolean uniqueWeaponFlag = mainhand.getItem() == HAWKBRAND || mainhand.getItem() == MASTERS_KATANA;
            if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.CRITICAL_HIT)){
                int criticalHitLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.CRITICAL_HIT, mainhand);
                float criticalHitChance;
                criticalHitChance = 0.5F + criticalHitLevel * 0.05F;
                float criticalHitRand = attacker.getRNG().nextFloat();
                if(criticalHitRand <= criticalHitChance){
                    event.setResult(Event.Result.ALLOW);
                    float newDamageModifier = event.getDamageModifier() == event.getOldDamageModifier() ? event.getDamageModifier() + 1.5F : event.getDamageModifier() * 3.0F;
                    event.setDamageModifier(newDamageModifier);
                }
            }
            if(uniqueWeaponFlag){
                float criticalHitRand = attacker.getRNG().nextFloat();
                if(criticalHitRand <= 0.1F){
                    event.setResult(Event.Result.ALLOW);
                    float newDamageModifier = event.getDamageModifier() == event.getOldDamageModifier() ? event.getDamageModifier() + 1.5F : event.getDamageModifier() * 3.0F;
                    event.setDamageModifier(newDamageModifier);
                }
            }
        }
    }
}
