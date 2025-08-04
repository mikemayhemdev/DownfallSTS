package awakenedOne.cards;

import awakenedOne.actions.EasyXCostAction;
import awakenedOne.cards.tokens.PlumeJab;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.att;

public class FeatherWhirl extends AbstractAwakenedCard {
    public final static String ID = makeID(FeatherWhirl.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public FeatherWhirl() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 0;
        cardsToPreview = new PlumeJab();
        loadJokeCardImage(this, makeBetaCardPath(FeatherWhirl.class.getSimpleName() + ".png"));
    }

 public void use(AbstractPlayer p, AbstractMonster m) {
        att(new EasyXCostAction(this, (effect, params) -> {
            Wiz.makeInHand(new PlumeJab(), effect + params[0]);
            return true;
        }, magicNumber));
    }


    public void upp() {
        upgradeMagicNumber(1);
    }
}