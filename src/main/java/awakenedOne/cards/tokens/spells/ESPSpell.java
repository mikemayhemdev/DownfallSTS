package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class ESPSpell extends AbstractSpellCard {
    public final static String ID = makeID(ESPSpell.class.getSimpleName());
    // intellij stuff skill, all_enemy, , , , , 7, 2

    public ESPSpell() {
        super(ID, 0, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        this.setBackgroundTexture("awakenedResources/images/512/bg_skill_awakened.png", "awakenedResources/images/1024/bg_skill_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(ESPSpell.class.getSimpleName() + ".png"));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}