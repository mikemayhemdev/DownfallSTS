package champ.patches;

import champ.ChampChar;
import champ.ChampMod;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Ironclad.NewAge.ArchetypeAct1StatusesNewAge;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.beyond.Deca;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

@SpirePatch(clz = CharBossIronclad.class, method = "takeTurn")
public class ChampTalkIronclad1 {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("champ:ChampTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(CharBossIronclad sb) {
        if (AbstractDungeon.player instanceof ChampChar && AbstractDungeon.actNum == 1) {
            if (!ChampMod.talked1 && !ChampMod.talked2) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[0], 1.0F, 2.0F));
                ChampMod.talked1 = true;
            } else if (!ChampMod.talked2) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[1], 1.0F, 2.0F));
                ChampMod.talked2 = true;
            }
        }
    }
}