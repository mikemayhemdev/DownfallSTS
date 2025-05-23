package awakenedOne.cards;

import awakenedOne.util.OnConjureSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class HeavyStrike extends AbstractAwakenedCard implements OnConjureSubscriber {
    public final static String ID = makeID(HeavyStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    //carrionmaker
    public HeavyStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 4;
        //tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public void OnConjure() {
            this.addToBot(new DiscardToHandAction(this));
    }

    public void upp() {
        upgradeDamage(2);
    }
}