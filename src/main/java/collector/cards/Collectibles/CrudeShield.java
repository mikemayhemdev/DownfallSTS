package collector.cards.Collectibles;

import collector.CollectorChar;
import collector.actions.AddAggroAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrudeShield extends AbstractCollectibleCard {
    public final static String ID = makeID("CrudeShield");

    public CrudeShield() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, AbstractCollectorCard.CollectorCardSource.TORCH_ONLY);
        douBaseBlock = baseBlock;
        magicNumber = baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.getLivingTorchHead() != null) {
            atb(new AddAggroAction(magicNumber));
            Torchblck();
        }
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}
