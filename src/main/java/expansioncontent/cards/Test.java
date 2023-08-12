package expansioncontent.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.actions.EchoACardAction;
import expansioncontent.cardmods.*;

@CardIgnore
public class Test extends AbstractDownfallCard {
    //Card used for quick testing, if stuff interacts correctly.

    public Test() {
        super("TEST", "TEST", null, 1, "TEXT", CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        atb(new EchoACardAction(this, 2));
    }

    @Override
    public void upp() {

    }
}
