package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

@Mod.EventBusSubscriber(modid = MODID)
public class EnigmaResonatorEnchantment extends DamageBoostEnchantment {

    public EnigmaResonatorEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onVanillaNonCriticalHit(CriticalHitEvent event) {
        if (event.getPlayer() == null) return;
        PlayerEntity attacker = event.getPlayer();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        boolean uniqueWeaponFlag = hasEnigmaResonatorBuiltIn(mainhand);

        int numSouls = Math.min(attacker.experienceTotal, 50);
        if (!event.isVanillaCritical()) {
            boolean success = false;
            if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.ENIGMA_RESONATOR)) {
                int enigmaResonatorLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.ENIGMA_RESONATOR, mainhand);
                float soulsCriticalBoostChanceCap;
                soulsCriticalBoostChanceCap = 0.1F + 0.05F * enigmaResonatorLevel;
                float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
                if (soulsCriticalBoostRand <= Math.min(numSouls / 50.0, soulsCriticalBoostChanceCap)) {
                    success = true;
                }
            }
            if (uniqueWeaponFlag) {
                float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
                if (soulsCriticalBoostRand <= Math.min(numSouls / 50.0, 0.15F)) {
                    success = true;
                }
            }
            if (success) {
                event.setResult(Event.Result.ALLOW);
                float newDamageModifier = event.getDamageModifier() == event.getOldDamageModifier() && !(mainhand.getItem() instanceof IComboWeapon) ? event.getDamageModifier() + 1.5F : event.getDamageModifier() * 3.0F;
                event.setDamageModifier(newDamageModifier);
                // soul particles
                PROXY.spawnParticles(attacker, ParticleTypes.SOUL);
            }
        }
    }

    private static boolean hasEnigmaResonatorBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasEnigmaResonatorBuiltIn(mainhand);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }
}
