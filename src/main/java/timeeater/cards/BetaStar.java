package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class BetaStar extends AbstractTimeEaterCard {
    public final static String ID = makeID("BetaStar");
    // intellij stuff attack, enemy, special, , 4, , , , 

    public BetaStar() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 20;
        cardsToPreview = new OmegaStar();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractCard q = new OmegaStar();
        if (upgraded) q.upgrade();
        atb(new SuspendAction(q));
    }

    public void upp() {
        upgradeDamage(4);
        AbstractCard q = new OmegaStar();
        q.upgrade();
        cardsToPreview = q;
        uDesc();
    }
}