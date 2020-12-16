package twins.cards;

import twins.DonuDecaMod;
import twins.actions.FireFromPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReflectionHacks extends AbstractTwinsCard {

    public final static String ID = makeID("ReflectionHacks");

    //stupid intellij stuff skill, self, uncommon

    public ReflectionHacks() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(DonuDecaMod.SHIELD);
        tags.add(DonuDecaMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new FireFromPileAction(p.discardPile, 1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}