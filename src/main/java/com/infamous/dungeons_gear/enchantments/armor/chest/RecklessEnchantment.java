package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = MODID)
public class RecklessEnchantment extends DungeonsEnchantment {
    private static final UUID RECKLESS = UUID.fromString("c131aecf-3b88-43c9-9df3-16f791077d41");

    public RecklessEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()) {
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(RECKLESS);
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(RECKLESS);
            int recklessLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RECKLESS.get(), player);
            if (recklessLevel > 0) {
                player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier(RECKLESS, "reckless multiplier", DungeonsGearConfig.RECKLESS_MAX_HEALTH_MULTIPLIER.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
                player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(RECKLESS, "reckless multiplier", DungeonsGearConfig.RECKLESS_ATTACK_DAMAGE_BASE_MULTIPLIER.get() + (DungeonsGearConfig.RECKLESS_ATTACK_DAMAGE_MULTIPLIER_PER_LEVEL.get() * recklessLevel), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }
}
