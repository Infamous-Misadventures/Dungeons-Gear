package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;

import com.infamous.dungeons_gear.integration.curios.CuriosIntegration;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.BAG_OF_SOULS;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.FORTUNE_OF_THE_SEA;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_CAP;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;
import static net.minecraft.entity.ai.attributes.Attributes.LUCK;

public class FortuneOfTheSeaEnchantment extends DungeonsEnchantment {
    private final static Map<EquipmentSlotType, UUID> EQUIPMENT_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.HEAD, UUID.fromString("8b9fd184-7732-4a5f-a695-00880275d5dc")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.CHEST, UUID.fromString("5270105c-8be5-4ac1-a98f-681bf43a15ce")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.LEGS, UUID.fromString("95360cfb-ae8f-4cc9-bc5f-6a6bbd040b66")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.FEET, UUID.fromString("c375a5c7-4194-44fe-91c3-1c9eee9c12ed"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

    public FortuneOfTheSeaEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, ModEnchantmentTypes.ARMOR_SLOT);
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
            ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(LUCK);
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) != null) {
                attributeInstance.removeModifier(attributeModifierUUID);
            }
        }
    }

    private static void addAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(FORTUNE_OF_THE_SEA, itemStack);
        if (itemEnchantmentLevel > 0) {
            ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(LUCK);
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) == null) {
                attributeInstance.addTransientModifier(new AttributeModifier(attributeModifierUUID, "Enchantment Fortune of the Sea", 1, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
