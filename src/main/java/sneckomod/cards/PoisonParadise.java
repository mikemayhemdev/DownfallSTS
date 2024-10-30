package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;

public class PoisonParadise extends AbstractSneckoCard {

    public final static String ID = makeID("PoisonParadise");

    // weird and out of place if I'm speaking honestly - blue

    public PoisonParadise() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 12;
        this.tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "PoisonParadise.png");
    }

        public void use (AbstractPlayer p, AbstractMonster m){
            applyToEnemy(m, new PoisonPower(m, p, magicNumber));
            if (isOverflowActive(this)){
                applyToEnemy(m, new PoisonPower(m, p, magicNumber));
            }
        }


    public void upgrade() {

        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}