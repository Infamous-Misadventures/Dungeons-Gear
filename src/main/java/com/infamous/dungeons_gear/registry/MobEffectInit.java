package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.effects.CustomEffect;
import com.infamous.dungeons_gear.effects.PartyStarterEffect;
import com.infamous.dungeons_gear.effects.ShieldingEffect;
import com.infamous.dungeons_libraries.attribute.AttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class MobEffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

    public static RegistryObject<MobEffect> SHIELDING = MOB_EFFECTS.register("shielding", () -> new ShieldingEffect(MobEffectCategory.BENEFICIAL, 10044730));
    public static RegistryObject<MobEffect> SOUL_PROTECTION = MOB_EFFECTS.register("soul_protection", () -> new CustomEffect(MobEffectCategory.BENEFICIAL, 2445989));
    public static RegistryObject<MobEffect> STUNNED = MOB_EFFECTS.register("stunned", () -> new CustomEffect(MobEffectCategory.HARMFUL, 4738376));
    public static RegistryObject<MobEffect> PARTY_STARTER = MOB_EFFECTS.register("party_starter", () -> new PartyStarterEffect(MobEffectCategory.BENEFICIAL, 0xE25822));
    public static RegistryObject<MobEffect> DYNAMO = MOB_EFFECTS.register("dynamo", () -> new CustomEffect(MobEffectCategory.BENEFICIAL, 0xFFBB2E));
    public static RegistryObject<MobEffect> LIFE_STEAL = MOB_EFFECTS.register("life_steal", () -> new CustomEffect(MobEffectCategory.BENEFICIAL, 0x660901).addAttributeModifier(AttributeRegistry.LIFE_STEAL.get(), "ba815a35-c0d4-4bbd-b932-76a916d44eb9", 0.05F, AttributeModifier.Operation.ADDITION));
    public static RegistryObject<MobEffect> BOW_CHARGE = MOB_EFFECTS.register("bow_charge", () -> new CustomEffect(MobEffectCategory.BENEFICIAL, 0x3571D4));
}
