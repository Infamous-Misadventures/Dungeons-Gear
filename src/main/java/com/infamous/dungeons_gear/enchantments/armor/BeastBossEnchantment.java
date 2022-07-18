package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.BeastEnchantment;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Minion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class BeastBossEnchantment extends BeastEnchantment {
    public BeastBossEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onBeastDamage(LivingDamageEvent event){
        LivingEntity target = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity trueSource = source.getEntity();
        if(trueSource == null) return;

        if(trueSource.level instanceof ServerLevel
                && MinionMasterHelper.isMinionEntity(trueSource)){
            Minion attackerSummonableCap = MinionMasterHelper.getMinionCapability(trueSource);
            if(attackerSummonableCap == null) return;

            LivingEntity beastOwner = attackerSummonableCap.getMaster();
            if(beastOwner != null){
                if(!MinionMasterHelper.isMinionOf(target, beastOwner)){
                    int beastBossLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.BEAST_BOSS, beastOwner);
                    if(beastBossLevel > 0){
                        float beastBossFactor = (float) (DungeonsGearConfig.BEAST_BOSS_BASE_MULTIPLIER.get() + (DungeonsGearConfig.BEAST_BOSS_MULTIPLIER_PER_LEVEL.get()  * beastBossLevel));
                        float currentDamage = event.getAmount();
                        float newDamage = currentDamage * beastBossFactor;
                        event.setAmount(newDamage);
                    }
                }
            }
        }
    }

}
