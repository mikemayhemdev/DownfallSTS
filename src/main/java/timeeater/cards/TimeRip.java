package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class TimeRip extends AbstractTimeEaterCard {
    public final static String ID = makeID("TimeRip");
    // intellij stuff attack, enemy, common, 4, , , , , 

    public TimeRip() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        cardsToPreview = new Shiv();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        for (int i = 0; i < 2; i++) {
            AbstractCard q = new Shiv();
            if (upgraded) q.upgrade();
            atb(new SuspendAction(q));
        }
    }

    public void upp() {
        AbstractCard q = new Shiv();
        q.upgrade();
        cardsToPreview = q;
        uDesc();
    }
}