package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0EPacketSpawnObject extends Packet {

   private int field_149018_a;
   private int field_149016_b;
   private int field_149017_c;
   private int field_149014_d;
   private int field_149015_e;
   private int field_149012_f;
   private int field_149013_g;
   private int field_149021_h;
   private int field_149022_i;
   private int field_149019_j;
   private int field_149020_k;
   private static final String __OBFID = "CL_00001276";


   public S0EPacketSpawnObject() {}

   public S0EPacketSpawnObject(Entity p_i45165_1_, int p_i45165_2_) {
      this(p_i45165_1_, p_i45165_2_, 0);
   }

   public S0EPacketSpawnObject(Entity p_i45166_1_, int p_i45166_2_, int p_i45166_3_) {
      this.field_149018_a = p_i45166_1_.func_145782_y();
      this.field_149016_b = MathHelper.func_76128_c(p_i45166_1_.field_70165_t * 32.0D);
      this.field_149017_c = MathHelper.func_76128_c(p_i45166_1_.field_70163_u * 32.0D);
      this.field_149014_d = MathHelper.func_76128_c(p_i45166_1_.field_70161_v * 32.0D);
      this.field_149021_h = MathHelper.func_76141_d(p_i45166_1_.field_70125_A * 256.0F / 360.0F);
      this.field_149022_i = MathHelper.func_76141_d(p_i45166_1_.field_70177_z * 256.0F / 360.0F);
      this.field_149019_j = p_i45166_2_;
      this.field_149020_k = p_i45166_3_;
      if(p_i45166_3_ > 0) {
         double var4 = p_i45166_1_.field_70159_w;
         double var6 = p_i45166_1_.field_70181_x;
         double var8 = p_i45166_1_.field_70179_y;
         double var10 = 3.9D;
         if(var4 < -var10) {
            var4 = -var10;
         }

         if(var6 < -var10) {
            var6 = -var10;
         }

         if(var8 < -var10) {
            var8 = -var10;
         }

         if(var4 > var10) {
            var4 = var10;
         }

         if(var6 > var10) {
            var6 = var10;
         }

         if(var8 > var10) {
            var8 = var10;
         }

         this.field_149015_e = (int)(var4 * 8000.0D);
         this.field_149012_f = (int)(var6 * 8000.0D);
         this.field_149013_g = (int)(var8 * 8000.0D);
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149018_a = p_148837_1_.func_150792_a();
      this.field_149019_j = p_148837_1_.readByte();
      this.field_149016_b = p_148837_1_.readInt();
      this.field_149017_c = p_148837_1_.readInt();
      this.field_149014_d = p_148837_1_.readInt();
      this.field_149021_h = p_148837_1_.readByte();
      this.field_149022_i = p_148837_1_.readByte();
      this.field_149020_k = p_148837_1_.readInt();
      if(this.field_149020_k > 0) {
         this.field_149015_e = p_148837_1_.readShort();
         this.field_149012_f = p_148837_1_.readShort();
         this.field_149013_g = p_148837_1_.readShort();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149018_a);
      p_148840_1_.writeByte(this.field_149019_j);
      p_148840_1_.writeInt(this.field_149016_b);
      p_148840_1_.writeInt(this.field_149017_c);
      p_148840_1_.writeInt(this.field_149014_d);
      p_148840_1_.writeByte(this.field_149021_h);
      p_148840_1_.writeByte(this.field_149022_i);
      p_148840_1_.writeInt(this.field_149020_k);
      if(this.field_149020_k > 0) {
         p_148840_1_.writeShort(this.field_149015_e);
         p_148840_1_.writeShort(this.field_149012_f);
         p_148840_1_.writeShort(this.field_149013_g);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_149011_1_) {
      p_149011_1_.func_147235_a(this);
   }

   public String func_148835_b() {
      return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f", new Object[]{Integer.valueOf(this.field_149018_a), Integer.valueOf(this.field_149019_j), Float.valueOf((float)this.field_149016_b / 32.0F), Float.valueOf((float)this.field_149017_c / 32.0F), Float.valueOf((float)this.field_149014_d / 32.0F)});
   }

   public void func_148996_a(int p_148996_1_) {
      this.field_149016_b = p_148996_1_;
   }

   public void func_148995_b(int p_148995_1_) {
      this.field_149017_c = p_148995_1_;
   }

   public void func_149005_c(int p_149005_1_) {
      this.field_149014_d = p_149005_1_;
   }

   public void func_149003_d(int p_149003_1_) {
      this.field_149015_e = p_149003_1_;
   }

   public void func_149000_e(int p_149000_1_) {
      this.field_149012_f = p_149000_1_;
   }

   public void func_149007_f(int p_149007_1_) {
      this.field_149013_g = p_149007_1_;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_148833_a((INetHandlerPlayClient)p_148833_1_);
   }
}
