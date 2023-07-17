package collector.cards.collectibles;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DuplicationPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class DarklingsCard extends AbstractCollectibleCard {
    public final static String ID = makeID(DarklingsCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , , 

    public DarklingsCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (AbstractCard q : cards) {
                att(new MakeTempCardInHandAction(q, true));
                att(new MakeTempCardInHandAction(q, true));
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}