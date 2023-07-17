package collector.cards.collectibles;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class NlothCard extends AbstractCollectibleCard {
    public final static String ID = makeID(NlothCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public NlothCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard toGenerate = AbstractDungeon.srcRareCardPool.getRandomCard(AbstractDungeon.cardRandomRng).makeCopy();
        if (upgraded) {
            toGenerate.updateCost(-999);
        }
        else {
            toGenerate.updateCost(-1);
        }
        makeInHand(toGenerate);
    }

    public void upp() {
        uDesc();
    }
}