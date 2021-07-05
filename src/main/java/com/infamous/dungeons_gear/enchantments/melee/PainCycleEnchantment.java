package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class PainCycleEnchantment extends DungeonsEnchantment {

    public PainCycleEnchantment() {
        super(Rarity.COMMON, ModEnchantmentTypes.MELEE, ModEnchantmentTypes.WEAPON_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPainfulAttack(LivingDamageEvent event){
        if(PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(event.getEntityLiving().world.isRemote) return;

        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            ICombo comboCap = CapabilityHelper.getComboCapability(attacker);
            if(comboCap != null){
                int painCycleLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.PAIN_CYCLE, mainhand);
                int painDamage = 2;
                if(painCycleLevel > 0 && attacker.getHealth() > painDamage){
                    attacker.attackEntityFrom(DamageSource.MAGIC, painDamage); // 1 heart of damage
                    comboCap.setPainCycleStacks(comboCap.getPainCycleStacks() + 1);
                    if(comboCap.getPainCycleStacks() >= 5){
                        int painCycleMultiplier = 2 + painCycleLevel;
                        comboCap.setPainCycleStacks(0);
                        float currentDamage = event.getAmount();
                        event.setAmount(currentDamage * painCycleMultiplier);
                    }
                } else{
                    comboCap.setPainCycleStacks(0);
                }
            }
        }
    }
}
