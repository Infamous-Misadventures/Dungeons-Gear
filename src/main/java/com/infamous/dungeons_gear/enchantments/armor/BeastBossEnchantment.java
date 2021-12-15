package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.BeastEnchantment;
import com.infamous.dungeons_libraries.capabilities.summoning.IMinion;
import com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper;
import net.minecraft.enchantment.EnchantmentHelper;
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
        Entity trueSource = source.getEntity();
        if(trueSource == null) return;

        if(trueSource.level instanceof ServerWorld
                && MinionMasterHelper.isMinionEntity(trueSource)){
            ServerWorld serverWorld = (ServerWorld) trueSource.level;
            IMinion attackerSummonableCap = MinionMasterHelper.getMinionCapability(trueSource);
            if(attackerSummonableCap == null) return;

            UUID summonerUUID = attackerSummonableCap.getMaster();
            if(summonerUUID != null){
                if(!MinionMasterHelper.isMinionOf(target, summonerUUID)){
                    Entity beastOwner = serverWorld.getEntity(summonerUUID);
                    if(beastOwner instanceof LivingEntity){
                        LivingEntity beastOwnerAsLiving = ((LivingEntity) beastOwner);
                        int beastBossLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.BEAST_BOSS, beastOwnerAsLiving);
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
