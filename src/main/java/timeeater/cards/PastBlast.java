package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class PastBlast extends AbstractTimeEaterCard {
    public final static String ID = makeID("PastBlast");
    // intellij stuff attack, enemy, uncommon, 8, 3, , , , 

    public PastBlast() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractCard q = p.discardPile.getTopCard();
                p.discardPile.removeCard(q);
                p.drawPile.addToTop(q);
                att(new DrawCardAction(1));
            }
        });
    }

    public void upp() {
        upgradeDamage(3);
    }
}