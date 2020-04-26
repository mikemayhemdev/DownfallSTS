package evilWithin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "setBoss")
public class BossSetPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon __instance, String key) {
        if (__instance instanceof TheEnding && EvilModeCharacterSelect.evilMode) {

            AbstractDungeon.bossList.clear();

            AbstractDungeon.bossList.add("EvilWithin:NeowBoss");
            AbstractDungeon.bossList.add("EvilWithin:NeowBoss");
            AbstractDungeon.bossList.add("EvilWithin:NeowBoss");


            AbstractDungeon.monsterList.clear();
            AbstractDungeon.eliteMonsterList.clear();

            AbstractDungeon.monsterList.add("EvilWithin:CharBossMerchant");
            AbstractDungeon.monsterList.add("EvilWithin:CharBossMerchant");
            AbstractDungeon.monsterList.add("EvilWithin:CharBossMerchant");
            AbstractDungeon.eliteMonsterList.add("EvilWithin:CharBossMerchant");
            AbstractDungeon.eliteMonsterList.add("EvilWithin:CharBossMerchant");
            AbstractDungeon.eliteMonsterList.add("EvilWithin:CharBossMerchant");

            key = "EvilWithin:NeowBoss";

            AbstractDungeon.bossKey = key;
        }

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
            case "EvilWithin:NeowBoss": {
                DungeonMap.boss = ImageMaster.loadImage("evilWithinResources/images/ui/map/neow.png");// 432
                DungeonMap.bossOutline = ImageMaster.loadImage("evilWithinResources/images/ui/map/neowoutline.png");// 433
                break;
            }
        }
    }
}