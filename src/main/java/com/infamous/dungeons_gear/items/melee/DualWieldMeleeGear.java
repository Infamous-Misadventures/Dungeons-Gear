package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.capabilities.ModCapabilities;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.compat.DungeonsGearCompatibility;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_libraries.items.gearconfig.MeleeGear;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DualWieldMeleeGear extends MeleeGear implements IDualWieldWeapon {

    public DualWieldMeleeGear(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (handIn == InteractionHand.OFF_HAND && worldIn.isClientSide && !DungeonsGearCompatibility.warDance) {
            CombatEventHandler.checkForOffhandAttack();
            ItemStack offhand = playerIn.getItemInHand(handIn);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, offhand);
        } else {
            return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.invulnerableTime = 0;
        if (stack.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).isPresent()) {
            if (!stack.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).resolve().get().getLinkedItemStack().isEmpty())
                stack = stack.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).resolve().get().getLinkedItemStack();
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity && !worldIn.isClientSide)
            update((LivingEntity) entityIn, stack, itemSlot);
    }
}
