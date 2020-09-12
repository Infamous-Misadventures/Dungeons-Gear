package com.infamous.dungeons_gear.artifacts.corruptedbeacon;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class BeamEntity extends AbstractBeamEntity {

    public BeamEntity(EntityType<? extends BeamEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BeamEntity(FMLPlayMessages.SpawnEntity packet, World worldIn)
    {
        super(ModEntityTypes.BEAM.get(), worldIn);
    }

    public BeamEntity(World worldIn, double x, double y, double z) {
        super(ModEntityTypes.BEAM.get(), x, y, z, worldIn);
    }

    public BeamEntity(World worldIn, LivingEntity shooter) {
        super(ModEntityTypes.BEAM.get(), shooter, worldIn);
    }


    public BeamEntity(World world) {
        super(ModEntityTypes.BEAM.get(),world);
    }

    /**
     * Create a custom spawn packet to spawn entity in Client World
     */
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected ItemStack getArrowStack() {
        // No Item Stack for this Entity
        return null;
    }
}
