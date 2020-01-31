package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.SoulExchangeAction;

public class SoulExchange extends AbstractSneckoCard {

    public final static String ID = makeID("SoulExchangeAction");

    //stupid intellij stuff SKILL, SELF, RARE

    public SoulExchange() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(1));
        atb(new SoulExchangeAction());
        if (upgraded) atb(new DrawCardAction(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}