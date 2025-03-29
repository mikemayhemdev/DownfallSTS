package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class SpireGrowthCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SpireGrowthCard.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , 10, 5

    public SpireGrowthCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 10;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new ConstrictedPower(m, p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(5);
    }
}