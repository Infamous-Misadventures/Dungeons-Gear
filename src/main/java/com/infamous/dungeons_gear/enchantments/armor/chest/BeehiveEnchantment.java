package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.integration.curios.CuriosIntegration;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.summon.SummonHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.BEEHIVE;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.DODGE;
import static com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList.BUSY_BEE;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SUMMON_CAP;

@Mod.EventBusSubscriber(modid= MODID)
public class BeehiveEnchantment extends DungeonsEnchantment {
    private final static Map<EquipmentSlotType, UUID> EQUIPMENT_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.HEAD, UUID.fromString("58598d49-1149-4ce7-aef6-6e7969e44235")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.CHEST, UUID.fromString("68ba2242-c0f6-408e-9e79-f1f0395b1943")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.LEGS, UUID.fromString("559c385d-8edd-46c3-ba54-63db9aab61a2")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.FEET, UUID.fromString("a3d550e3-3b9e-46fc-8af3-97fc378d72d6")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.MAINHAND, UUID.fromString("cacb61ba-be22-49a1-8085-d14ef8f4aba1")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlotType.OFFHAND, UUID.fromString("4a1bdf58-4cbb-4607-9711-8e323cf09906"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
    private final static Map<Integer, UUID> CURIO_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(0, UUID.fromString("53f30d3a-e9c1-432b-92d6-2cfbb5bbc5c5")),
                    new AbstractMap.SimpleImmutableEntry<>(1, UUID.fromString("6e91dc8d-bfc8-4dac-891b-433fdb7ed49a")),
                    new AbstractMap.SimpleImmutableEntry<>(2, UUID.fromString("4b51fcfc-ce5e-4577-9496-ea4c7615f4d7"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

    public BeehiveEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }


    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        LivingEntity victim = event.getEntityLiving();
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(BEEHIVE, victim);
        float beehiveChance = (float) (DungeonsGearConfig.BEEHIVE_CHANCE_PER_LEVEL.get() * enchantmentLevel);

        float beehiveHitRand = victim.getRandom().nextFloat();
        if (beehiveHitRand <= beehiveChance) {
            if(SummonHelper.summonEntity(victim, victim.blockPosition(), EntityType.BEE) != null) {
                SoundHelper.playCreatureSound(victim, SoundEvents.BEE_LOOP);
            }
        }
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
        if (EnchantmentHelper.getItemEnchantmentLevel(BEEHIVE, itemStack) > 0) {
            ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(SUMMON_CAP.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) != null) {
                attributeInstance.removeModifier(attributeModifierUUID);
            }
        }
    }

    private static void addAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(BEEHIVE, itemStack);
        if (itemEnchantmentLevel > 0) {
            ModifiableAttributeInstance attributeInstance = livingEntity.getAttribute(SUMMON_CAP.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) == null) {
                attributeInstance.addTransientModifier(new AttributeModifier(attributeModifierUUID, "Enchantment busy bee", 3, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
