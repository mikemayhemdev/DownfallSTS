package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Beta;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class AlphaStar extends AbstractTimeEaterCard {
    public final static String ID = makeID("AlphaStar");
    // intellij stuff attack, enemy, rare, 10, 2, , , , 

    public AlphaStar() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 10;
        cardsToPreview = new BetaStar();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractCard q = new BetaStar();
        if (upgraded) q.upgrade();
        atb(new SuspendAction(q));
    }

    public void upp() {
        upgradeDamage(2);
        AbstractCard q = new BetaStar();
        q.upgrade();
        cardsToPreview = q;
        uDesc();
    }
}