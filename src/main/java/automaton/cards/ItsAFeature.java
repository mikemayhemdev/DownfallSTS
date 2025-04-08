package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.FeaturePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class ItsAFeature extends AbstractBronzeCard {

    public final static String ID = makeID("ItsAFeature");

    //stupid intellij stuff power, self, uncommon

    public ItsAFeature() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 3;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("ItsAFeature.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FeaturePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}