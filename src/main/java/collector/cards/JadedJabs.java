package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class JadedJabs extends AbstractCollectorCard implements OnPyreCard {
    public final static String ID = makeID(JadedJabs.class.getSimpleName());
    // intellij stuff attack, enemy, common, 10, 2, , , , 

    public JadedJabs() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
        cardsToPreview = new Shiv();
        isPyre();
    }

    private int toAdd = -1;

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (toAdd > 0) {
                    AbstractCard q = new Shiv();
                    if (upgraded) q.upgrade();
                    att(new MakeTempCardInHandAction(q, toAdd, true));
                }
            }
        });
    }

    @Override
    public void onPyred(AbstractCard card) {
        int result = Wiz.getLogicalCardCost(card);
        toAdd = result > 0 ? result : -1;
    }

    public void upp() {
        upgradeDamage(2);
        AbstractCard q = new Shiv();
        q.upgrade();
        cardsToPreview = q;
        uDesc();
    }
}