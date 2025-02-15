package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;

public class SoulExchange extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("SoulExchange");

    private static final int COST = 1;

    public SoulExchange() {
        super(ID, COST, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.selfRetain = true;
        this.exhaust = true;
        SneckoMod.loadJokeCardImage(this, "SoulExchange.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MuddleHandAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
