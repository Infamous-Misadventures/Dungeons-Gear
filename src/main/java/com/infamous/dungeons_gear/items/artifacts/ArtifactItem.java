package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.items.ItemTagWrappers;
import com.infamous.dungeons_gear.mixin.CooldownAccessor;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public abstract class ArtifactItem extends Item {
    protected boolean procOnItemUse = false;

    public ArtifactItem(Properties properties) {
        super(properties
                .maxDamage(DungeonsGearConfig.ARTIFACT_DURABILITY.get())
        );
    }

    public static void putArtifactOnCooldown(PlayerEntity playerIn, Item item) {
        int cooldownInTicks = item instanceof ArtifactItem ?
                ((ArtifactItem)item).getCooldownInSeconds() * 20 : 0;
        ItemStack helmet = playerIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = playerIn.getItemStackFromSlot(EquipmentSlotType.CHEST);


        float armorCooldownModifier = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getArtifactCooldown() : 0;
        float armorCooldownModifier2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getArtifactCooldown() : 0;

        float totalArmorCooldownModifier = 1.0F - armorCooldownModifier * 0.01F - armorCooldownModifier2 * 0.01F;
        float cooldownEnchantmentReduction = 0;
        if (ModEnchantmentHelper.hasEnchantment(playerIn, ArmorEnchantmentList.COOLDOWN)) {
            int cooldownEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.COOLDOWN, playerIn);
            cooldownEnchantmentReduction = (int) (cooldownEnchantmentLevel * 0.1F * cooldownInTicks);

        }
        playerIn.getCooldownTracker().setCooldown(item, Math.max(0, (int) (cooldownInTicks * totalArmorCooldownModifier - cooldownEnchantmentReduction)));
    }

    public static void triggerSynergy(PlayerEntity player, ItemStack stack){
        ICombo comboCap = CapabilityHelper.getComboCapability(player);
        if(comboCap == null) return;

        if(!comboCap.hasArtifactSynergy()){
            comboCap.setArtifactSynergy(true);
        }
        if(stack.getItem() instanceof ArtifactItem){
            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.SPEED_SYNERGY)){
                int speedSynergyLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.SPEED_SYNERGY, player);
                EffectInstance speedBoost = new EffectInstance(Effects.SPEED, 20 * speedSynergyLevel);
                player.addPotionEffect(speedBoost);
            }
            if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.HEALTH_SYNERGY)){
                int healthSynergyLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.HEALTH_SYNERGY, player);
                player.heal(0.2F + (0.1F * healthSynergyLevel));
            }
        }

    }

    public static void reduceArtifactCooldowns(PlayerEntity playerEntity, double reductionInSeconds){
        for(Item item : playerEntity.getCooldownTracker().cooldowns.keySet()){
            if(item instanceof ArtifactItem){
                int createTicks = ((CooldownAccessor)playerEntity.getCooldownTracker().cooldowns.get(item)).getCreateTicks();
                int expireTicks = ((CooldownAccessor)playerEntity.getCooldownTracker().cooldowns.get(item)).getExpireTicks();
                int duration = expireTicks - createTicks;
                playerEntity.getCooldownTracker().setCooldown(item, Math.max(0, duration - (int)(reductionInSeconds * 20)));
            }
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        if (!procOnItemUse) return super.onItemUse(itemUseContext);
        ActionResultType procResultType = procArtifact(itemUseContext).getType();
        if(procResultType.isSuccessOrConsume() && itemUseContext.getPlayer() != null && !itemUseContext.getWorld().isRemote){
            triggerSynergy(itemUseContext.getPlayer(), itemUseContext.getItem());
        }
        return procResultType;
    }

    public Rarity getRarity(ItemStack itemStack) {
        return Rarity.RARE;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return ItemTagWrappers.ARTIFACT_REPAIR_ITEMS.contains(repair.getItem()) || super.getIsRepairable(toRepair, repair);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (procOnItemUse) return super.onItemRightClick(worldIn, playerIn, handIn);
        ItemUseContext iuc = new ItemUseContext(playerIn, handIn, new BlockRayTraceResult(playerIn.getPositionVec(), Direction.UP, playerIn.getPosition(), false));
        ActionResult<ItemStack> procResult = procArtifact(iuc);
        if(procResult.getType().isSuccessOrConsume() && !worldIn.isRemote){
            triggerSynergy(playerIn, iuc.getItem());
        }
        return procResult;
    }

    public abstract ActionResult<ItemStack> procArtifact(ItemUseContext iuc);

    public abstract int getCooldownInSeconds();

    public abstract int getDurationInSeconds();
}
