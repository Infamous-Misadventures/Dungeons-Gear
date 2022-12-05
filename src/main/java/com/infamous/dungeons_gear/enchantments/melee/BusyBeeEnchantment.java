package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.integration.curios.CuriosIntegration;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.summon.SummonHelper;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
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
import static com.infamous.dungeons_gear.registry.EnchantmentInit.BUSY_BEE;
import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.SUMMON_CAP;

@Mod.EventBusSubscriber(modid= MODID)
public class BusyBeeEnchantment extends DungeonsEnchantment {
    private final static Map<EquipmentSlot, UUID> EQUIPMENT_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.HEAD, UUID.fromString("3d8f6614-0db6-4031-a057-2bd0f4ffdc16")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.CHEST, UUID.fromString("fdb79435-0efc-45ac-8035-6481f00461e7")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.LEGS, UUID.fromString("2393bf96-e3c3-4d6d-9951-f50fb0585f3c")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.FEET, UUID.fromString("fdd1db94-38e8-48c5-b5dc-b8ac647f457a")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.MAINHAND, UUID.fromString("0581a4fc-4037-4cbb-aaf5-c43ba5213963")),
                    new AbstractMap.SimpleImmutableEntry<>(EquipmentSlot.OFFHAND, UUID.fromString("ce6681fb-6613-40fa-a0f6-5c552141f45b"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
    private final static Map<Integer, UUID> CURIO_ATTRIBUTE_UUID_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(0, UUID.fromString("d54bd0cc-6359-4d1f-b09e-9a2a8059f823")),
                    new AbstractMap.SimpleImmutableEntry<>(1, UUID.fromString("a1b05109-6d42-4bd4-95d0-fceb7b7d7abd")),
                    new AbstractMap.SimpleImmutableEntry<>(2, UUID.fromString("ea152764-1b4c-4bde-bae7-826ea3562106"))
            )
            .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

    public BusyBeeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onBusyBeeKill(LivingDeathEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrow) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntity();
            if(attacker != null){
                ItemStack mainhand = attacker.getMainHandItem();
                if(ModEnchantmentHelper.hasEnchantment(mainhand, BUSY_BEE.get())){
                    int busyBeeLevel = EnchantmentHelper.getItemEnchantmentLevel(BUSY_BEE.get(), mainhand);
                    float busyBeeRand = attacker.getRandom().nextFloat();
                    float busyBeeChance = (float) (DungeonsGearConfig.BUSY_BEE_BASE_CHANCE.get() + busyBeeLevel * DungeonsGearConfig.BUSY_BEE_CHANCE_PER_LEVEL.get());
                    if(busyBeeRand <= busyBeeChance) {
                        if(SummonHelper.summonEntity(attacker, victim.blockPosition(), EntityType.BEE) != null) {
                            SoundHelper.playCreatureSound(attacker, SoundEvents.BEE_LOOP);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        removeAttribute(event.getFrom(), event.getEntity(), EQUIPMENT_ATTRIBUTE_UUID_MAP.get(event.getSlot()));
        addAttribute(event.getTo(), event.getEntity(), EQUIPMENT_ATTRIBUTE_UUID_MAP.get(event.getSlot()));
    }

    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        if(!event.getIdentifier().equals(CuriosIntegration.ARTIFACT_IDENTIFIER)) return;
        removeAttribute(event.getFrom(), event.getEntity(), CURIO_ATTRIBUTE_UUID_MAP.get(event.getSlotIndex()));
        addAttribute(event.getTo(), event.getEntity(), CURIO_ATTRIBUTE_UUID_MAP.get(event.getSlotIndex()));
    }

    private static void removeAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        if (EnchantmentHelper.getItemEnchantmentLevel(BUSY_BEE.get(), itemStack) > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(SUMMON_CAP.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) != null) {
                attributeInstance.removeModifier(attributeModifierUUID);
            }
        }
    }

    private static void addAttribute(ItemStack itemStack, LivingEntity livingEntity, UUID attributeModifierUUID) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(BUSY_BEE.get(), itemStack);
        if (itemEnchantmentLevel > 0) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(SUMMON_CAP.get());
            if (attributeInstance != null && attributeInstance.getModifier(attributeModifierUUID) == null) {
                attributeInstance.addTransientModifier(new AttributeModifier(attributeModifierUUID, "Enchantment busy bee", 3, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
