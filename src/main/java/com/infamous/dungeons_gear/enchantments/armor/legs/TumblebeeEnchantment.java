package com.infamous.dungeons_gear.enchantments.armor.legs;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import com.infamous.dungeons_libraries.integration.curios.CuriosIntegration;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.TUMBLEBEE;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SUMMON_CAP;

@Mod.EventBusSubscriber(modid = MODID)
public class TumblebeeEnchantment extends JumpingEnchantment {
    private final static Map<EquipmentSlot, UUID> EQUIPMENT_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.HEAD, UUID.fromString("350050cf-ab03-4320-8792-21592e61ef6b")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.CHEST, UUID.fromString("55f68b68-139a-4d92-b8d3-54e0d6988f83")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.LEGS, UUID.fromString("d161bb99-0405-4546-a3ae-0e56076071d4")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.FEET, UUID.fromString("f8b6b2a9-77b5-4105-8e94-9d8d9f15670b")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.MAINHAND, UUID.fromString("fe856449-11ee-45f0-98a9-e2aa41796fe3")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.OFFHAND, UUID.fromString("5c4b8b7d-5252-40d4-a263-4f485512b734"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
    private final static Map<Integer, UUID> CURIO_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(0, UUID.fromString("ab82623a-99e9-4072-85ff-3c72e9b4c55e")),
                    new AbstractMap.SimpleImmutableEntry<>(1, UUID.fromString("83c34148-4ff4-4eea-83e3-02642d408fcf")),
                    new AbstractMap.SimpleImmutableEntry<>(2, UUID.fromString("d802da75-d195-415a-89ad-08fc7e5aedbf"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

    public TumblebeeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof JumpingEnchantment);
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
        if (EnchantmentHelper.getItemEnchantmentLevel(TUMBLEBEE, itemStack) > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(SUMMON_CAP.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) != null) {
                attributeInstance.removeModifier(attributeModifierUUID);
            }
        }
    }

    private static void addAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(TUMBLEBEE, itemStack);
        if (itemEnchantmentLevel > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(SUMMON_CAP.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) == null) {
                attributeInstance.addTransientModifier(new AttributeModifier(attributeModifierUUID, "Enchantment tumblebee", 3, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
