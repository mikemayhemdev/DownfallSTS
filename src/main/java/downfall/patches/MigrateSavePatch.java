package downfall.patches;

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
        if (downfallMod.STEAM_MODE && !SaveHelper.saveExists()) {
            System.out.println("VEX LOOK HERE! -> -> -> Attempting to migrate save.");
            String q = Gdx.files.getLocalStoragePath();
            q = q + "preferences";
            System.out.println(q);
            new File(q).mkdirs();
            String result = q.split(String.format("((?<=%1$s)|(?=%1$s))", "common"))[0] + "common\\";
            System.out.println(result);
            String path = result + "SlayTheSpire\\preferences";
            System.out.println(path);
            try {
                System.out.println("COPYING DIRECTORY...");
                copyDirectory(new File(path), new File(q));
                System.out.println("SAVE MIGRATION SUCCESS!");
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
            System.out.println(files.toString());
            for (int i = 0; i < files.length; i++) {
                copyDirectory(new File(sourceLocation, files[i]),
                        new File(targetLocation, files[i]));
            }
        } else {
            System.out.println(sourceLocation);
            System.out.println(targetLocation);
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
