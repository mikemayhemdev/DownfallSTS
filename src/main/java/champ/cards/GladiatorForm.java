package champ.cards;

import basemod.helpers.BaseModCardTags;
import champ.powers.GladiatorFormPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GladiatorForm extends AbstractChampCard {

    public final static String ID = makeID("GladiatorForm");

    //stupid intellij stuff power, self, rare

    public GladiatorForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GladiatorFormPower(1));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}