package net.minecraft.client.gui.mco;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfigureWorld;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.mco.Backup;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBackup extends GuiScreen {

   private static final AtomicInteger field_146845_a = new AtomicInteger(0);
   private static final Logger field_146841_f = LogManager.getLogger();
   private final GuiScreenConfigureWorld field_146842_g;
   private final long field_146846_h;
   private List field_146847_i = Collections.emptyList();
   private GuiScreenBackup.SelectionList field_146844_r;
   private int field_146843_s = -1;
   private static final String __OBFID = "CL_00000766";


   public GuiScreenBackup(GuiScreenConfigureWorld p_i1103_1_, long p_i1103_2_) {
      this.field_146842_g = p_i1103_1_;
      this.field_146846_h = p_i1103_2_;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_146844_r = new GuiScreenBackup.SelectionList();
      (new Thread("MCO Backup Requester #" + field_146845_a.incrementAndGet()) {

         private static final String __OBFID = "CL_00000767";

         public void run() {
            Session var1 = GuiScreenBackup.this.field_146297_k.func_110432_I();
            McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());

            try {
               GuiScreenBackup.this.field_146847_i = var2.func_148704_d(GuiScreenBackup.this.field_146846_h).field_148797_a;
            } catch (ExceptionMcoService var4) {
               GuiScreenBackup.field_146841_f.error("Couldn\'t request backups", var4);
            }

         }
      }).start();
      this.func_146840_h();
   }

   private void func_146840_h() {
      this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 + 6, this.field_146295_m - 52, 153, 20, I18n.func_135052_a("mco.backup.button.download", new Object[0])));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 153, 20, I18n.func_135052_a("mco.backup.button.restore", new Object[0])));
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 74, this.field_146295_m - 52 + 25, 153, 20, I18n.func_135052_a("gui.back", new Object[0])));
   }

   public void func_73876_c() {
      super.func_73876_c();
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == 1) {
            this.func_146827_i();
         } else if(p_146284_1_.field_146127_k == 0) {
            this.field_146297_k.func_147108_a(this.field_146842_g);
         } else if(p_146284_1_.field_146127_k == 2) {
            this.func_146826_p();
         } else {
            this.field_146844_r.func_148357_a(p_146284_1_);
         }

      }
   }

   private void func_146827_i() {
      if(this.field_146843_s >= 0 && this.field_146843_s < this.field_146847_i.size()) {
         Date var1 = ((Backup)this.field_146847_i.get(this.field_146843_s)).field_148778_b;
         String var2 = DateFormat.getDateTimeInstance(3, 3).format(var1);
         String var3 = this.func_146829_a(Long.valueOf(System.currentTimeMillis() - var1.getTime()));
         String var4 = I18n.func_135052_a("mco.configure.world.restore.question.line1", new Object[0]) + " \'" + var2 + "\' (" + var3 + ")";
         String var5 = I18n.func_135052_a("mco.configure.world.restore.question.line2", new Object[0]);
         this.field_146297_k.func_147108_a(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, var4, var5, 1));
      }

   }

   private void func_146826_p() {
      String var1 = I18n.func_135052_a("mco.configure.world.restore.download.question.line1", new Object[0]);
      String var2 = I18n.func_135052_a("mco.configure.world.restore.download.question.line2", new Object[0]);
      this.field_146297_k.func_147108_a(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var1, var2, 2));
   }

   private void func_146821_q() {
      Session var1 = this.field_146297_k.func_110432_I();
      McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());

      try {
         String var3 = var2.func_148708_h(this.field_146846_h);
         Clipboard var4 = Toolkit.getDefaultToolkit().getSystemClipboard();
         var4.setContents(new StringSelection(var3), (ClipboardOwner)null);
         this.func_146823_a(var3);
      } catch (ExceptionMcoService var5) {
         field_146841_f.error("Couldn\'t download world data");
      }

   }

   private void func_146823_a(String p_146823_1_) {
      try {
         URI var2 = new URI(p_146823_1_);
         Class var3 = Class.forName("java.awt.Desktop");
         Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         var3.getMethod("browse", new Class[]{URI.class}).invoke(var4, new Object[]{var2});
      } catch (Throwable var5) {
         field_146841_f.error("Couldn\'t open link");
      }

   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if(p_73878_1_ && p_73878_2_ == 1) {
         this.func_146839_r();
      } else if(p_73878_1_ && p_73878_2_ == 2) {
         this.func_146821_q();
      } else {
         this.field_146297_k.func_147108_a(this);
      }

   }

   private void func_146839_r() {
      Backup var1 = (Backup)this.field_146847_i.get(this.field_146843_s);
      GuiScreenBackup.RestoreTask var2 = new GuiScreenBackup.RestoreTask(var1, null);
      GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.field_146297_k, this.field_146842_g, var2);
      var3.func_146902_g();
      this.field_146297_k.func_147108_a(var3);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.field_146844_r.func_148350_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("mco.backup.title", new Object[0]), this.field_146294_l / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   private String func_146829_a(Long p_146829_1_) {
      if(p_146829_1_.longValue() < 0L) {
         return "right now";
      } else {
         long var2 = p_146829_1_.longValue() / 1000L;
         if(var2 < 60L) {
            return (var2 == 1L?"1 second":var2 + " seconds") + " ago";
         } else {
            long var4;
            if(var2 < 3600L) {
               var4 = var2 / 60L;
               return (var4 == 1L?"1 minute":var4 + " minutes") + " ago";
            } else if(var2 < 86400L) {
               var4 = var2 / 3600L;
               return (var4 == 1L?"1 hour":var4 + " hours") + " ago";
            } else {
               var4 = var2 / 86400L;
               return (var4 == 1L?"1 day":var4 + " days") + " ago";
            }
         }
      }
   }


   class RestoreTask extends TaskLongRunning {

      private final Backup field_148424_c;
      private static final String __OBFID = "CL_00000769";


      private RestoreTask(Backup p_i1101_2_) {
         this.field_148424_c = p_i1101_2_;
      }

      public void run() {
         this.func_148417_b(I18n.func_135052_a("mco.backup.restoring", new Object[0]));

         try {
            if(this.func_148418_c()) {
               return;
            }

            Session var1 = GuiScreenBackup.this.field_146297_k.func_110432_I();
            McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());
            var2.func_148712_c(GuiScreenBackup.this.field_146846_h, this.field_148424_c.field_148780_a);

            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var4) {
               Thread.currentThread().interrupt();
            }

            if(this.func_148418_c()) {
               return;
            }

            this.func_148413_b().func_147108_a(GuiScreenBackup.this.field_146842_g);
         } catch (ExceptionMcoService var5) {
            if(this.func_148418_c()) {
               return;
            }

            GuiScreenBackup.field_146841_f.error("Couldn\'t restore backup");
            this.func_148416_a(var5.toString());
         } catch (Exception var6) {
            if(this.func_148418_c()) {
               return;
            }

            GuiScreenBackup.field_146841_f.error("Couldn\'t restore backup");
            this.func_148416_a(var6.getLocalizedMessage());
         }

      }

      // $FF: synthetic method
      RestoreTask(Backup p_i1102_2_, Object p_i1102_3_) {
         this(p_i1102_2_);
      }
   }

   class SelectionList extends GuiScreenSelectLocation {

      private static final String __OBFID = "CL_00000768";


      public SelectionList() {
         super(GuiScreenBackup.this.field_146297_k, GuiScreenBackup.this.field_146294_l, GuiScreenBackup.this.field_146295_m, 32, GuiScreenBackup.this.field_146295_m - 64, 36);
      }

      protected int func_148355_a() {
         return GuiScreenBackup.this.field_146847_i.size() + 1;
      }

      protected void func_148352_a(int p_148352_1_, boolean p_148352_2_) {
         if(p_148352_1_ < GuiScreenBackup.this.field_146847_i.size()) {
            GuiScreenBackup.this.field_146843_s = p_148352_1_;
         }
      }

      protected boolean func_148356_a(int p_148356_1_) {
         return p_148356_1_ == GuiScreenBackup.this.field_146843_s;
      }

      protected boolean func_148349_b(int p_148349_1_) {
         return false;
      }

      protected int func_148351_b() {
         return this.func_148355_a() * 36;
      }

      protected void func_148358_c() {
         GuiScreenBackup.this.func_146276_q_();
      }

      protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_) {
         if(p_148348_1_ < GuiScreenBackup.this.field_146847_i.size()) {
            this.func_148385_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
         }

      }

      private void func_148385_b(int p_148385_1_, int p_148385_2_, int p_148385_3_, int p_148385_4_, Tessellator p_148385_5_) {
         Backup var6 = (Backup)GuiScreenBackup.this.field_146847_i.get(p_148385_1_);
         GuiScreenBackup.this.func_73731_b(GuiScreenBackup.this.field_146289_q, "Backup (" + GuiScreenBackup.this.func_146829_a(Long.valueOf(MinecraftServer.func_130071_aq() - var6.field_148778_b.getTime())) + ")", p_148385_2_ + 2, p_148385_3_ + 1, 16777215);
         GuiScreenBackup.this.func_73731_b(GuiScreenBackup.this.field_146289_q, this.func_148384_a(var6.field_148778_b), p_148385_2_ + 2, p_148385_3_ + 12, 7105644);
      }

      private String func_148384_a(Date p_148384_1_) {
         return DateFormat.getDateTimeInstance(3, 3).format(p_148384_1_);
      }
   }
}
