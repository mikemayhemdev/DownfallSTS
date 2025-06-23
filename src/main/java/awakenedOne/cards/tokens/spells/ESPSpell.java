package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class ESPSpell extends AbstractSpellCard {
    public final static String ID = makeID(ESPSpell.class.getSimpleName());
    // intellij stuff skill, all_enemy, , , , , 7, 2

    public ESPSpell() {
        super(ID, 0, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, ID+".png");
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}