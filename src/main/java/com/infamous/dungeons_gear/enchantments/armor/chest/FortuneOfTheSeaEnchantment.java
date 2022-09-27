package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.FORTUNE_OF_THE_SEA;
import static net.minecraft.world.entity.ai.attributes.Attributes.LUCK;

public class FortuneOfTheSeaEnchantment extends DungeonsEnchantment {
    private final static Map<EquipmentSlot, UUID> EQUIPMENT_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.HEAD, UUID.fromString("8b9fd184-7732-4a5f-a695-00880275d5dc")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.CHEST, UUID.fromString("5270105c-8be5-4ac1-a98f-681bf43a15ce")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.LEGS, UUID.fromString("95360cfb-ae8f-4cc9-bc5f-6a6bbd040b66")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.FEET, UUID.fromString("c375a5c7-4194-44fe-91c3-1c9eee9c12ed"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

    public FortuneOfTheSeaEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        removeAttribute(event.getFrom(), event.getEntityLiving(), EQUIPMENT_ATTRIBUTE_UUID_MAP.get(event.getSlot()));
        addAttribute(event.getTo(), event.getEntityLiving(), EQUIPMENT_ATTRIBUTE_UUID_MAP.get(event.getSlot()));
    }

    private static void removeAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        if (EnchantmentHelper.getItemEnchantmentLevel(FORTUNE_OF_THE_SEA, itemStack) > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(LUCK);
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) != null) {
                attributeInstance.removeModifier(attributeModifierUUID);
            }
        }
    }

    private static void addAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(FORTUNE_OF_THE_SEA, itemStack);
        if (itemEnchantmentLevel > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(LUCK);
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) == null) {
                attributeInstance.addTransientModifier(new AttributeModifier(attributeModifierUUID, "Enchantment Fortune of the Sea", 1, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
