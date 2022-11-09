package com.infamous.dungeons_gear.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;


public class SnowflakeParticle extends SpriteTexturedParticle {
	
    protected SnowflakeParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
    		IAnimatedSprite spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.quadSize *= 1.25F;
        this.lifetime = 5 + this.random.nextInt(10);
        this.hasPhysics = false;
        
        this.pickSprite(spriteSet);
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd += 0.004D;
            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.xd *= (double)0.75F;
            this.yd *= (double)0.75F;
            this.zd *= (double)0.75F;
            if (this.onGround) {
                this.xd *= (double)0.6F;
                this.zd *= (double)0.6F;
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(BasicParticleType particleType, ClientWorld level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new SnowflakeParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
