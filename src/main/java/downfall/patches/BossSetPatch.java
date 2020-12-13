package downfall.patches;

import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Merchant.CharBossMerchant;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;

@SpirePatch(clz = AbstractDungeon.class, method = "setBoss")
public class BossSetPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon __instance, String key) {
        if (EvilModeCharacterSelect.evilMode) {
            boolean nukeLooter = false;
            boolean nukeLooter2 = false;

            for (String s : AbstractDungeon.monsterList) {
                if (s.equals("Looter")) {
                    nukeLooter = true;
                }

                if (s.equals("2 Thieves")) {
                    nukeLooter2 = true;
                }

            }

            if (nukeLooter) {
                AbstractDungeon.monsterList.remove("Looter");
                AbstractDungeon.monsterList.add(downfallMod.makeID("LooterAlt"));

            }

            if (nukeLooter2) {
                AbstractDungeon.monsterList.remove("2 Thieves");
                AbstractDungeon.monsterList.add(downfallMod.makeID("LooterAlt2"));

            }


        }
        if (__instance instanceof TheEnding && EvilModeCharacterSelect.evilMode) {

            AbstractDungeon.bossList.clear();

            AbstractDungeon.bossList.add(NeowBoss.ID);
            AbstractDungeon.bossList.add(NeowBoss.ID);
            AbstractDungeon.bossList.add(NeowBoss.ID);

            AbstractDungeon.monsterList.clear();
            AbstractDungeon.eliteMonsterList.clear();

            AbstractDungeon.monsterList.add(CharBossMerchant.ID);
            AbstractDungeon.monsterList.add(CharBossMerchant.ID);
            AbstractDungeon.monsterList.add(CharBossMerchant.ID);
            AbstractDungeon.eliteMonsterList.add(CharBossMerchant.ID);
            AbstractDungeon.eliteMonsterList.add(CharBossMerchant.ID);
            AbstractDungeon.eliteMonsterList.add(CharBossMerchant.ID);

            key = NeowBoss.ID;

            AbstractDungeon.bossKey = key;
        }


        if (key.equals(CharBossIronclad.ID)) {
            DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/ironclad.png");// 432
            DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/ironcladoutline.png");// 433
        } else if (key.equals(CharBossSilent.ID)) {
            DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/silent.png");// 432
            DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/silentoutline.png");// 433
        } else if (key.equals(CharBossDefect.ID)) {
            DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/defect.png");// 432
            DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/defectoutline.png");// 433
        } else if (key.equals(CharBossWatcher.ID)) {
            DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/watcher.png");// 432
            DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/watcheroutline.png");// 433
        } else if (key.equals(NeowBoss.ID)) {
            DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/neow.png");// 432
            DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/neowoutline.png");// 433
        }
    }
}