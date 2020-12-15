package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class HeldProtector extends AbstractBronzeCard {

    public final static String ID = makeID("HeldProtector");

    //stupid intellij stuff skill, self, uncommon

    public HeldProtector() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(AutomatonMod.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (inFire) {
            applyToSelf(new ArtifactPower(p, 1));
        }
        if (upgraded) {
            atb(new ReduceCostAction(this));
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}