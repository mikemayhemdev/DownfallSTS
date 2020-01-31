package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.PureSneckoAction;

public class PureSnecko extends AbstractSneckoCard {

    public final static String ID = makeID("PureSnecko");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public PureSnecko() {
        super(ID, "Beta", 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) atb(new DrawCardAction(1));
        atb(new PureSneckoAction(p));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}