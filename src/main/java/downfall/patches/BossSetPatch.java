package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;

@SpirePatch(clz = AbstractDungeon.class, method = "setBoss")
public class BossSetPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon __instance, String key) {
        if (__instance instanceof TheEnding && EvilModeCharacterSelect.evilMode) {

            AbstractDungeon.bossList.clear();

            AbstractDungeon.bossList.add("downfall:NeowBoss");
            AbstractDungeon.bossList.add("downfall:NeowBoss");
            AbstractDungeon.bossList.add("downfall:NeowBoss");


            AbstractDungeon.monsterList.clear();
            AbstractDungeon.eliteMonsterList.clear();

            AbstractDungeon.monsterList.add("downfall:CharBossMerchant");
            AbstractDungeon.monsterList.add("downfall:CharBossMerchant");
            AbstractDungeon.monsterList.add("downfall:CharBossMerchant");
            AbstractDungeon.eliteMonsterList.add("downfall:CharBossMerchant");
            AbstractDungeon.eliteMonsterList.add("downfall:CharBossMerchant");
            AbstractDungeon.eliteMonsterList.add("downfall:CharBossMerchant");

            key = "downfall:NeowBoss";

            AbstractDungeon.bossKey = key;
        }

        switch (key) {
            case "downfall:CharBossIronclad": {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/ironclad.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/ironcladoutline.png");// 433
                break;
            }
            case "downfall:CharBossSilent": {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/silent.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/silentoutline.png");// 433
                break;
            }
            case "downfall:CharBossDefect": {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/defect.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/defectoutline.png");// 433
                break;
            }
            case "downfall:CharBossWatcher": {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/watcher.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/watcheroutline.png");// 433
                break;
            }
            case "downfall:NeowBoss": {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/neow.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/neowoutline.png");// 433
                break;
            }
        }
    }
}