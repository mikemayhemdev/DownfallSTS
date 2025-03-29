package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class CenturionCard extends AbstractCollectibleCard {
    public final static String ID = makeID(CenturionCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , 10, 4, 1, 1

    public CenturionCard() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 10;
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new DexterityPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(4);
        upgradeMagicNumber(1);
    }
}