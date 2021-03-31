package reskinContent.skinCharacter;

import automaton.AutomatonChar;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import reskinContent.skinCharacter.skins.Automaton.*;
import reskinContent.vfx.ReskinUnlockedTextEffect;


public class AutomatonSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("bronze:theAutomaton").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new AutomationOriginal(),
            new BetaAutomaton()
    };

    public AutomatonSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == AutomatonChar.Enums.THE_AUTOMATON && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(5));
            this.reskinUnlock = true;
        }
    }
}


