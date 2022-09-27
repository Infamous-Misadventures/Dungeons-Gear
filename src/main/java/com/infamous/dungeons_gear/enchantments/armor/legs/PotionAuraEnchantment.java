package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class PotionAuraEnchantment extends DungeonsEnchantment {

    public PotionAuraEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_LEGS, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPlayerDamaged(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if(player.isAlive()){
            List<EffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if(potionEffects.isEmpty()) return;
            EffectInstance effectInstance = potionEffects.get(0);
            if(effectInstance.getEffect() == Effects.HEAL){
                if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.POTION_AURA)){
                    int potionBarrierLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.POTION_AURA, player);
                    AreaOfEffectHelper.healNearbyAllies(player, effectInstance, 6.0F*potionBarrierLevel);
                }
            }
        }
    }
}
