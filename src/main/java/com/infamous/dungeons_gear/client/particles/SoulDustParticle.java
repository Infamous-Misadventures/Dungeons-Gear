package com.infamous.dungeons_gear.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class SoulDustParticle extends TextureSheetParticle {
	
    protected SoulDustParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.quadSize *= 1.5F;
        this.lifetime = 10;
        this.hasPhysics = true;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.xd = this.xd * 0.5F;
        this.yd = this.yd * 0.25F;
        this.zd = this.zd * 0.5F;
        fadeOut();
    }

    @Override
    protected int getLightColor(float p_189214_1_) {
    	return 240;
    }
    
    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age + 1);
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
            SoulDustParticle soulDustParticle = new SoulDustParticle(level, x, y, z, dx, dy, dz);
            soulDustParticle.pickSprite(this.spriteSet);
            return soulDustParticle;
        }
    }
}
