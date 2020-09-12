package com.infamous.dungeons_gear.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.models.MercenaryArmorModel;
import com.infamous.dungeons_gear.armor.models.RenegadeArmorModel;
import com.infamous.dungeons_gear.interfaces.IArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.UUID;

import static com.infamous.dungeons_gear.items.ArmorList.*;

public class MercenaryArmorItem extends ArmorItem implements IArmor {

    private static final UUID[] ARMOR_MODIFIERS = new UUID[]{
            UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
            UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
            UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
            UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

    private final boolean unique;
    private final int damageReduceAmount;
    private final float toughness;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public MercenaryArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties);
        this.unique = unique;

        this.damageReduceAmount = armorMaterial.getDamageReductionAmount(slot);
        this.toughness = armorMaterial.getToughness();

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIERS[slot.getIndex()];
        builder.put(Attributes.field_233826_i_, new AttributeModifier(uuid, "Armor modifier", (double)this.damageReduceAmount, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.field_233827_j_, new AttributeModifier(uuid, "Armor toughness", (double)this.toughness, AttributeModifier.Operation.ADDITION));
        if (this.field_234655_c_ > 0) {
            builder.put(Attributes.field_233820_c_, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.field_234655_c_, AttributeModifier.Operation.ADDITION));
        }
        builder.put(Attributes.field_233823_f_, new AttributeModifier(uuid, "Armor attack damage boost", 0.20D * 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
        if(this.unique) {
            builder.put(Attributes.field_233825_h_, new AttributeModifier(uuid, "Armor attack speed boost", 0.25D * 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        this.attributeModifiers = builder.build();
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(this.unique) return DungeonsGear.MODID + ":textures/models/armor/renegade_armor.png";
        return DungeonsGear.MODID + ":textures/models/armor/mercenary_armor.png";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
        if(stack.getItem() == MERCENARY_ARMOR || stack.getItem() == MERCENARY_ARMOR_HELMET){
            return (A) new MercenaryArmorModel<>(1.0F, slot, entityLiving);
        }
        else if(stack.getItem() == RENEGADE_ARMOR || stack.getItem() == RENEGADE_ARMOR_HELMET){
            return (A) new RenegadeArmorModel<>(1.0F, slot, entityLiving);
        }
        return null;
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == this.slot ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    @Override
    public Rarity getRarity(ItemStack itemStack){
        if(this.unique) return Rarity.RARE;
        return Rarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (this.unique) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Renegade Armor made a fine payment for mercenaries hired to protect the villages against Illagers."));
        }
        else{
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Mercenary Armor, favored by those looking to save some coin, lacks flair but gets the job done."));
        }
    }

    @Override
    public double getRangedDamage() {
        return 10;
    }
}
