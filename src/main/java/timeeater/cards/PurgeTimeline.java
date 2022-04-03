package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Purity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class PurgeTimeline extends AbstractTimeEaterCard {
    public final static String ID = makeID("PurgeTimeline");
    // intellij stuff skill, self, uncommon, , , 8, 1, , 

    public PurgeTimeline() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        cardsToPreview = new Purity();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SuspendAction(new Purity()));
    }

    public void upp() {
        upgradeBlock(1);
        AbstractCard q = new Purity();
        q.upgrade();
        cardsToPreview = q;
    }
}