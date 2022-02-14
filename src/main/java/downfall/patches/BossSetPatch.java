package downfall.patches;

import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Merchant.CharBossMerchant;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
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
            AbstractDungeon.monsterList.replaceAll((s) -> {
                switch (s) {
                    case "Looter":
                        return downfallMod.makeID("LooterAlt");
                    case "2 Thieves":
                        return downfallMod.makeID("LooterAlt2");
                    default:
                        return s;
                }
            });
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
            if (__instance instanceof TheBeyond) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/ironclad_bastion.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/ironclad_bastion_outline.png");
            } else if (__instance instanceof TheCity) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/ironclad_shroom.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/ironclad_shroom_outline.png");
            } else {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/ironclad_status.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/ironclad_status_outline.png");
            }
        } else if (key.equals(CharBossSilent.ID)) {
            if (__instance instanceof TheBeyond) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/silent_poison.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/silent_poison_outline.png");
            } else if (__instance instanceof TheCity) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/silent_mirror_image.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/silent_mirror_image_outline.png");
            } else {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/silent_shiv.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/silent_shiv_outline.png");
            }
        } else if (key.equals(CharBossDefect.ID)) {
            if (__instance instanceof TheBeyond) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/defect_focus.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/defect_focus_outline.png");
            } else if (__instance instanceof TheCity) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/defect_bronze_orbs.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/defect_bronze_orbs_outline.png");
            } else {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/defect_void.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/defect_void_outline.png");
            }
        } else if (key.equals(CharBossWatcher.ID)) {
            if (__instance instanceof TheBeyond) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/watcher_divinity.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/watcher_divinity_outline.png");
            } else if (__instance instanceof TheCity) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/watcher_blasphemy.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/watcher_blasphemy_outline.png");
            } else {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/watcher_wrath.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/watcher_wrath_outline.png");
            }
        }  else if (key.equals(CharBossHermit.ID)) {
            if (__instance instanceof TheBeyond) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/hermit_curse.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/hermit_curse_outline.png");
            } else if (__instance instanceof TheCity) {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/hermit_wheel.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/hermit_wheel_outline.png");
            } else {
                DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/hermit_sharpshooter.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/hermit_sharpshooter_outline.png");
            }
        } else if (key.equals(NeowBoss.ID)) {
            DungeonMap.boss = ImageMaster.loadImage("downfallResources/images/ui/map/icon/neow.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("downfallResources/images/ui/map/outline/neow_outline.png");
        }
    }
}