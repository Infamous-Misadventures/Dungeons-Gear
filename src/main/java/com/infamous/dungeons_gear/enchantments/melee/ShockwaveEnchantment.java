package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

@Mod.EventBusSubscriber(modid = MODID)
public class ShockwaveEnchantment extends AOEDamageEnchantment {

    public static final float SHOCKWAVE_DAMAGE_MULTIPLIER = 0.25F;

    public ShockwaveEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onVanillaCriticalHit(CriticalHitEvent event) {
        if (event.getEntity() != null && !event.getEntity().level.isClientSide() && event.getTarget() instanceof LivingEntity
                && (event.getResult() == Event.Result.ALLOW || (event.getResult() == Event.Result.DEFAULT && event.isVanillaCritical()))
        ) {
            Player attacker = event.getEntity();
            LivingEntity victim = (LivingEntity) event.getTarget();
            ItemStack mainhand = attacker.getMainHandItem();
            if (event.getResult() != Event.Result.ALLOW && mainhand.getItem() instanceof IDualWieldWeapon) return;
            if (attacker.getLastHurtMobTimestamp() == attacker.tickCount) return;
            if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.SHOCKWAVE.get())) {
                int shockwaveLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.SHOCKWAVE.get(), mainhand);
                // gets the attack damage of the original attack before any enchantment modifiers are added
                float attackDamage = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
                Combo ic = ComboHelper.getComboCapability(attacker);
                float cooledAttackStrength = ic == null ? 1 : ic.getCachedCooldown();
                attackDamage *= 0.2F + cooledAttackStrength * cooledAttackStrength * 0.8F;

                float shockwaveDamage = 4;//attackDamage * SHOCKWAVE_DAMAGE_MULTIPLIER;
                shockwaveDamage *= (shockwaveLevel + 1) / 2.0F;
                SoundHelper.playBoltImpactSound(attacker);
                AOECloudHelper.spawnCritCloud(attacker, victim, 3.0f);
                AreaOfEffectHelper.causeShockwave(attacker, victim, shockwaveDamage, 6.0f);
            }
        }
    }

}
