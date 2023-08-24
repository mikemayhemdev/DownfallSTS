package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.vigor;
import static champ.ChampMod.loadJokeCardImage;

public class DeathBlow extends AbstractChampCard {

    public final static String ID = makeID("DeathBlow");

    public DeathBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL);
        baseDamage = 12;
        baseMagicNumber = magicNumber = 8;
        isMultiDamage = true;
        postInit();
        exhaust = true;
        loadJokeCardImage(this, "DeathBlow.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        vigor(magicNumber);
       // fatigue(10);
      //  finisher();
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(2);
    }
}