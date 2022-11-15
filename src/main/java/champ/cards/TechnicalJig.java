package champ.cards;

import champ.powers.IronFortressPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TechnicalJig extends AbstractChampCard {

    public final static String ID = makeID("TechnicalJig");

    //stupid intellij stuff power, self, uncommon

    public TechnicalJig() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        //tags.add(ChampMod.TECHNIQUE);
        //techniqueLast = true;
        //;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IronFortressPower(2));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}