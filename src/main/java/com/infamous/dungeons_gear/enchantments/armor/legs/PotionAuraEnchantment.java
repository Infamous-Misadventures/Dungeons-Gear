package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class PotionAuraEnchantment extends DungeonsEnchantment {

    public PotionAuraEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onPlayerDamaged(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof Player)) return;
        Player player = (Player) event.getEntityLiving();
        if(player.isAlive()){
            List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if(potionEffects.isEmpty()) return;
            MobEffectInstance effectInstance = potionEffects.get(0);
            if(effectInstance.getEffect() == MobEffects.HEAL){
                if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.POTION_AURA)){
                    int potionBarrierLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.POTION_AURA, player);
                    AreaOfEffectHelper.healNearbyAllies(player, effectInstance, 6.0F*potionBarrierLevel);
                }
            }
        }
    }
}
