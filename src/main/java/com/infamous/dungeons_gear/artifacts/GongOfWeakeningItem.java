package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.armor.BattleRobeItem;
import com.infamous.dungeons_gear.armor.EvocationRobeItem;
import com.infamous.dungeons_gear.armor.GuardsArmorItem;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.utilties.AbilityUtils.isPetOfAttacker;

public class GongOfWeakeningItem extends Item implements IArtifact {
    public GongOfWeakeningItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        World world = playerIn.getEntityWorld();

        world.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS, 2.0F, 1.0F);
        world.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS, 1.0F, 1.0F);


        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerIn.getPosX() - 5, playerIn.getPosY() - 5, playerIn.getPosZ() - 5,
                playerIn.getPosX() + 5, playerIn.getPosY() + 5, playerIn.getPosZ() + 5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });
        for(LivingEntity nearbyEntity : nearbyEntities){
            EffectInstance weakness = new EffectInstance(Effects.WEAKNESS, 140);
            EffectInstance vulnerability = new EffectInstance(Effects.RESISTANCE, 140, -2);
            nearbyEntity.addPotionEffect(weakness);
            nearbyEntity.addPotionEffect(vulnerability);
        }

        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }



        setArtifactCooldown(playerIn, itemstack.getItem(), 400);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.GONG_OF_WEAKENING){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The ancient gong, marked with the symbols of a nameless kingdom, feels safe in your hands but emits a menacing hum to those nearby."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Weakens enemies around you, decreasing their damage and defensive capabilities."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "7 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "20 Seconds Cooldown"));
        }
    }
}
