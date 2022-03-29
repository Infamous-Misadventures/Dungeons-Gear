package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.HealingEnchantment;
import com.infamous.dungeons_gear.integration.curios.CuriosIntegration;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.event.PlayerSoulEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList.ANIMA_CONDUIT;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SOUL_GATHERING;

@Mod.EventBusSubscriber(modid = MODID)
public class AnimaConduitEnchantment extends HealingEnchantment {
    private final static Map<EquipmentSlotType, UUID> EQUIPMENT_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.HEAD, UUID.fromString("8eadc4ca-b5f4-4412-88e0-5d07f5eb79e8")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.CHEST, UUID.fromString("7cf5cebe-d8e3-4a17-9235-c0eed2a63813")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.LEGS, UUID.fromString("35035bf5-cc19-4d8f-9b9b-4d3b303d2cf8")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.FEET, UUID.fromString("29363940-366c-4c0c-bcae-ff01d399235a")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.MAINHAND, UUID.fromString("4c1e2969-701b-4391-8043-c364f88a21ab")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.OFFHAND, UUID.fromString("723452ec-16cf-494e-979b-9518731eacee"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
    private final static Map<Integer, UUID> CURIO_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(0, UUID.fromString("44f6cd12-9cff-4e09-9c27-c3c8acf4d51d")),
                    new AbstractMap.SimpleImmutableEntry<>(1, UUID.fromString("0ed840b4-cdee-4f0b-a087-435ac212c193")),
                    new AbstractMap.SimpleImmutableEntry<>(2, UUID.fromString("a785283e-a3b0-449a-a6dd-891e9a937050"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

    public AnimaConduitEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @SubscribeEvent
    public static void onPickupSoulOrb(PlayerSoulEvent.PickupSoul event){
        PlayerEntity playerEntity = event.getPlayer();
        if(!playerEntity.level.isClientSide() && ModEnchantmentHelper.hasEnchantment(playerEntity, ANIMA_CONDUIT)) {
            int level = EnchantmentHelper.getEnchantmentLevel(ANIMA_CONDUIT, playerEntity);
            playerEntity.heal(level);
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealingEnchantment);
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
        if (EnchantmentHelper.getItemEnchantmentLevel(ANIMA_CONDUIT, itemStack) > 0) {
            ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(SOUL_GATHERING.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) != null) {
                attributeInstance.removeModifier(attributeModifierUUID);
            }
        }
    }

    private static void addAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(ANIMA_CONDUIT, itemStack);
        if (itemEnchantmentLevel > 0) {
            ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(SOUL_GATHERING.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) == null) {
                attributeInstance.addTransientModifier(new AttributeModifier(attributeModifierUUID, "Enchantment anima conduit", 1, AttributeModifier.Operation.ADDITION));
            }
        }
    }

}
