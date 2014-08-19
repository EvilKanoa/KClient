package net.minecraft.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class Util {

   private static final Pattern field_147174_a = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
   private static final String __OBFID = "CL_00001633";


   public static boolean func_147172_a(String p_147172_0_) {
      return field_147174_a.matcher(p_147172_0_).matches();
   }

   public static UUID func_147173_b(String p_147173_0_) {
      if(p_147173_0_ == null) {
         return null;
      } else if(func_147172_a(p_147173_0_)) {
         return UUID.fromString(p_147173_0_);
      } else {
         if(p_147173_0_.length() == 32) {
            String var1 = p_147173_0_.substring(0, 8) + "-" + p_147173_0_.substring(8, 12) + "-" + p_147173_0_.substring(12, 16) + "-" + p_147173_0_.substring(16, 20) + "-" + p_147173_0_.substring(20, 32);
            if(func_147172_a(var1)) {
               return UUID.fromString(var1);
            }
         }

         return null;
      }
   }

}
