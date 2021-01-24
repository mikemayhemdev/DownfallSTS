package downfall.patches;

import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Merchant.CharBossMerchant;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import slimebound.SlimeboundMod;

@SpirePatch(clz = AbstractPlayer.class,
        method = SpirePatch.CONSTRUCTOR)
public class AddOrbForCustomRunsPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer __instance) {
        if (ModHelper.enabledMods.size() > 0){
            for (AbstractDailyMod m : ModHelper.enabledMods){
                SlimeboundMod.logger.info(m.modID);
            }
        }

        if ((ModHelper.enabledMods.size() > 0) &&
                ((ModHelper.isModEnabled("The Guardian Cards"))
                || (ModHelper.isModEnabled("The Slime Boss Cards"))
                )) {
            __instance.masterMaxOrbs = 1;
        }
    }
}