package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.init.PotionList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class SurpriseGiftEnchantment extends DropsEnchantment {

    public SurpriseGiftEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof DropsEnchantment);
    }

    @SubscribeEvent
    public static void onPotionConsumed(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if(player.isAlive()){
            List<EffectInstance> potionEffects = PotionUtils.getEffectsFromStack(event.getItem());
            if(potionEffects.isEmpty()) return;
            if(potionEffects.get(0).getPotion() == Effects.INSTANT_HEALTH){
                if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.SURPRISE_GIFT)){
                    int surpriseGiftLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.SURPRISE_GIFT, player);
                    float surpriseGiftChance = 0.5F * surpriseGiftLevel;

                    ItemStack strengthPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.SHORT_STRENGTH);
                    ItemStack speedPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.SHORT_SWIFTNESS);
                    ItemStack shadowBrew = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.SHADOW_BREW);
                    ItemStack oakwoodBrew = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.OAKWOOD_BREW);
                    //ItemStack arrowBundle = new ItemStack(Items.ARROW, 10);
                    List<ItemStack> giftList = Arrays.asList(strengthPotion, speedPotion, shadowBrew, oakwoodBrew
                            //,arrowBundle
                    );
                    // TODO: Add more potions

                    while(surpriseGiftChance > 0){
                        float surpriseGiftRand = player.getRNG().nextFloat();
                        if(surpriseGiftRand <= surpriseGiftChance){
                            ItemStack potionToDrop = giftList.get(player.getRNG().nextInt(giftList.size()));
                            ItemEntity surpriseGift = new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), potionToDrop);
                            player.world.addEntity(surpriseGift);
                        }
                        surpriseGiftChance -= 1.0F;
                    }
                }
            }
        }
    }
}
