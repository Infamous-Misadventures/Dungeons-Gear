package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.registry.EnchantmentInit.ARROW_HOARDER;
import static com.infamous.dungeons_gear.registry.ItemInit.ARROW_BUNDLE;

@Mod.EventBusSubscriber(modid= MODID)
public class ArrowHoarderEnchantment extends JumpingEnchantment {

    public ArrowHoarderEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArrowDrop(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntity();
            int maxLevel = EnchantmentHelper.getEnchantmentLevel(ARROW_HOARDER.get(), attacker);
            int drops = (maxLevel / 4);
            drops += attacker.getRandom().nextFloat() <= (maxLevel % 4) / 4.0F ? 1 : 0;
            Collection<ItemEntity> itemEntities = event.getDrops();
            if (drops > 0 && victim instanceof Enemy && itemEntities.stream().anyMatch(itemEntity -> itemEntity.getItem().getItem().equals(Items.ARROW))) {
                ItemEntity arrowDrop = new ItemEntity(victim.level, victim.getX(), victim.getY(), victim.getZ(), new ItemStack(ARROW_BUNDLE.get(), drops));
                itemEntities.add(arrowDrop);
            }
        }
    }
}
