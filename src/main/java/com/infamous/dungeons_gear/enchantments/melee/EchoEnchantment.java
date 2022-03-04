package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class EchoEnchantment extends AOEDamageEnchantment {

    private static boolean echoing = false;

    public EchoEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onVanillaCriticalHit(CriticalHitEvent event) {
        if(event.getTarget() instanceof LivingEntity) {
            PlayerEntity attacker = (PlayerEntity) event.getPlayer();
            LivingEntity victim = (LivingEntity) event.getTarget();
            ItemStack mainhand = attacker.getMainHandItem();
            if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.ECHO)) {
                int echoLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.ECHO, mainhand);
                float cooldown = Math.max(3, 6 - echoLevel);
                if (echoLevel > 3) {
                    echoLevel -= 3;
                    while (echoLevel > 0) {
                        cooldown /= 2;
                        echoLevel--;
                    }
                }
                echo(attacker, victim, (int) (cooldown * 20));
                // play echo sound, if there was one
                //AreaOfEffectHelper.causeEchoAttack(attacker, victim, attackDamage, 3.0f, echoLevel);
            }
        }
    }

    private static void echo(PlayerEntity user, LivingEntity target, int cooldown) {
        final ICombo comboCap = CapabilityHelper.getComboCapability(user);
        if (user.level.isClientSide) return;
        if (echoing) return;
        if (comboCap.getEchoCooldown() == 0 || comboCap.getEchoCooldown() == cooldown) {
            user.attackStrengthTicker = (int) (user.getAttributeValue(Attributes.ATTACK_SPEED) * comboCap.getCachedCooldown() * 20);
            target.invulnerableTime = 0;
            echoing = true;
            user.attack(target);
            comboCap.setEchoCooldown(cooldown);
            target.invulnerableTime = 0;
            echoing = false;
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()) {
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap == null) return;
            comboCap.setEchoCooldown(Math.max(0, comboCap.getEchoCooldown() - 1));
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }
}
