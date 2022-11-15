package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.BeastEnchantment;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Minion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class BeastBossEnchantment extends BeastEnchantment {
    public BeastBossEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
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
