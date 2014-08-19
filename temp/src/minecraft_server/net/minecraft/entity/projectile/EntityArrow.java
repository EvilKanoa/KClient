package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityArrow extends Entity implements IProjectile {

   private int field_145791_d = -1;
   private int field_145792_e = -1;
   private int field_145789_f = -1;
   private Block field_145790_g;
   private int field_70253_h;
   private boolean field_70254_i;
   public int field_70251_a;
   public int field_70249_b;
   public Entity field_70250_c;
   private int field_70252_j;
   private int field_70257_an;
   private double field_70255_ao = 2.0D;
   private int field_70256_ap;
   private static final String __OBFID = "CL_00001715";


   public EntityArrow(World p_i1753_1_) {
      super(p_i1753_1_);
      this.field_70155_l = 10.0D;
      this.func_70105_a(0.5F, 0.5F);
   }

   public EntityArrow(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
      super(p_i1754_1_);
      this.field_70155_l = 10.0D;
      this.func_70105_a(0.5F, 0.5F);
      this.func_70107_b(p_i1754_2_, p_i1754_4_, p_i1754_6_);
      this.field_70129_M = 0.0F;
   }

   public EntityArrow(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_) {
      super(p_i1755_1_);
      this.field_70155_l = 10.0D;
      this.field_70250_c = p_i1755_2_;
      if(p_i1755_2_ instanceof EntityPlayer) {
         this.field_70251_a = 1;
      }

      this.field_70163_u = p_i1755_2_.field_70163_u + (double)p_i1755_2_.func_70047_e() - 0.10000000149011612D;
      double var6 = p_i1755_3_.field_70165_t - p_i1755_2_.field_70165_t;
      double var8 = p_i1755_3_.field_70121_D.field_72338_b + (double)(p_i1755_3_.field_70131_O / 3.0F) - this.field_70163_u;
      double var10 = p_i1755_3_.field_70161_v - p_i1755_2_.field_70161_v;
      double var12 = (double)MathHelper.func_76133_a(var6 * var6 + var10 * var10);
      if(var12 >= 1.0E-7D) {
         float var14 = (float)(Math.atan2(var10, var6) * 180.0D / 3.1415927410125732D) - 90.0F;
         float var15 = (float)(-(Math.atan2(var8, var12) * 180.0D / 3.1415927410125732D));
         double var16 = var6 / var12;
         double var18 = var10 / var12;
         this.func_70012_b(p_i1755_2_.field_70165_t + var16, this.field_70163_u, p_i1755_2_.field_70161_v + var18, var14, var15);
         this.field_70129_M = 0.0F;
         float var20 = (float)var12 * 0.2F;
         this.func_70186_c(var6, var8 + (double)var20, var10, p_i1755_4_, p_i1755_5_);
      }
   }

   public EntityArrow(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
      super(p_i1756_1_);
      this.field_70155_l = 10.0D;
      this.field_70250_c = p_i1756_2_;
      if(p_i1756_2_ instanceof EntityPlayer) {
         this.field_70251_a = 1;
      }

      this.func_70105_a(0.5F, 0.5F);
      this.func_70012_b(p_i1756_2_.field_70165_t, p_i1756_2_.field_70163_u + (double)p_i1756_2_.func_70047_e(), p_i1756_2_.field_70161_v, p_i1756_2_.field_70177_z, p_i1756_2_.field_70125_A);
      this.field_70165_t -= (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
      this.field_70163_u -= 0.10000000149011612D;
      this.field_70161_v -= (double)(MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70129_M = 0.0F;
      this.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F));
      this.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F));
      this.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.1415927F));
      this.func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, p_i1756_3_ * 1.5F, 1.0F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(16, Byte.valueOf((byte)0));
   }

   public void func_70186_c(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
      float var9 = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
      p_70186_1_ /= (double)var9;
      p_70186_3_ /= (double)var9;
      p_70186_5_ /= (double)var9;
      p_70186_1_ += this.field_70146_Z.nextGaussian() * (double)(this.field_70146_Z.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_3_ += this.field_70146_Z.nextGaussian() * (double)(this.field_70146_Z.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_5_ += this.field_70146_Z.nextGaussian() * (double)(this.field_70146_Z.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_1_ *= (double)p_70186_7_;
      p_70186_3_ *= (double)p_70186_7_;
      p_70186_5_ *= (double)p_70186_7_;
      this.field_70159_w = p_70186_1_;
      this.field_70181_x = p_70186_3_;
      this.field_70179_y = p_70186_5_;
      float var10 = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
      this.field_70126_B = this.field_70177_z = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / 3.1415927410125732D);
      this.field_70127_C = this.field_70125_A = (float)(Math.atan2(p_70186_3_, (double)var10) * 180.0D / 3.1415927410125732D);
      this.field_70252_j = 0;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if(this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float var1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / 3.1415927410125732D);
         this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)var1) * 180.0D / 3.1415927410125732D);
      }

      Block var16 = this.field_70170_p.func_147439_a(this.field_145791_d, this.field_145792_e, this.field_145789_f);
      if(var16.func_149688_o() != Material.field_151579_a) {
         var16.func_149719_a(this.field_70170_p, this.field_145791_d, this.field_145792_e, this.field_145789_f);
         AxisAlignedBB var2 = var16.func_149668_a(this.field_70170_p, this.field_145791_d, this.field_145792_e, this.field_145789_f);
         if(var2 != null && var2.func_72318_a(this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
            this.field_70254_i = true;
         }
      }

      if(this.field_70249_b > 0) {
         --this.field_70249_b;
      }

      if(this.field_70254_i) {
         int var18 = this.field_70170_p.func_72805_g(this.field_145791_d, this.field_145792_e, this.field_145789_f);
         if(var16 == this.field_145790_g && var18 == this.field_70253_h) {
            ++this.field_70252_j;
            if(this.field_70252_j == 1200) {
               this.func_70106_y();
            }

         } else {
            this.field_70254_i = false;
            this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70252_j = 0;
            this.field_70257_an = 0;
         }
      } else {
         ++this.field_70257_an;
         Vec3 var17 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         Vec3 var3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         MovingObjectPosition var4 = this.field_70170_p.func_147447_a(var17, var3, false, true, false);
         var17 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         var3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         if(var4 != null) {
            var3 = this.field_70170_p.func_82732_R().func_72345_a(var4.field_72307_f.field_72450_a, var4.field_72307_f.field_72448_b, var4.field_72307_f.field_72449_c);
         }

         Entity var5 = null;
         List var6 = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
         double var7 = 0.0D;

         int var9;
         float var11;
         for(var9 = 0; var9 < var6.size(); ++var9) {
            Entity var10 = (Entity)var6.get(var9);
            if(var10.func_70067_L() && (var10 != this.field_70250_c || this.field_70257_an >= 5)) {
               var11 = 0.3F;
               AxisAlignedBB var12 = var10.field_70121_D.func_72314_b((double)var11, (double)var11, (double)var11);
               MovingObjectPosition var13 = var12.func_72327_a(var17, var3);
               if(var13 != null) {
                  double var14 = var17.func_72438_d(var13.field_72307_f);
                  if(var14 < var7 || var7 == 0.0D) {
                     var5 = var10;
                     var7 = var14;
                  }
               }
            }
         }

         if(var5 != null) {
            var4 = new MovingObjectPosition(var5);
         }

         if(var4 != null && var4.field_72308_g != null && var4.field_72308_g instanceof EntityPlayer) {
            EntityPlayer var20 = (EntityPlayer)var4.field_72308_g;
            if(var20.field_71075_bZ.field_75102_a || this.field_70250_c instanceof EntityPlayer && !((EntityPlayer)this.field_70250_c).func_96122_a(var20)) {
               var4 = null;
            }
         }

         float var19;
         float var26;
         if(var4 != null) {
            if(var4.field_72308_g != null) {
               var19 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
               int var23 = MathHelper.func_76143_f((double)var19 * this.field_70255_ao);
               if(this.func_70241_g()) {
                  var23 += this.field_70146_Z.nextInt(var23 / 2 + 2);
               }

               DamageSource var21 = null;
               if(this.field_70250_c == null) {
                  var21 = DamageSource.func_76353_a(this, this);
               } else {
                  var21 = DamageSource.func_76353_a(this, this.field_70250_c);
               }

               if(this.func_70027_ad() && !(var4.field_72308_g instanceof EntityEnderman)) {
                  var4.field_72308_g.func_70015_d(5);
               }

               if(var4.field_72308_g.func_70097_a(var21, (float)var23)) {
                  if(var4.field_72308_g instanceof EntityLivingBase) {
                     EntityLivingBase var24 = (EntityLivingBase)var4.field_72308_g;
                     if(!this.field_70170_p.field_72995_K) {
                        var24.func_85034_r(var24.func_85035_bI() + 1);
                     }

                     if(this.field_70256_ap > 0) {
                        var26 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                        if(var26 > 0.0F) {
                           var4.field_72308_g.func_70024_g(this.field_70159_w * (double)this.field_70256_ap * 0.6000000238418579D / (double)var26, 0.1D, this.field_70179_y * (double)this.field_70256_ap * 0.6000000238418579D / (double)var26);
                        }
                     }

                     if(this.field_70250_c != null && this.field_70250_c instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a(var24, this.field_70250_c);
                        EnchantmentHelper.func_151385_b((EntityLivingBase)this.field_70250_c, var24);
                     }

                     if(this.field_70250_c != null && var4.field_72308_g != this.field_70250_c && var4.field_72308_g instanceof EntityPlayer && this.field_70250_c instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.field_70250_c).field_71135_a.func_147359_a(new S2BPacketChangeGameState(6, 0.0F));
                     }
                  }

                  this.func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
                  if(!(var4.field_72308_g instanceof EntityEnderman)) {
                     this.func_70106_y();
                  }
               } else {
                  this.field_70159_w *= -0.10000000149011612D;
                  this.field_70181_x *= -0.10000000149011612D;
                  this.field_70179_y *= -0.10000000149011612D;
                  this.field_70177_z += 180.0F;
                  this.field_70126_B += 180.0F;
                  this.field_70257_an = 0;
               }
            } else {
               this.field_145791_d = var4.field_72311_b;
               this.field_145792_e = var4.field_72312_c;
               this.field_145789_f = var4.field_72309_d;
               this.field_145790_g = var16;
               this.field_70253_h = this.field_70170_p.func_72805_g(this.field_145791_d, this.field_145792_e, this.field_145789_f);
               this.field_70159_w = (double)((float)(var4.field_72307_f.field_72450_a - this.field_70165_t));
               this.field_70181_x = (double)((float)(var4.field_72307_f.field_72448_b - this.field_70163_u));
               this.field_70179_y = (double)((float)(var4.field_72307_f.field_72449_c - this.field_70161_v));
               var19 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
               this.field_70165_t -= this.field_70159_w / (double)var19 * 0.05000000074505806D;
               this.field_70163_u -= this.field_70181_x / (double)var19 * 0.05000000074505806D;
               this.field_70161_v -= this.field_70179_y / (double)var19 * 0.05000000074505806D;
               this.func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
               this.field_70254_i = true;
               this.field_70249_b = 7;
               this.func_70243_d(false);
               if(this.field_145790_g.func_149688_o() != Material.field_151579_a) {
                  this.field_145790_g.func_149670_a(this.field_70170_p, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
               }
            }
         }

         if(this.func_70241_g()) {
            for(var9 = 0; var9 < 4; ++var9) {
               this.field_70170_p.func_72869_a("crit", this.field_70165_t + this.field_70159_w * (double)var9 / 4.0D, this.field_70163_u + this.field_70181_x * (double)var9 / 4.0D, this.field_70161_v + this.field_70179_y * (double)var9 / 4.0D, -this.field_70159_w, -this.field_70181_x + 0.2D, -this.field_70179_y);
            }
         }

         this.field_70165_t += this.field_70159_w;
         this.field_70163_u += this.field_70181_x;
         this.field_70161_v += this.field_70179_y;
         var19 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / 3.1415927410125732D);

         for(this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)var19) * 180.0D / 3.1415927410125732D); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
            ;
         }

         while(this.field_70125_A - this.field_70127_C >= 180.0F) {
            this.field_70127_C += 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B < -180.0F) {
            this.field_70126_B -= 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B >= 180.0F) {
            this.field_70126_B += 360.0F;
         }

         this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
         this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
         float var22 = 0.99F;
         var11 = 0.05F;
         if(this.func_70090_H()) {
            for(int var25 = 0; var25 < 4; ++var25) {
               var26 = 0.25F;
               this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * (double)var26, this.field_70163_u - this.field_70181_x * (double)var26, this.field_70161_v - this.field_70179_y * (double)var26, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            }

            var22 = 0.8F;
         }

         if(this.func_70026_G()) {
            this.func_70066_B();
         }

         this.field_70159_w *= (double)var22;
         this.field_70181_x *= (double)var22;
         this.field_70179_y *= (double)var22;
         this.field_70181_x -= (double)var11;
         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         this.func_145775_I();
      }
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74777_a("xTile", (short)this.field_145791_d);
      p_70014_1_.func_74777_a("yTile", (short)this.field_145792_e);
      p_70014_1_.func_74777_a("zTile", (short)this.field_145789_f);
      p_70014_1_.func_74777_a("life", (short)this.field_70252_j);
      p_70014_1_.func_74774_a("inTile", (byte)Block.func_149682_b(this.field_145790_g));
      p_70014_1_.func_74774_a("inData", (byte)this.field_70253_h);
      p_70014_1_.func_74774_a("shake", (byte)this.field_70249_b);
      p_70014_1_.func_74774_a("inGround", (byte)(this.field_70254_i?1:0));
      p_70014_1_.func_74774_a("pickup", (byte)this.field_70251_a);
      p_70014_1_.func_74780_a("damage", this.field_70255_ao);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_145791_d = p_70037_1_.func_74765_d("xTile");
      this.field_145792_e = p_70037_1_.func_74765_d("yTile");
      this.field_145789_f = p_70037_1_.func_74765_d("zTile");
      this.field_70252_j = p_70037_1_.func_74765_d("life");
      this.field_145790_g = Block.func_149729_e(p_70037_1_.func_74771_c("inTile") & 255);
      this.field_70253_h = p_70037_1_.func_74771_c("inData") & 255;
      this.field_70249_b = p_70037_1_.func_74771_c("shake") & 255;
      this.field_70254_i = p_70037_1_.func_74771_c("inGround") == 1;
      if(p_70037_1_.func_150297_b("damage", 99)) {
         this.field_70255_ao = p_70037_1_.func_74769_h("damage");
      }

      if(p_70037_1_.func_150297_b("pickup", 99)) {
         this.field_70251_a = p_70037_1_.func_74771_c("pickup");
      } else if(p_70037_1_.func_150297_b("player", 99)) {
         this.field_70251_a = p_70037_1_.func_74767_n("player")?1:0;
      }

   }

   public void func_70100_b_(EntityPlayer p_70100_1_) {
      if(!this.field_70170_p.field_72995_K && this.field_70254_i && this.field_70249_b <= 0) {
         boolean var2 = this.field_70251_a == 1 || this.field_70251_a == 2 && p_70100_1_.field_71075_bZ.field_75098_d;
         if(this.field_70251_a == 1 && !p_70100_1_.field_71071_by.func_70441_a(new ItemStack(Items.field_151032_g, 1))) {
            var2 = false;
         }

         if(var2) {
            this.func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            p_70100_1_.func_71001_a(this, 1);
            this.func_70106_y();
         }

      }
   }

   protected boolean func_70041_e_() {
      return false;
   }

   public void func_70239_b(double p_70239_1_) {
      this.field_70255_ao = p_70239_1_;
   }

   public double func_70242_d() {
      return this.field_70255_ao;
   }

   public void func_70240_a(int p_70240_1_) {
      this.field_70256_ap = p_70240_1_;
   }

   public boolean func_70075_an() {
      return false;
   }

   public void func_70243_d(boolean p_70243_1_) {
      byte var2 = this.field_70180_af.func_75683_a(16);
      if(p_70243_1_) {
         this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(var2 | 1)));
      } else {
         this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(var2 & -2)));
      }

   }

   public boolean func_70241_g() {
      byte var1 = this.field_70180_af.func_75683_a(16);
      return (var1 & 1) != 0;
   }
}
