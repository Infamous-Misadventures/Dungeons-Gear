package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;

import java.util.Random;

public class ClientProxy extends CommonProxy {

    @Override
    public void spawnParticles(Entity entity, BasicParticleType particleType) {
        for (int k = 0; k < 20; ++k)
        {
            Random rand = new Random();
            double d2 = rand.nextGaussian() * 0.02D;
            double d0 = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            entity.getCommandSenderWorld().addParticle(particleType,
                    entity.getX() + (double)(rand.nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth(),
                    entity.getY() + (double)(rand.nextFloat() * entity.getBbHeight()),
                    entity.getZ() + (double)(rand.nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth(), d2, d0, d1);
        }
    }
}
