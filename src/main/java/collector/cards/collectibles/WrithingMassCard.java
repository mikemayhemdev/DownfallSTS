package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class WrithingMassCard extends AbstractCollectibleCard {
    public final static String ID = makeID(WrithingMassCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 3, 2

    public WrithingMassCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ScryAction(magicNumber));
        atb(new PlayTopCardAction(AbstractDungeon.getRandomMonster(), false));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}