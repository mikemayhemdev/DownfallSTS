package automaton.cards;

import automaton.powers.FeaturePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ItsAFeature extends AbstractBronzeCard {

    public final static String ID = makeID("ItsAFeature");

    //stupid intellij stuff power, self, uncommon

    public ItsAFeature() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FeaturePower(1));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}