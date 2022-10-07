package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.interfaces.IChargeableItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EyeOfTheGuardianItem extends AbstractBeaconItem implements IChargeableItem {

    public static final BeamColor EYE_OF_THE_GUARDIAN_BEACON_BEAM_COLOR =
            new BeamColor((short) 25, (short) 88, (short) 82, (short) 255, (short) 255, (short) 255);

    public EyeOfTheGuardianItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(World world, LivingEntity livingEntity, ItemStack stack, int count) {
        if(IChargeableItem.isCharged(stack)){
            super.onUseTick(world, livingEntity, stack, count);
        } else{
            float charge = (float)(stack.getUseDuration() - count) / (float) this.getChargeTime();
            if(charge >= 1.0F){
                IChargeableItem.setCharged(stack, true);
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        entityLiving.releaseUsingItem();
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
        IChargeableItem.setCharged(stack, false);
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entityLiving;
            ArtifactItem.putArtifactOnCooldown(player, stack.getItem());
        }
    }

    @Override
    public boolean canFire(PlayerEntity playerEntity, ItemStack stack) {
        return true;
    }

    @Override
    public BeamColor getBeamColor() {
        return EYE_OF_THE_GUARDIAN_BEACON_BEAM_COLOR;
    }



    @Override
    public int getCooldownInSeconds() {
        return 20;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.getDurationInSeconds() * 20;
    }

    @Override
    protected boolean consumeTick(PlayerEntity playerEntity, ItemStack stack) {
        return true;
    }

    @Override
    public int getDurationInSeconds() {
        return 3;
    }


    @Override
    public int getChargeTimeInSeconds() {
        return 1;
    }

}
