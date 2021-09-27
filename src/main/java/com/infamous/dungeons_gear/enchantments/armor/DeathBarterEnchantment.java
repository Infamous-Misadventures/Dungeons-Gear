package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.enchantments.types.IEmeraldsEnchantment;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class DeathBarterEnchantment extends DungeonsEnchantment implements IEmeraldsEnchantment {

    public DeathBarterEnchantment() {
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
    public static void onDeathBarter(LivingDeathEvent event){
        LivingEntity living = event.getEntityLiving();
        PlayerEntity player = null;
        if(living instanceof PlayerEntity){
            player = (PlayerEntity) event.getEntityLiving();
        } else{
            return;
        }

        PlayerInventory playerInventory = player.inventory;
        int totalEmeraldCount = 0;
        List<Integer> emeraldSlotIndices = new ArrayList<>();
        for(int slotIndex = 0; slotIndex < playerInventory.getContainerSize(); slotIndex++){
            ItemStack currentStack = playerInventory.getItem(slotIndex);
            if(currentStack.getItem() == Items.EMERALD){
                totalEmeraldCount += currentStack.getCount();
                emeraldSlotIndices.add(slotIndex);
            }
        }

        int deathBarterLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.DEATH_BARTER, player);
        int emeraldRequirement = 150 - Math.max(100, 50 * (deathBarterLevel - 1)); // will always need at least 50 emeralds even if the level exceeds 3
        if(deathBarterLevel > 0 && totalEmeraldCount >= emeraldRequirement){

            for(Integer slotIndex : emeraldSlotIndices){
                if(emeraldRequirement > 0){
                    ItemStack currentEmeraldStack = playerInventory.getItem(slotIndex);
                    int currentEmeraldCount = currentEmeraldStack.getCount();
                    if(currentEmeraldCount >= emeraldRequirement){
                        currentEmeraldStack.setCount(currentEmeraldCount - emeraldRequirement);
                        emeraldRequirement = 0;
                    } else{
                        currentEmeraldStack.setCount(0);
                        emeraldRequirement -= currentEmeraldCount;
                    }
                } else{
                    break;
                }
            }

            event.setCanceled(true);
            event.getEntityLiving().setHealth(1.0F);
            event.getEntityLiving().removeAllEffects();
            event.getEntityLiving().addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
            event.getEntityLiving().addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 900, 1));
            event.getEntityLiving().addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
        }
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get()
                || !(enchantment instanceof IEmeraldsEnchantment);
    }
}
