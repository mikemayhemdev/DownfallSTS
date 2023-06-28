package collector.cards;

import collector.actions.GainReservesAction;
import collector.util.NewReserves;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelfTop;
import static collector.util.Wiz.atb;

public class HoardersStrike extends AbstractCollectorCard {
    public final static String ID = makeID(HoardersStrike.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 12, 5, , , , 

    public HoardersStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        atb(new DrawCardAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : DrawCardAction.drawnCards) {
                    addToTop(new GainReservesAction(q.costForTurn));
                }
            }
        }));
    }

    public void upp() {
        upgradeDamage(5);
    }
}