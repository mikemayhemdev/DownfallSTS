package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.SacrificeAction2;

public class NecessarySacrifice extends AbstractHexaCard {

    public final static String ID = makeID("NecessarySacrifice");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public NecessarySacrifice() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SacrificeAction2());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}