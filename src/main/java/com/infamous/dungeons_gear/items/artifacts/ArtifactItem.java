package com.infamous.dungeons_gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.items.ItemTagWrappers;
import com.infamous.dungeons_gear.mixin.CooldownAccessor;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

import static com.infamous.dungeons_libraries.attribute.AttributeRegistry.ARTIFACT_COOLDOWN_MULTIPLIER;

public abstract class ArtifactItem extends Item implements ICurioItem {
    protected final UUID SLOT0_UUID = UUID.fromString("7037798e-ac2c-4711-aa72-ba73589f1411");
    protected final UUID SLOT1_UUID = UUID.fromString("1906bae9-9f26-4194-bb8a-ef95b8cad134");
    protected final UUID SLOT2_UUID = UUID.fromString("b99aa930-03d0-4b2d-aa69-7b5d943dd75c");
    protected boolean procOnItemUse = false;

    public ArtifactItem(Properties properties) {
        super(properties
                .durability(DungeonsGearConfig.ARTIFACT_DURABILITY.get())
        );
    }

    public static void putArtifactOnCooldown(PlayerEntity playerIn, Item item) {
        int cooldownInTicks = item instanceof ArtifactItem ?
                ((ArtifactItem)item).getCooldownInSeconds() * 20 : 0;

        ModifiableAttributeInstance artifactCooldownMultiplierAttribute = playerIn.getAttribute(ARTIFACT_COOLDOWN_MULTIPLIER.get());
        double attributeModifier = artifactCooldownMultiplierAttribute != null ? artifactCooldownMultiplierAttribute.getValue() : 1.0D;
        float cooldownEnchantmentReduction = 0;
        if (ModEnchantmentHelper.hasEnchantment(playerIn, ArmorEnchantmentList.COOLDOWN)) {
            int cooldownEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.COOLDOWN, playerIn);
            cooldownEnchantmentReduction = (int) (cooldownEnchantmentLevel * 0.1F * cooldownInTicks);
        }
        playerIn.getCooldowns().addCooldown(item, Math.max(0, (int) (cooldownInTicks * attributeModifier - cooldownEnchantmentReduction)));
    }

    public static void triggerSynergy(PlayerEntity player, ItemStack stack){
        ICombo comboCap = CapabilityHelper.getComboCapability(player);
        if(comboCap == null) return;

        if(!comboCap.hasArtifactSynergy()){
            comboCap.setArtifactSynergy(true);
        }
        if(stack.getItem() instanceof ArtifactItem){
            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.SPEED_SYNERGY)){
                int speedSynergyLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.SPEED_SYNERGY, player);
                EffectInstance speedBoost = new EffectInstance(Effects.MOVEMENT_SPEED, 20 * speedSynergyLevel);
                player.addEffect(speedBoost);
            }
            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.HEALTH_SYNERGY)){
                int healthSynergyLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.HEALTH_SYNERGY, player);
                player.heal(0.2F + (0.1F * healthSynergyLevel));
            }
        }

    }

    public static void reduceArtifactCooldowns(PlayerEntity playerEntity, double reductionInSeconds){
        for(Item item : playerEntity.getCooldowns().cooldowns.keySet()){
            if(item instanceof ArtifactItem){
                int createTicks = ((CooldownAccessor)playerEntity.getCooldowns().cooldowns.get(item)).getStartTime();
                int expireTicks = ((CooldownAccessor)playerEntity.getCooldowns().cooldowns.get(item)).getEndTime();
                int duration = expireTicks - createTicks;
                playerEntity.getCooldowns().addCooldown(item, Math.max(0, duration - (int)(reductionInSeconds * 20)));
            }
        }
    }

    public Rarity getRarity(ItemStack itemStack) {
        return Rarity.RARE;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return ItemTagWrappers.ARTIFACT_REPAIR_ITEMS.contains(repair.getItem()) || super.isValidRepairItem(toRepair, repair);
    }

//    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        if (procOnItemUse) return super.use(worldIn, playerIn, handIn);
//        ArtifactUseContext iuc = new ArtifactUseContext(playerIn, handIn, new BlockRayTraceResult(playerIn.position(), Direction.UP, playerIn.blockPosition(), false));
//        return activateArtifact(iuc);
//    }

    public ActionResult<ItemStack> activateArtifact(ArtifactUseContext artifactUseContext) {
        if(artifactUseContext.getPlayer() != null) {
            ItemStack itemStack = artifactUseContext.getItemInHand();
            if (artifactUseContext.getPlayer().getCooldowns().isOnCooldown(itemStack.getItem())){
                return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
            }
        }
        ActionResult<ItemStack> procResult = procArtifact(artifactUseContext);
        if(procResult.getResult().consumesAction() && artifactUseContext.getPlayer() != null && !artifactUseContext.getLevel().isClientSide){
            triggerSynergy(artifactUseContext.getPlayer(), artifactUseContext.getItemInHand());
        }
        return procResult;
    }

    public abstract ActionResult<ItemStack> procArtifact(ArtifactUseContext iuc);

    public abstract int getCooldownInSeconds();

    public abstract int getDurationInSeconds();

    public void stopUsingArtifact(LivingEntity livingEntity){}

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return ImmutableMultimap.of();
    }

    protected UUID getUUIDForSlot(int slotIndex){
        switch(slotIndex){
            case 0: return SLOT0_UUID;
            case 1: return SLOT1_UUID;
            case 2: return SLOT2_UUID;
            default: return SLOT2_UUID;
        }
    }
}
