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
import static com.infamous.dungeons_gear.utilties.AbilityUtils.isPetOfAttacker;

public class WindHornItem extends Item implements IArtifact {
    public WindHornItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.EVENT_RAID_HORN, SoundCategory.BLOCKS, 64.0F, 1.0F);

        //((ServerPlayerEntity)playerIn).connection.sendPacket(new SPlaySoundEffectPacket(SoundEvents.EVENT_RAID_HORN, SoundCategory.NEUTRAL, d0, playerIn.posY, d1, 64.0F, 1.0F));


        List<LivingEntity> nearbyEntities = worldIn.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerIn.getPosX() - 5, playerIn.getPosY() - 5, playerIn.getPosZ() - 5,
                playerIn.getPosX() + 5, playerIn.getPosY() + 5, playerIn.getPosZ() + 5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });

        PROXY.spawnParticles(playerIn, ParticleTypes.CLOUD);
        for(LivingEntity nearbyEntity : nearbyEntities){


            // KNOCKBACK
            float knockbackMultiplier = 3.0F;
            double xRatio = playerIn.getPosX() - nearbyEntity.getPosX();
            double zRatio;
            for(zRatio = playerIn.getPosZ() - nearbyEntity.getPosZ(); xRatio * xRatio + zRatio * zRatio < 1.0E-4D; zRatio = (Math.random() - Math.random()) * 0.01D) {
                xRatio = (Math.random() - Math.random()) * 0.01D;
            }
            nearbyEntity.attackedAtYaw = (float)(MathHelper.atan2(zRatio, xRatio) * 57.2957763671875D - (double)nearbyEntity.rotationYaw);
            nearbyEntity.func_233627_a_(0.4F  * knockbackMultiplier, xRatio, zRatio);
            // END OF KNOCKBACK

            PROXY.spawnParticles(nearbyEntity, ParticleTypes.CLOUD);

            EffectInstance stun = new EffectInstance(Effects.SLOWNESS, 60, 4);
            nearbyEntity.addPotionEffect(stun);

        }
        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }
        setArtifactCooldown(playerIn, itemstack.getItem(), 200);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.WIND_HORN){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "When the Wind Horn echoes throughout the forests of the Overworld the creatures of the night tremble with fear."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Pushes enemies away from you and slows them briefly."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "5 Blocks Pushed"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "10 Seconds Cooldown"));
        }
    }
}
