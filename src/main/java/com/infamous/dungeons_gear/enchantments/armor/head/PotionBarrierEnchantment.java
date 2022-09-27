package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
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
public class PotionBarrierEnchantment extends DungeonsEnchantment {

    public PotionBarrierEnchantment() {
        super(Rarity.RARE,  EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onDrinkPotion(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof Player)) return;
        Player player = (Player) event.getEntityLiving();
        if(player.isAlive()){
            List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if(potionEffects.isEmpty()) return;
            if(potionEffects.get(0).getEffect() == MobEffects.HEAL){
                if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.POTION_BARRIER)){
                    int potionBarrierLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.POTION_BARRIER, player);
                    int duration = 60 + 20*potionBarrierLevel;
                    MobEffectInstance resistance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 3);
                    player.addEffect(resistance);
                }
            }
        }
    }
}
