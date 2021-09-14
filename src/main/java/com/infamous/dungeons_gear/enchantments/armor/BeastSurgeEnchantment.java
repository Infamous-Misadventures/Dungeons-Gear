package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.BeastEnchantment;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class BeastSurgeEnchantment extends BeastEnchantment {
    public BeastSurgeEnchantment() {
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
    public static void onPlayerUsedHealthPotion(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if(player.isAlive() && player.world instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld) player.world;
            List<EffectInstance> potionEffects = PotionUtils.getEffectsFromStack(event.getItem());
            if(potionEffects.isEmpty()) return;
            if(potionEffects.get(0).getPotion() == Effects.INSTANT_HEALTH){
                int beastSurgeLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.BEAST_SURGE, player);
                if(beastSurgeLevel > 0){
                    ISummoner summonerCap = CapabilityHelper.getSummonerCapability(player);
                    if(summonerCap == null) return;

                    for(UUID summonedMobUUID : summonerCap.getSummonedMobs()){
                        Entity summonedMob = serverWorld.getEntityByUuid(summonedMobUUID);
                        if(summonedMob instanceof LivingEntity){
                            LivingEntity summonedMobAsLiving = (LivingEntity) summonedMob;
                            EffectInstance surgeSpeed = new EffectInstance(Effects.SPEED, 10 * 20, (beastSurgeLevel * 3) - 1);
                            summonedMobAsLiving.addPotionEffect(surgeSpeed);
                        }
                    }
                }
            }
        }
    }

}
