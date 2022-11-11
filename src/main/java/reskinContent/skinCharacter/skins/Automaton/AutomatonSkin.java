package reskinContent.skinCharacter.skins.Automaton;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import reskinContent.skinCharacter.AbstractSkin;
import reskinContent.skinCharacter.AbstractSkinCharacter;
import reskinContent.skinCharacter.skins.Automaton.AutomationOriginal;
import reskinContent.skinCharacter.skins.Automaton.BetaAutomaton;
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
        if (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_AUTOMATON && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(5));
            this.reskinUnlock = true;
        }
    }
}


