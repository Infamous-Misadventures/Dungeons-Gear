package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.armor.BattleRobeItem;
import com.infamous.dungeons_gear.armor.EvocationRobeItem;
import com.infamous.dungeons_gear.armor.GuardsArmorItem;
import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class GhostCloakItem extends Item implements IArtifact {
    public GhostCloakItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        EffectInstance invisibility = new EffectInstance(Effects.INVISIBILITY, 60);
        playerIn.addPotionEffect(invisibility);

        EffectInstance glowing = new EffectInstance(Effects.GLOWING, 60);
        playerIn.addPotionEffect(glowing);

        EffectInstance swiftness = new EffectInstance(Effects.SPEED, 60);
        playerIn.addPotionEffect(swiftness);

        EffectInstance resistance = new EffectInstance(Effects.RESISTANCE, 60,3);
        playerIn.addPotionEffect(resistance);

        //ICombo comboCap = playerIn.getCapability(ComboProvider.COMBO_CAPABILITY).orElseThrow(IllegalStateException::new);
        //comboCap.setGhostForm(true);

        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }
        IArtifact.setArtifactCooldown(playerIn, itemstack.getItem(), 120);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.GHOST_CLOAK){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The souls trapped within the Ghost Cloak are protective, but they radiate a sense of melancholy."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Briefly gain Ghost Form, allowing you to move through mobs and absorb some damage."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "6 Seconds Cooldown"));
        }
    }
}
