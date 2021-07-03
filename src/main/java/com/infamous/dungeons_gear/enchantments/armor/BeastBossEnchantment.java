package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.SummoningHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.BeastEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class BeastBossEnchantment extends BeastEnchantment {
    public BeastBossEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onBeastDamage(LivingDamageEvent event){
        LivingEntity target = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity trueSource = source.getTrueSource();
        if(trueSource == null) return;

        if(trueSource.world instanceof ServerWorld
                && SummoningHelper.isEntitySummonable(trueSource)){
            ServerWorld serverWorld = (ServerWorld) trueSource.world;
            ISummonable attackerSummonableCap = CapabilityHelper.getSummonableCapability(trueSource);
            if(attackerSummonableCap == null) return;

            UUID summonerUUID = attackerSummonableCap.getSummoner();
            if(summonerUUID != null){
                if(!SummoningHelper.wasSummonedBy(target, summonerUUID)){
                    Entity beastOwner = serverWorld.getEntityByUuid(summonerUUID);
                    if(beastOwner instanceof LivingEntity){
                        LivingEntity beastOwnerAsLiving = ((LivingEntity) beastOwner);
                        int beastBossLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.BEAST_BOSS, beastOwnerAsLiving);
                        if(beastBossLevel > 0){
                            float beastBossFactor = 0.1F + (0.1F * beastBossLevel);
                            float currentDamage = event.getAmount();
                            float newDamage = currentDamage * beastBossFactor;
                            event.setAmount(newDamage);
                        }
                    }
                }
            }
        }
    }

}
