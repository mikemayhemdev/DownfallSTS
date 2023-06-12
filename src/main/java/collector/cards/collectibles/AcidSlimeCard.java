package collector.cards.collectibles;

import collector.cards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class AcidSlimeCard extends AbstractCollectorCard {
    public final static String ID = makeID(AcidSlimeCard.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public AcidSlimeCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (AbstractCard q : cards) {
                att(new MakeTempCardInHandAction(q, true));
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}