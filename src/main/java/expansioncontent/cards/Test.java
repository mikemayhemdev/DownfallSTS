package expansioncontent.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.cardmods.*;

@CardIgnore
public class Test extends AbstractDownfallCard {
    //Card used for quick testing, if stuff interacts correctly.

    public Test() {
        super("TEST", "TEST", null, 1, "TEXT", CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        CardModifierManager.addModifier(this, new UnplayableMod());
        UnplayableMod.getUnplayableModFromCard(this).updateMessage("I AM UNPLAYABLE!!!");
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        atb(new DrawCardAction(1));
    }

    @Override
    public void upp() {
        CardModifierManager.removeModifiersById(this, EtherealMod.ID, true);
    }
}
