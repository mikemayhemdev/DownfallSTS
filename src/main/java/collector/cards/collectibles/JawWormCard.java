package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class JawWormCard extends AbstractCollectibleCard {
    public final static String ID = makeID(JawWormCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , 14, 4, 2, 1

    public JawWormCard() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 12;
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }
}