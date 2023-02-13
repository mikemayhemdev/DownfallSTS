package champ.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Circumvent extends AbstractChampCard {
    public final static String ID = makeID("Circumvent");

    public Circumvent() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        baseMagicNumber = magicNumber = 2;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardAction(magicNumber));
        atb(new DiscardAction(p, p, magicNumber, false));
    }

    public void upp() {
        upgradeBlock(3);
    }
}