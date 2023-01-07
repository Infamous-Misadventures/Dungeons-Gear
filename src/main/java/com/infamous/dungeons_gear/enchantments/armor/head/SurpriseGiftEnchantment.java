package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.items.ArrowBundleItem;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
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
import static com.infamous.dungeons_gear.registry.ItemInit.ARROW_BUNDLE;

@Mod.EventBusSubscriber(modid = MODID)
public class SurpriseGiftEnchantment extends DropsEnchantment {

    public SurpriseGiftEnchantment() {
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
    public static void onPotionConsumed(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (player.isAlive() && player.isEffectiveAi()) {
            List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if (potionEffects.isEmpty()) return;
            if (potionEffects.get(0).getEffect() == MobEffects.HEAL) {
                if (ModEnchantmentHelper.hasEnchantment(player, EnchantmentInit.SURPRISE_GIFT.get())) {
                    int surpriseGiftLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SURPRISE_GIFT.get(), player);
                    float surpriseGiftChance = 0.5F * surpriseGiftLevel;

                    while (surpriseGiftChance > 0) {
                        float surpriseGiftRand = player.getRandom().nextFloat();
                        if (surpriseGiftRand <= surpriseGiftChance) {
                            ItemStack itemStack = LootTableHelper.generateItemStack((ServerLevel) player.level, player.blockPosition(), new ResourceLocation(MODID, "enchantments/surprise_gift"), player.getRandom());
                            if (itemStack.getItem().equals(ARROW_BUNDLE.get())) {
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
