package reskinContent.skinCharacter;

import automaton.AutomatonChar;
import awakenedOne.AwakenedOneChar;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import reskinContent.skinCharacter.skins.Automaton.AutomationOriginal;
import reskinContent.skinCharacter.skins.Automaton.BetaAutomaton;
import reskinContent.skinCharacter.skins.Automaton.ThePerfect;
import reskinContent.skinCharacter.skins.AwakenedOne.AwakenedOneOriginal;
import reskinContent.vfx.ReskinUnlockedTextEffect;


public class AwakenedOneSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("awakenedOne").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new AwakenedOneOriginal()
    };

    public AwakenedOneSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == AwakenedOneChar.Enums.AWAKENED_ONE && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(5));
            this.reskinUnlock = true;
        }
    }
}


