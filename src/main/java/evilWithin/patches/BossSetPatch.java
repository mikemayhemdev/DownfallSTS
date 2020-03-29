package evilWithin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;

@SpirePatch(clz = AbstractDungeon.class, method = "setBoss")

public class BossSetPatch {
    public static void Postfix(AbstractDungeon __instance, String key) {
        switch (key) {
            case "EvilWithin:CharBossIronclad": {
                DungeonMap.boss = ImageMaster.loadImage("evilWithinResources/images/ui/map/ironclad.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("evilWithinResources/images/ui/map/ironcladoutline.png");// 433
                break;
            }
            case "EvilWithin:CharBossSilent": {
                DungeonMap.boss = ImageMaster.loadImage("evilWithinResources/images/ui/map/silent.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("evilWithinResources/images/ui/map/silentoutline.png");// 433
                break;
            }
            case "EvilWithin:CharBossDefect": {
                DungeonMap.boss = ImageMaster.loadImage("evilWithinResources/images/ui/map/defect.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("evilWithinResources/images/ui/map/defectoutline.png");// 433
                break;
            }
            case "EvilWithin:CharBossWatcher": {
                DungeonMap.boss = ImageMaster.loadImage("evilWithinResources/images/ui/map/watcher.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("evilWithinResources/images/ui/map/watcheroutline.png");// 433
                break;
            }

        }
    }
}