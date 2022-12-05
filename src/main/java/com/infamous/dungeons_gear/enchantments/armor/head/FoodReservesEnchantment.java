package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class FoodReservesEnchantment extends DropsEnchantment {

    public FoodReservesEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof DropsEnchantment);
    }

    @SubscribeEvent
    public static void onPlayerUsedHealthPotion(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(player.isAlive()){
            List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if(potionEffects.isEmpty()) return;
            if(potionEffects.get(0).getEffect() == MobEffects.HEAL){
                if(ModEnchantmentHelper.hasEnchantment(player, EnchantmentInit.FOOD_RESERVES.get())){
                    int foodReservesLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FOOD_RESERVES.get(), player);
                    while(foodReservesLevel > 0){
                        ItemStack itemStack = LootTableHelper.generateItemStack((ServerLevel) player.level, player.blockPosition(), new ResourceLocation(MODID, "enchantments/food_reserves"), player.getRandom());
                        ItemEntity foodDrop = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), itemStack);
                        player.level.addFreshEntity(foodDrop);
                        foodReservesLevel--;
                    }
                }
            }
        }
    }
}
