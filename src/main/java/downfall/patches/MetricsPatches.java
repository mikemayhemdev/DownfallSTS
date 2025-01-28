package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import downfall.downfallMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MetricsPatches {

   private static final Logger logger = LogManager.getLogger(MetricsPatches.class);
    private static final String url = "http://downfallstats.atwebpages.com/beta/";

   @SpirePatch(clz = GameOverScreen.class, method = "shouldUploadMetricData")
  public static class ShouldUploadMetricData {
       public static boolean Postfix(boolean returnValue) {
           if (downfallMod.isDownfallCharacter(AbstractDungeon.player)) {
               returnValue = Settings.UPLOAD_DATA;
           }
           return returnValue;
        }
  }
@SpirePatch(clz = Metrics.class, method = "run")
  public static class RunPatch {
       public static void Postfix(Metrics metrics) {
         if (metrics.type == Metrics.MetricRequestType.UPLOAD_METRICS && downfallMod.isDownfallCharacter(AbstractDungeon.player)) {
              try {
                  Method m = Metrics.class.getDeclaredMethod("sendPost", String.class, String.class);
                    m.setAccessible(true);
                m.invoke(metrics, url, null);
               } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                   logger.error("Exception while sending metrics", e);
             }
           }
       }

   }
}