package com.infamous.dungeons_gear.artifacts.fallingblocks;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.damagesources.SummonedFallingBlockDamageSource;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class SummonedFallingBlockEntity extends FallingBlockEntity {
    private boolean hurtEntities;
    private int fallHurtMax;
    private float fallHurtAmount;
    private LivingEntity summoner;

    public SummonedFallingBlockEntity(World p_i50218_2_) {
        super(EntityType.FALLING_BLOCK, p_i50218_2_);
    }

    public SummonedFallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public SummonedFallingBlockEntity(World world, double x, double y, double z, BlockState blockState, LivingEntity summoner) {
        super(world, x, y, z, blockState);
        this.summoner = summoner;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
    }

    @Override
    public boolean onLivingFall(float p_225503_1_, float p_225503_2_) {
        if (this.hurtEntities) {
            int i = MathHelper.ceil(p_225503_1_ - 1.0F);
            if (i > 0) {
                List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));
                //boolean flag = this.fallTile.func_235714_a_(BlockTags.ANVIL);
                DamageSource damagesource = new SummonedFallingBlockDamageSource(this, this.summoner);
                Iterator var7 = list.iterator();

                while(var7.hasNext()) {
                    Entity entity = (Entity)var7.next();
                    entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor((float)i * this.fallHurtAmount), this.fallHurtMax));
                }

                /*
                if (flag && (double)this.rand.nextFloat() < 0.05000000074505806D + (double)i * 0.05D) {
                    BlockState blockstate = AnvilBlock.damage(this.fallTile);
                    if (blockstate == null) {
                        this.dontSetBlock = true;
                    } else {
                        this.fallTile = blockstate;
                    }
                }

                 */
            }
        }

        return false;
    }

    @Override
    public void setHurtEntities(boolean b) {
        this.hurtEntities = b;
    }
}
