package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class PotionAuraEnchantment extends DungeonsEnchantment {

    public PotionAuraEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
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
