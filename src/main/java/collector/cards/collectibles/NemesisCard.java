package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class NemesisCard extends AbstractCollectibleCard {
    public final static String ID = makeID(NemesisCard.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , , 

    public NemesisCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.getIntentBaseDmg() > -1) {
            applyToSelf(new IntangiblePlayerPower(p, 1));
        }
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}