package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.BeastEnchantment;
import com.infamous.dungeons_libraries.capabilities.minionmaster.IMinion;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class BeastBossEnchantment extends BeastEnchantment {
    public BeastBossEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, ARMOR_SLOT);
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

        if(trueSource.level instanceof ServerWorld
                && MinionMasterHelper.isMinionEntity(trueSource)){
            IMinion attackerSummonableCap = MinionMasterHelper.getMinionCapability(trueSource);
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
