package collector.cards.Collectibles;

import collector.CollectorChar;
import collector.actions.AddAggroAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrudeShield extends AbstractCollectibleCard {
    public final static String ID = makeID("CrudeShield");

    public CrudeShield() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, AbstractCollectorCard.DTCardTarget.DRAGON_ONLY);
        block = baseBlock = 4;
        douBaseBlock = baseBlock;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.getLivingTorchHead() != null) {
            atb(new AddAggroAction(true, magicNumber));
            atb(new GainBlockAction(CollectorChar.torch,douBlock));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}
