package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.FocusEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class PoisonFocusEnchantment extends FocusEnchantment {

    public PoisonFocusEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_HEAD, ARMOR_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPoisonAttack(LivingDamageEvent event){
        if(event.getSource() != DamageSource.MAGIC) return; // Poison effect applies this specific damage source
        if(event.getEntityLiving().level.isClientSide) return;

        LivingEntity victim = event.getEntityLiving();
        if(victim.getEffect(Effects.POISON) != null){
            LivingEntity attacker = victim.getKillCredit();
            if(attacker != null){
                int poisonFocusLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.POISON_FOCUS, attacker);
                if(poisonFocusLevel > 0){
                    float multiplier = 1 + (float) (DungeonsGearConfig.FOCUS_MULTIPLIER_PER_LEVEL.get() * poisonFocusLevel);
                    float currentDamage = event.getAmount();
                    event.setAmount(currentDamage * multiplier);
                }
            }
        }
    }
}
