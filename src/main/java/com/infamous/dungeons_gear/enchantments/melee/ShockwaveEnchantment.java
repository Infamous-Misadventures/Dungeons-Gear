package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IComboWeapon;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
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

@Mod.EventBusSubscriber(modid= MODID)
public class ShockwaveEnchantment extends AOEDamageEnchantment {

    public static final float SHOCKWAVE_DAMAGE_MULTIPLIER = 0.25F;

    public ShockwaveEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
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

    @SubscribeEvent
    public static void onVanillaCriticalHit(CriticalHitEvent event){
        if(event.getPlayer() != null && event.getTarget() instanceof LivingEntity
                && (event.getResult() == Event.Result.ALLOW || (event.getResult() == Event.Result.DEFAULT && event.isVanillaCritical()))
        ){
            PlayerEntity attacker = (PlayerEntity) event.getPlayer();
            LivingEntity victim = (LivingEntity) event.getTarget();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            if (event.getResult() != Event.Result.ALLOW && mainhand.getItem() instanceof IComboWeapon) return;
            if(attacker.getLastAttackedEntityTime()==attacker.ticksExisted)return;
            boolean uniqueWeaponFlag = hasShockwaveBuiltIn(mainhand);
            if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.SHOCKWAVE) || uniqueWeaponFlag){
                int shockwaveLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.SHOCKWAVE, mainhand);
                if(uniqueWeaponFlag) shockwaveLevel += 1;
                // gets the attack damage of the original attack before any enchantment modifiers are added
                float attackDamage = (float)attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
                ICombo ic = CapabilityHelper.getComboCapability(attacker);
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

    private static boolean hasShockwaveBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasShockwaveBuiltIn(mainhand);
    }
}
