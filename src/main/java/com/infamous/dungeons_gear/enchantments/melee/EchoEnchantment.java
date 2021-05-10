package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
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
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class EchoEnchantment extends AOEDamageEnchantment {

    public EchoEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onVanillaCriticalHit(CriticalHitEvent event) {
        if (event.getPlayer() != null
                && (event.getResult() == Event.Result.ALLOW || (event.getResult() == Event.Result.DEFAULT && event.isVanillaCritical()))
        ) {
            PlayerEntity attacker = (PlayerEntity) event.getPlayer();
            LivingEntity victim = event.getEntityLiving();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            boolean uniqueWeaponFlag = mainhand.getItem() == DeferredItemInit.WHISPERING_SPEAR.get();
            if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.ECHO) || uniqueWeaponFlag) {
                int echoLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.ECHO, mainhand);
                if (uniqueWeaponFlag) echoLevel++;
                // gets the attack damage of the original attack before any enchantment modifiers are added
                float attackDamage = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
                ICombo ic = CapabilityHelper.getComboCapability(attacker);
                float cooledAttackStrength = ic == null ? 1 : ic.getCachedCooldown();
                attackDamage *= 0.2F + cooledAttackStrength * cooledAttackStrength * 0.8F;

                // play echo sound, if there was one
                AreaOfEffectHelper.causeEchoAttack(attacker, victim, attackDamage, 3.0f, echoLevel);
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }
}
