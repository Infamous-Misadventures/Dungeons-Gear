package com.infamous.dungeons_gear.utilties;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class PositionUtils {

	public static Vector3d getOffsetPos(Entity entity, double offsetX, double offsetY, double offsetZ, float rotation) {
        Vector3d vector3d = (new Vector3d(offsetZ, offsetY, offsetX).yRot(-rotation * ((float)Math.PI / 180F) - ((float)Math.PI / 2F)));
        return entity.position().add(vector3d.x, vector3d.y, vector3d.z);
	}
	
	public static BlockPos getOffsetBlockPos(Entity entity, double offsetX, double offsetY, double offsetZ, float rotation) {
        Vector3d vector3d = (new Vector3d(offsetZ, offsetY, offsetX).yRot(-rotation * ((float)Math.PI / 180F) - ((float)Math.PI / 2F)));
        return entity.blockPosition().offset(vector3d.x, vector3d.y, vector3d.z);
	}
	
	public static Vector3d getOffsetMotion(Entity entity, double offsetX, double offsetY, double offsetZ, float rotation) {
        Vector3d vector3d = (new Vector3d(offsetZ, offsetY, offsetX).yRot(-rotation * ((float)Math.PI / 180F) - ((float)Math.PI / 2F)));
        return vector3d;
	}
	
	public static void moveToCorrectHeight(Entity entity) {
		BlockPos blockpos = entity.blockPosition();	
        boolean flag = false;
        double d0 = 0.0D;

        do {
           BlockPos blockpos1 = blockpos.below();
           BlockState blockstate = entity.level.getBlockState(blockpos1);
           if (blockstate.isFaceSturdy(entity.level, blockpos1, Direction.UP)) {
              if (!entity.level.isEmptyBlock(blockpos)) {
                 BlockState blockstate1 = entity.level.getBlockState(blockpos);
                 VoxelShape voxelshape = blockstate1.getCollisionShape(entity.level, blockpos);
                 if (!voxelshape.isEmpty()) {
                    d0 = voxelshape.max(Direction.Axis.Y);
                 }
              }

              flag = true;
              break;
           }

           blockpos = blockpos.below();
        } while(blockpos.getY() >= MathHelper.floor(blockpos.getY()) - 1);

        if (flag) {
        	entity.setPos(blockpos.getX(), blockpos.getY() + d0, blockpos.getZ());
        }
	}
	
}
