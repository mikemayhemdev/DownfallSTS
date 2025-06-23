package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;

public class Reviscerate extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Reviscerate.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Reviscerate() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            if (this.costForTurn > 0)
            this.setCostForTurn(this.costForTurn-1);
        }
    }


    @Override
    public void upp() {
        upgradeDamage(2);
    }
}