package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.forAllMonstersLiving;

public class LagavulinCard extends AbstractCollectibleCard {
    public final static String ID = makeID(LagavulinCard.class.getSimpleName());
    // intellij stuff skill, all_enemy, uncommon, , , , , 2, 1

    public LagavulinCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseBlock = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        forAllMonstersLiving(q -> applyToEnemy(q, new StrengthPower(q, -magicNumber)));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}