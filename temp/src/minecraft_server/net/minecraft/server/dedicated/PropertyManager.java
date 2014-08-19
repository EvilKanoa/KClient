package net.minecraft.server.dedicated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyManager {

   private static final Logger field_164440_a = LogManager.getLogger();
   private final Properties field_73672_b = new Properties();
   private final File field_73673_c;
   private static final String __OBFID = "CL_00001782";


   public PropertyManager(File p_i45278_1_) {
      this.field_73673_c = p_i45278_1_;
      if(p_i45278_1_.exists()) {
         FileInputStream var2 = null;

         try {
            var2 = new FileInputStream(p_i45278_1_);
            this.field_73672_b.load(var2);
         } catch (Exception var12) {
            field_164440_a.warn("Failed to load " + p_i45278_1_, var12);
            this.func_73666_a();
         } finally {
            if(var2 != null) {
               try {
                  var2.close();
               } catch (IOException var11) {
                  ;
               }
            }

         }
      } else {
         field_164440_a.warn(p_i45278_1_ + " does not exist");
         this.func_73666_a();
      }

   }

   public void func_73666_a() {
      field_164440_a.info("Generating new properties file");
      this.func_73668_b();
   }

   public void func_73668_b() {
      FileOutputStream var1 = null;

      try {
         var1 = new FileOutputStream(this.field_73673_c);
         this.field_73672_b.store(var1, "Minecraft server properties");
      } catch (Exception var11) {
         field_164440_a.warn("Failed to save " + this.field_73673_c, var11);
         this.func_73666_a();
      } finally {
         if(var1 != null) {
            try {
               var1.close();
            } catch (IOException var10) {
               ;
            }
         }

      }

   }

   public File func_73665_c() {
      return this.field_73673_c;
   }

   public String func_73671_a(String p_73671_1_, String p_73671_2_) {
      if(!this.field_73672_b.containsKey(p_73671_1_)) {
         this.field_73672_b.setProperty(p_73671_1_, p_73671_2_);
         this.func_73668_b();
         this.func_73668_b();
      }

      return this.field_73672_b.getProperty(p_73671_1_, p_73671_2_);
   }

   public int func_73669_a(String p_73669_1_, int p_73669_2_) {
      try {
         return Integer.parseInt(this.func_73671_a(p_73669_1_, "" + p_73669_2_));
      } catch (Exception var4) {
         this.field_73672_b.setProperty(p_73669_1_, "" + p_73669_2_);
         this.func_73668_b();
         return p_73669_2_;
      }
   }

   public boolean func_73670_a(String p_73670_1_, boolean p_73670_2_) {
      try {
         return Boolean.parseBoolean(this.func_73671_a(p_73670_1_, "" + p_73670_2_));
      } catch (Exception var4) {
         this.field_73672_b.setProperty(p_73670_1_, "" + p_73670_2_);
         this.func_73668_b();
         return p_73670_2_;
      }
   }

   public void func_73667_a(String p_73667_1_, Object p_73667_2_) {
      this.field_73672_b.setProperty(p_73667_1_, "" + p_73667_2_);
   }

}
