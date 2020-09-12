package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.enchantments.*;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.utilties.AbilityUtils;
import com.infamous.dungeons_gear.utilties.EnchantUtils;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.items.WeaponList.MOON_DAGGER;
import static com.infamous.dungeons_gear.items.WeaponList.SOUL_FIST;

@Mod.EventBusSubscriber(modid= MODID)
public class EnigmaResonatorEnchantment extends DamageBoostEnchantment {

    public EnigmaResonatorEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment);
    }

    @SubscribeEvent
    public static void onVanillaNonCriticalHit(CriticalHitEvent event){
        if(event.getPlayer() == null) return;
        PlayerEntity attacker = event.getPlayer();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        boolean uniqueWeaponFlag = mainhand.getItem() == SOUL_FIST
                || mainhand.getItem() == MOON_DAGGER;

        int numSouls = Math.min(attacker.experienceTotal, 50);
        if(!event.isVanillaCritical()){
            if(EnchantUtils.hasEnchantment(mainhand, MeleeRangedEnchantmentList.ENIGMA_RESONATOR)){
                int enigmaResonatorLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.ENIGMA_RESONATOR, mainhand);
                float soulsCriticalBoostChanceCap = 0;
                if(enigmaResonatorLevel == 1) soulsCriticalBoostChanceCap = 0.15F;
                if(enigmaResonatorLevel == 2) soulsCriticalBoostChanceCap = 0.2F;
                if(enigmaResonatorLevel == 3) soulsCriticalBoostChanceCap = 0.25F;
                float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
                if(soulsCriticalBoostRand <= Math.min(numSouls/50.0, soulsCriticalBoostChanceCap)){
                    event.setResult(Event.Result.ALLOW);
                    float newDamageModifier = event.getDamageModifier() == event.getOldDamageModifier() ? event.getDamageModifier() + 1.5F : event.getDamageModifier() * 3.0F;
                    event.setDamageModifier(newDamageModifier);
                    // soul particles
                    PROXY.spawnParticles(attacker, ParticleTypes.field_239812_C_);
                }
            }
            if(uniqueWeaponFlag){
                float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
                if(soulsCriticalBoostRand <= Math.min(numSouls/50.0, 0.15F)){
                    event.setResult(Event.Result.ALLOW);
                    float newDamageModifier = event.getDamageModifier() == event.getOldDamageModifier() ? event.getDamageModifier() + 1.5F : event.getDamageModifier() * 3.0F;
                    event.setDamageModifier(newDamageModifier);
                    // soul particles
                    PROXY.spawnParticles(attacker, ParticleTypes.field_239812_C_);
                }
            }
        }
    }
}
