package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class SoulFocusEnchantment extends FocusEnchantment {

    public SoulFocusEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onMagicAttack(LivingDamageEvent event) {
        if (!isIndirectMagic(event.getSource())) return; // we don't want this to trigger via generic magic damage
        if (event.getEntity().level.isClientSide) return;

        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            int soulFocusLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SOUL_FOCUS.get(), attacker);
            if (soulFocusLevel > 0) {
                float multiplier = 1 + (float) (DungeonsGearConfig.FOCUS_MULTIPLIER_PER_LEVEL.get() * soulFocusLevel);
                float currentDamage = event.getAmount();
                event.setAmount(currentDamage * multiplier);
            }
        }
    }

    private static boolean isIndirectMagic(DamageSource damageSource) {
        return damageSource instanceof IndirectEntityDamageSource && damageSource.isMagic();
    }
}
