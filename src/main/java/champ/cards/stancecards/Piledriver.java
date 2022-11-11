package champ.cards.stancecards;

import champ.ChampMod;
import champ.actions.AnimateSuplexAction;
import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class Piledriver extends AbstractChampCard {

    public final static String ID = makeID("Piledriver");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public Piledriver() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        tags.add(ChampMod.FINISHER);
        loadJokeCardImage(this, "Haymaker.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AnimateSuplexAction(m));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);

        //if (dcombo())
        applyToEnemy(m, autoVuln(m, 2));
        applyToEnemy(m, autoWeak(m, 2));

        finisher();
    }


    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}
