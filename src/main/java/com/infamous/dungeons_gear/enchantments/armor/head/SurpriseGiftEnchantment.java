package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.items.ArrowBundleItem;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.registry.ItemRegistry.ARROW_BUNDLE;

@Mod.EventBusSubscriber(modid= MODID)
public class SurpriseGiftEnchantment extends DropsEnchantment {

    public SurpriseGiftEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_HEAD, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof DropsEnchantment);
    }

    @SubscribeEvent
    public static void onPotionConsumed(LivingEntityUseItemEvent.Finish event){
        if(!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if(player.isAlive() && player.isEffectiveAi()){
            List<EffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if(potionEffects.isEmpty()) return;
            if(potionEffects.get(0).getEffect() == Effects.HEAL){
                if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.SURPRISE_GIFT)){
                    int surpriseGiftLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.SURPRISE_GIFT, player);
                    float surpriseGiftChance = 0.5F * surpriseGiftLevel;

                    while(surpriseGiftChance > 0){
                        float surpriseGiftRand = player.getRandom().nextFloat();
                        if(surpriseGiftRand <= surpriseGiftChance){
                            ItemStack itemStack = LootTableHelper.generateItemStack((ServerWorld) player.level, player.blockPosition(), new ResourceLocation(MODID, "enchantments/surprise_gift"), player.getRandom());
                            if(itemStack.getItem().equals(ARROW_BUNDLE.get())){
                                ArrowBundleItem.changeNumberOfArrows(itemStack, 3);
                            }
                            ItemEntity surpriseGift = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), itemStack);
                            player.level.addFreshEntity(surpriseGift);
                        }
                        surpriseGiftChance -= 1.0F;
                    }
                }
            }
        }
    }
}
