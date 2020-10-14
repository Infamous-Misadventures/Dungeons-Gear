package com.infamous.dungeons_gear.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.combat.CombatEventHandler;
import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.items.WeaponList;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.WeaponList.SOUL_FIST;

public class GauntletItem extends TieredItem implements IOffhandAttack, IVanishable, IMeleeWeapon, ISoulGatherer {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> attributeModifierMultimap;

    public GauntletItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, properties);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifierMultimap = builder.build();
    }

    // Only used by MobEntity class for determining sword changes
    public float getAttackDamage() {
        return this.attackDamage;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return true;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (p_220044_0_) -> {
                p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */

    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.getHarvestLevel() < 0;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND || equipmentSlot == EquipmentSlotType.OFFHAND ? this.attributeModifierMultimap : super.getAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_SWORD) && enchantment != Enchantments.SWEEPING;
    }

    public Rarity getRarity(ItemStack itemStack){

        if(itemStack.getItem() == WeaponList.FIGHTERS_BINDING
                || itemStack.getItem() == WeaponList.MAULER
                || itemStack.getItem() == SOUL_FIST
        ){
            return Rarity.RARE;
        }
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == WeaponList.GAUNTLET){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Gauntlets call back to an ancient style of hand to hand combat."));

            //list.add(new StringTextComponent(TextFormatting.GREEN + "Relentless Combo"));
        }
        if(stack.getItem() == WeaponList.FIGHTERS_BINDING){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Made in the wilds beyond the mountains, these gauntlets have been worn by warriors for centuries."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Boosts Attack Speed"));
            //list.add(new StringTextComponent(TextFormatting.GREEN + "Turbo punches"));
        }
        if(stack.getItem() == WeaponList.MAULER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This claw-like weapon, wielded by ancient Illager soldiers, is savage in battle."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increases Attack Speed (Rampaging I)"));
            //list.add(new StringTextComponent(TextFormatting.GREEN + "Relentless Combo"));
        }
        if(stack.getItem() == SOUL_FIST){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Soul Fists are gauntlets clad with great gemstones, each containing a powerful soul."));

            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Souls Critical Boost (Enigma Resonator I)"));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "+2 XP Gathering"));
            //list.add(new StringTextComponent(TextFormatting.GREEN + "Relentless Combo"));
        }

        list.add(new StringTextComponent(TextFormatting.GREEN + "Dual Wield"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn == Hand.OFF_HAND) {
            CombatEventHandler.checkForOffhandAttack();
            ItemStack offhand = playerIn.getHeldItem(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, offhand);
        } else {
            return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if(stack.getItem() == SOUL_FIST){
            return 2;
        }
        else return 0;
    }
}
