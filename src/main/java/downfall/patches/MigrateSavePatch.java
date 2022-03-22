package downfall.patches;

import automaton.FunctionHelper;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import downfall.downfallMod;

import java.io.*;

@SpirePatch(clz = CardCrawlGame.class, method = "saveMigration")
public class MigrateSavePatch {
    @SpirePrefixPatch
    public static void Prefix(CardCrawlGame __instance) {
        if (!SaveHelper.saveExists() && downfallMod.STEAM_MODE) {
            String q = Gdx.files.getLocalStoragePath();
            String result = q.split(String.format(FunctionHelper.WITH_DELIMITER, "common"))[0];
            String path = result + "\\SlayTheSpire\\preferences";
            try {
                copyDirectory(new File(path), new File(q));
            } catch (IOException e) {
                System.out.println("SAVE MIGRATION FAILURE!");
                e.printStackTrace();
            }
        }
    }


    private static void copyDirectory(File sourceLocation, File targetLocation)
            throws IOException {
        if (sourceLocation.isDirectory()) {
            String[] files = sourceLocation.list();
            for (int i = 0; i < files.length; i++) {
                copyDirectory(new File(sourceLocation, files[i]),
                        new File(targetLocation, files[i]));
            }
        } else {
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }
}
