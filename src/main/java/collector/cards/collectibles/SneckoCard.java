package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class SneckoCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SneckoCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 3, 2

    public SneckoCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
        atb(new MuddleHandAction());
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}