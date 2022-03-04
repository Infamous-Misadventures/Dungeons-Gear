package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_libraries.items.interfaces.IComboWeapon;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.*;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class SwirlingEnchantment extends AOEDamageEnchantment {

    public static final float SWIRLING_DAMAGE_MULTIPLIER = 0.5F;

    public SwirlingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @SubscribeEvent
    public static void onVanillaCriticalHit(CriticalHitEvent event) {
        if (event.getPlayer() != null && event.getTarget() instanceof LivingEntity
                && (event.getResult() == Event.Result.ALLOW || (event.getResult() == Event.Result.DEFAULT && event.isVanillaCritical()))
        ) {
            PlayerEntity attacker = (PlayerEntity) event.getPlayer();
            LivingEntity victim = (LivingEntity) event.getTarget();
            if (attacker.getLastHurtMobTimestamp() == attacker.tickCount) return;
            ItemStack mainhand = attacker.getMainHandItem();
            if (event.getResult() != Event.Result.ALLOW && mainhand.getItem() instanceof IComboWeapon) return;
            if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.SWIRLING)) {
                int swirlingLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.SWIRLING, mainhand);
                // gets the attack damage of the original attack before any enchantment modifiers are added
                float attackDamage = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
                ICombo ic = CapabilityHelper.getComboCapability(attacker);
                float cooledAttackStrength = ic == null ? 1 : ic.getCachedCooldown();
                attackDamage *= 0.2F + cooledAttackStrength * cooledAttackStrength * 0.8F;


                float swirlingDamage = attackDamage * SWIRLING_DAMAGE_MULTIPLIER;
                swirlingDamage *= (swirlingLevel + 1) / 2.0F;
                SoundHelper.playAttackSweepSound(attacker);
                AOECloudHelper.spawnCritCloud(attacker, victim, 1.5f);
                AreaOfEffectHelper.causeSwirlingAttack(attacker, victim, swirlingDamage, 3f);
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
