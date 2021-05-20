package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class PotionBarrierEnchantment extends DungeonsEnchantment {

    public PotionBarrierEnchantment() {
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
            List<EffectInstance> potionEffects = PotionUtils.getEffectsFromStack(event.getItem());
            if(potionEffects.isEmpty()) return;
            if(potionEffects.get(0).getPotion() == Effects.INSTANT_HEALTH){
                ItemStack chestplate = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                ItemStack helmet = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
                boolean uniqueArmorFlag =
                        hasPotionBarrierBuiltIn(chestplate) || hasPotionBarrierBuiltIn(helmet);
                if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.POTION_BARRIER) || uniqueArmorFlag){
                    int potionBarrierLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.POTION_BARRIER, player);
                    if(uniqueArmorFlag) potionBarrierLevel++;
                    int duration = 60 + 20*potionBarrierLevel;
                    EffectInstance resistance = new EffectInstance(Effects.RESISTANCE, duration, 3);
                    player.addPotionEffect(resistance);
                }
            }
        }
    }
    private static boolean hasPotionBarrierBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IArmor && ((IArmor) stack.getItem()).hasPotionBarrierBuiltIn(stack);
    }
}
