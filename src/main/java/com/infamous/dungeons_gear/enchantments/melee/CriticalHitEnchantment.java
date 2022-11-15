package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.items.interfaces.IComboWeapon;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

//ToDo: WTF IS UP WITH COMBOWEAPONS? Why so different?
@Mod.EventBusSubscriber(modid = MODID)
public class CriticalHitEnchantment extends DamageBoostEnchantment {

    public CriticalHitEnchantment() {
        super(Enchantment.Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onVanillaNonCriticalHit(CriticalHitEvent event) {
        if (event.getPlayer() != null && !event.isVanillaCritical()) {
            Player attacker = event.getPlayer();
            ItemStack mainhand = attacker.getMainHandItem();
            boolean success = false;
            if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.CRITICAL_HIT)) {
                int criticalHitLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.CRITICAL_HIT, mainhand);
                float criticalHitChance;
                criticalHitChance = 0.05F + criticalHitLevel * 0.05F;
                float criticalHitRand = attacker.getRandom().nextFloat();
                if (criticalHitRand <= criticalHitChance) {
                    success = true;
                }
            }
            if (success) {
                event.setResult(Event.Result.ALLOW);
                float newDamageModifier = event.getDamageModifier() == event.getOldDamageModifier() && !(mainhand.getItem() instanceof IDualWieldWeapon) ? event.getDamageModifier() + 1.5F : event.getDamageModifier() * 3.0F;
                event.setDamageModifier(newDamageModifier);
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }
}
