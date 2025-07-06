package champ.cards;

import champ.ChampMod;
import champ.actions.AnimateSuplexAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class Haymaker extends AbstractChampCard {

    public final static String ID = makeID("Haymaker");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;

    public Haymaker() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;

        tags.add(ChampMod.FINISHER);
        postInit();
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
