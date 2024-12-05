package collector.cards.collectibles;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class MysticCard extends AbstractCollectibleCard {
    public final static String ID = makeID(MysticCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 6, 2

    public MysticCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AddTemporaryHPAction(p, p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}