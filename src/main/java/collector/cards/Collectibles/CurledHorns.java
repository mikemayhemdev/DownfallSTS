package collector.cards.Collectibles;

import collector.CollectorChar;
import collector.Interfaces.PerpetualCard;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CurledHorns extends AbstractCollectibleCard implements PerpetualCard {
    public final static String ID = makeID("CurledHorns");

    public CurledHorns() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, AbstractCollectorCard.DTCardTarget.BOTH);
        block = baseBlock = 4;
        douBaseBlock = baseBlock;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.isFrontTorchHead()) {
            atb(new GainBlockAction(CollectorChar.torch,douBlock));
        } else {
            atb(new GainBlockAction(p,block));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }

    @Override
    public void PerpetualBonus() {}
}