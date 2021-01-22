package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class DeathBlow extends AbstractChampCard {

    public final static String ID = makeID("DeathBlow");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 15;
    private static final int MAGIC = 15;

    public DeathBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        isMultiDamage = true;
       // tags.add(ChampMod.TECHNIQUE);
        myHpLossCost = MAGIC;
        //tags.add(ChampMod.FINISHER);
        postInit();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // techique();
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        fatigue(magicNumber);
        applyToSelf(new ResolvePower(10));
       // finisher();
    }

    public void upp() {
        upgradeMagicNumber(5);
        upgradeDamage(5);
    }
}