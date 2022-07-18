package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.stream.StreamSupport;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.ARROW_HOARDER;
import static com.infamous.dungeons_gear.registry.ItemRegistry.ARROW_BUNDLE;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class ArrowHoarderEnchantment extends JumpingEnchantment {

    public ArrowHoarderEnchantment() {
        super(Rarity.UNCOMMON, ModEnchantmentTypes.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArrowDrop(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntityLiving();
            int maxLevel = EnchantmentHelper.getEnchantmentLevel(ARROW_HOARDER, attacker);
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
