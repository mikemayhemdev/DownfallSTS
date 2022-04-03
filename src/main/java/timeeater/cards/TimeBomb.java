package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class TimeBomb extends AbstractTimeEaterCard implements OnRetrieveCard {
    public final static String ID = makeID("TimeBomb");
    // intellij stuff attack, none, uncommon, 18, 6, , , , 

    public TimeBomb() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.NONE);
        baseDamage = 18;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        selfStasis();
    }

    @Override
    public void onRetrieve() {
        att(new DiscardSpecificCardAction(this, adp().hand));
        allDmgTop(AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeDamage(6);
    }
}