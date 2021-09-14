package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.interfaces.IChargeableItem;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class EyeOfTheGuardianItem extends AbstractBeaconItem implements IChargeableItem {

    public static final BeaconBeamColor EYE_OF_THE_GUARDIAN_BEACON_BEAM_COLOR =
            new BeaconBeamColor((short) 25, (short) 88, (short) 82, (short) 255, (short) 255, (short) 255);

    public EyeOfTheGuardianItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUse(World world, LivingEntity livingEntity, ItemStack stack, int count) {
        if(IChargeableItem.isCharged(stack)){
            super.onUse(world, livingEntity, stack, count);
        } else{
            float charge = (float)(stack.getUseDuration() - count) / (float) this.getChargeTime();
            if(charge >= 1.0F){
                IChargeableItem.setCharged(stack, true);
            }
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        entityLiving.stopActiveHand();
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
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
    public BeaconBeamColor getBeamColor() {
        return EYE_OF_THE_GUARDIAN_BEACON_BEAM_COLOR;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
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
    protected boolean consumeTick(PlayerEntity playerEntity) {
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
