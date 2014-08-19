package net.minecraft.item;

import net.minecraft.block.BlockSkull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSkull extends Item {

   private static final String[] field_82807_a = new String[]{"skeleton", "wither", "zombie", "char", "creeper"};
   public static final String[] field_94587_a = new String[]{"skeleton", "wither", "zombie", "steve", "creeper"};
   private static final String __OBFID = "CL_00000067";


   public ItemSkull() {
      this.func_77637_a(CreativeTabs.field_78031_c);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      if(p_77648_7_ == 0) {
         return false;
      } else if(!p_77648_3_.func_147439_a(p_77648_4_, p_77648_5_, p_77648_6_).func_149688_o().func_76220_a()) {
         return false;
      } else {
         if(p_77648_7_ == 1) {
            ++p_77648_5_;
         }

         if(p_77648_7_ == 2) {
            --p_77648_6_;
         }

         if(p_77648_7_ == 3) {
            ++p_77648_6_;
         }

         if(p_77648_7_ == 4) {
            --p_77648_4_;
         }

         if(p_77648_7_ == 5) {
            ++p_77648_4_;
         }

         if(!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
         } else if(!Blocks.field_150465_bP.func_149742_c(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) {
            return false;
         } else {
            p_77648_3_.func_147465_d(p_77648_4_, p_77648_5_, p_77648_6_, Blocks.field_150465_bP, p_77648_7_, 2);
            int var11 = 0;
            if(p_77648_7_ == 1) {
               var11 = MathHelper.func_76128_c((double)(p_77648_2_.field_70177_z * 16.0F / 360.0F) + 0.5D) & 15;
            }

            TileEntity var12 = p_77648_3_.func_147438_o(p_77648_4_, p_77648_5_, p_77648_6_);
            if(var12 != null && var12 instanceof TileEntitySkull) {
               String var13 = "";
               if(p_77648_1_.func_77942_o() && p_77648_1_.func_77978_p().func_150297_b("SkullOwner", 8)) {
                  var13 = p_77648_1_.func_77978_p().func_74779_i("SkullOwner");
               }

               ((TileEntitySkull)var12).func_145905_a(p_77648_1_.func_77960_j(), var13);
               ((TileEntitySkull)var12).func_145903_a(var11);
               ((BlockSkull)Blocks.field_150465_bP).func_149965_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, (TileEntitySkull)var12);
            }

            --p_77648_1_.field_77994_a;
            return true;
         }
      }
   }

   public int func_77647_b(int p_77647_1_) {
      return p_77647_1_;
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      int var2 = p_77667_1_.func_77960_j();
      if(var2 < 0 || var2 >= field_82807_a.length) {
         var2 = 0;
      }

      return super.func_77658_a() + "." + field_82807_a[var2];
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      return p_77653_1_.func_77960_j() == 3 && p_77653_1_.func_77942_o() && p_77653_1_.func_77978_p().func_150297_b("SkullOwner", 8)?StatCollector.func_74837_a("item.skull.player.name", new Object[]{p_77653_1_.func_77978_p().func_74779_i("SkullOwner")}):super.func_77653_i(p_77653_1_);
   }

}
