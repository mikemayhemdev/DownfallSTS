package collector.cards.collectibles;

import collector.cards.OnPyreCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DuplicationPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class DarklingsCard extends AbstractCollectibleCard implements OnPyreCard {
    public final static String ID = makeID(DarklingsCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , , 

    public DarklingsCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        isPyre();
    }

    private AbstractCard toAdd;

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                att(new MakeTempCardInHandAction(toAdd, 3));
            }
        });
    }

    @Override
    public void onPyred(AbstractCard card) {
        toAdd = card;
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}