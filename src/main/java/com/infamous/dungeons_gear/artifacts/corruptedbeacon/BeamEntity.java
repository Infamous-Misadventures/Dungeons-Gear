package com.infamous.dungeons_gear.artifacts.corruptedbeacon;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class BeamEntity extends AbstractBeamEntity {

    public BeamEntity(EntityType<? extends BeamEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BeamEntity(World worldIn, LivingEntity shooter) {
        super(ModEntityTypes.BEAM.get(), shooter, worldIn);
    }

    public BeamEntity(World world) {
        super(ModEntityTypes.BEAM.get(),world);
    }

    @Override
    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }
}
