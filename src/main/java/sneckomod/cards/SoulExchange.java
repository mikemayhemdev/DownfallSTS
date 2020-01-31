package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SoulExchange extends AbstractSneckoCard {

    public final static String ID = makeID("SoulExchange");

    //stupid intellij stuff SKILL, SELF, RARE

    public SoulExchange() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
}