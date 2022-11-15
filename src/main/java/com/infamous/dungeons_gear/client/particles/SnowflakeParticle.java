package com.infamous.dungeons_gear.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class SnowflakeParticle extends TextureSheetParticle {
	
    protected SnowflakeParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.quadSize *= 1.25F;
        this.lifetime = 5 + this.random.nextInt(10);
        this.hasPhysics = false;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
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
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet sprite){
            this.spriteSet = sprite;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            SnowflakeParticle snowflakeParticle = new SnowflakeParticle(level, x, y, z, dx, dy, dz);
            snowflakeParticle.pickSprite(this.spriteSet);
            return snowflakeParticle;
        }
    }
}
