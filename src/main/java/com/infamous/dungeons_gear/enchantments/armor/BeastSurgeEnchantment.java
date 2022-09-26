package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.BeastEnchantment;
import com.infamous.dungeons_libraries.capabilities.minionmaster.Master;
import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class BeastSurgeEnchantment extends BeastEnchantment {
    public BeastSurgeEnchantment() {
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
    public static void onPlayerUsedHealthPotion(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof Player)) return;
        Player player = (Player) event.getEntityLiving();
        if(player.isAlive() && player.level instanceof ServerLevel){
            ServerLevel serverWorld = (ServerLevel) player.level;
            List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if(potionEffects.isEmpty()) return;
            if(potionEffects.get(0).getEffect() == MobEffects.HEAL){
                int beastSurgeLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.BEAST_SURGE, player);
                if(beastSurgeLevel > 0){
                    Master summonerCap = MinionMasterHelper.getMasterCapability(player);
                    if(summonerCap == null) return;

                    for(Entity summonedMob : summonerCap.getSummonedMobs()){
                        if(summonedMob instanceof LivingEntity){
                            LivingEntity summonedMobAsLiving = (LivingEntity) summonedMob;
                            MobEffectInstance surgeSpeed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, DungeonsGearConfig.BEAST_SURGE_DURATION.get(), beastSurgeLevel - 1);
                            summonedMobAsLiving.addEffect(surgeSpeed);
                        }
                    }
                }
            }
        }
    }

}
