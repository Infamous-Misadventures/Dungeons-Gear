package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.integration.curios.CuriosIntegration;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.BAG_OF_SOULS;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.COOLDOWN;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.*;

public class CooldownEnchantment extends DungeonsEnchantment {
    private final static Map<EquipmentSlot, UUID> EQUIPMENT_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.HEAD, UUID.fromString("bf62ed9a-6c97-4ecd-a5be-230c788b1569")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.CHEST, UUID.fromString("1231b1d0-839a-488d-bafd-c840a650ae8f")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.LEGS, UUID.fromString("978c008f-ca08-47bd-a00a-2d6a021b4006")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.FEET, UUID.fromString("dc47e161-9d69-415a-a00e-75e9a161f9d4")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.MAINHAND, UUID.fromString("333ffc0c-faac-4172-bad6-41162121549b")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.OFFHAND, UUID.fromString("a2d4d691-41b1-4575-85fb-a36c16bdf741"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
    private final static Map<Integer, UUID> CURIO_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(0, UUID.fromString("7d3b3ddd-e905-464a-9aff-89b92ae7e940")),
                    new AbstractMap.SimpleImmutableEntry<>(1, UUID.fromString("a4fd40a0-b088-4b68-b3d7-62b8eaac3b48")),
                    new AbstractMap.SimpleImmutableEntry<>(2, UUID.fromString("1d537c8c-4074-44c8-92a4-605737e369d5"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

    public CooldownEnchantment() {
        super(Rarity.RARE,  EnchantmentCategory.ARMOR_HEAD, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        removeAttribute(event.getFrom(), event.getEntityLiving(), EQUIPMENT_ATTRIBUTE_UUID_MAP.get(event.getSlot()));
        addAttribute(event.getTo(), event.getEntityLiving(), EQUIPMENT_ATTRIBUTE_UUID_MAP.get(event.getSlot()));
    }

    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        if(!event.getIdentifier().equals(CuriosIntegration.ARTIFACT_IDENTIFIER)) return;
        removeAttribute(event.getFrom(), event.getEntityLiving(), CURIO_ATTRIBUTE_UUID_MAP.get(event.getSlotIndex()));
        addAttribute(event.getTo(), event.getEntityLiving(), CURIO_ATTRIBUTE_UUID_MAP.get(event.getSlotIndex()));
    }

    private static void removeAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        if (EnchantmentHelper.getItemEnchantmentLevel(COOLDOWN, itemStack) > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(ARTIFACT_COOLDOWN_MULTIPLIER.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) != null) {
                attributeInstance.removeModifier(attributeModifierUUID);
            }
        }
    }

    private static void addAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(COOLDOWN, itemStack);
        if (itemEnchantmentLevel > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(ARTIFACT_COOLDOWN_MULTIPLIER.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) == null) {
                attributeInstance.addTransientModifier(new AttributeModifier(attributeModifierUUID, "Enchantment Cooldown", -0.1*itemEnchantmentLevel, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
    }
}
