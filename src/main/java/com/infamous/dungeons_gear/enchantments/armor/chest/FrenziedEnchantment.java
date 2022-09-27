package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class FrenziedEnchantment extends HealthAbilityEnchantment {
    private static final UUID FRENZY = UUID.fromString("86ded262-f5b3-41f0-a1ca-b881f6abfcff");

    public FrenziedEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, ARMOR_SLOT);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()) {
            float maxHealth = player.getMaxHealth();
            float currentHealth = player.getHealth();
            player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(FRENZY);
            if (currentHealth <= maxHealth / 2) {
                if (ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.FRENZIED)) {
                    int frenziedLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.FRENZIED, player);
                    player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier(FRENZY, "frenzy multiplier", DungeonsGearConfig.FRENZIED_MULTIPLIER_PER_LEVEL.get() * frenziedLevel, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealthAbilityEnchantment);
    }
}
