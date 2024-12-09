package collector.cards.collectibles;

import collector.powers.collectioncards.LouseCardPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class LouseCard extends AbstractCollectibleCard {
    public final static String ID = makeID(LouseCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , 7, 3, , 

    public LouseCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BlurPower(p, 1));
        this.addToBot(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
    }

    public void upp() {
        upgradeBlock(3);
    }
}