package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.utilties.AbilityHelper.isPetOfAttacker;

public class LightFeatherItem extends Item implements IArtifact {
    public LightFeatherItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        // Jump instead of roll
        playerIn.jump();

        List<LivingEntity> nearbyEntities = worldIn.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerIn.getPosX() - 5, playerIn.getPosY() - 5, playerIn.getPosZ() - 5,
                playerIn.getPosX() + 5, playerIn.getPosY() + 5, playerIn.getPosZ() + 5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });

        PROXY.spawnParticles(playerIn, ParticleTypes.CLOUD);
        for(LivingEntity nearbyEntity : nearbyEntities){

            // KNOCKBACK
            float knockbackMultiplier = 1.0F;
            double xRatio = playerIn.getPosX() - nearbyEntity.getPosX();
            double zRatio;
            for(zRatio = playerIn.getPosZ() - nearbyEntity.getPosZ(); xRatio * xRatio + zRatio * zRatio < 1.0E-4D; zRatio = (Math.random() - Math.random()) * 0.01D) {
                xRatio = (Math.random() - Math.random()) * 0.01D;
            }
            nearbyEntity.attackedAtYaw = (float)(MathHelper.atan2(zRatio, xRatio) * 57.2957763671875D - (double)nearbyEntity.rotationYaw);
            nearbyEntity.func_233627_a_(0.4F  * knockbackMultiplier, xRatio, zRatio);
            // END OF KNOCKBACK

            PROXY.spawnParticles(nearbyEntity, ParticleTypes.CLOUD);


            EffectInstance stunned = new EffectInstance(CustomEffects.STUNNED, 60);
            EffectInstance nausea = new EffectInstance(Effects.NAUSEA, 60);
            EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 60, 4);
            nearbyEntity.addPotionEffect(slowness);
            nearbyEntity.addPotionEffect(nausea);
            nearbyEntity.addPotionEffect(stunned);

        }

        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }
        setArtifactCooldown(playerIn, itemstack.getItem(), 60);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.LIGHT_FEATHER){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "No one knows what mysterious creature this feather came from, but it is as beautiful and powerful."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Lets you tumble through the air, stunning and pushing enemies back as you go."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "3 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "3 Seconds Cooldown"));
        }
    }
}
