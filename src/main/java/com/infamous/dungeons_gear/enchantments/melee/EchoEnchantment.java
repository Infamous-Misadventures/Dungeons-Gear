package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class EchoEnchantment extends AOEDamageEnchantment {

    private static boolean echoing = false;

    public EchoEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onVanillaCriticalHit(CriticalHitEvent event) {
        if(event.getTarget() instanceof LivingEntity && !event.getPlayer().level.isClientSide()
                && (event.getResult() == Event.Result.ALLOW || (event.getResult() == Event.Result.DEFAULT && event.isVanillaCritical()))) {
            Player attacker = (Player) event.getPlayer();
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

    private static void echo(Player user, LivingEntity target, int cooldown) {
        final Combo comboCap = ComboHelper.getComboCapability(user);
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
        Player player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()) {
            Combo comboCap = ComboHelper.getComboCapability(player);
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
