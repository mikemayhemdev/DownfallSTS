package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;
import static champ.ChampMod.vigor;

public class DeathBlow extends AbstractChampCard {

    public final static String ID = makeID("DeathBlow");

    public DeathBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL);
        baseDamage = 20;
        baseMagicNumber = magicNumber = 20;
        isMultiDamage = true;
        myHpLossCost = 10;
        tags.add(ChampMod.FINISHER);
        postInit();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        vigor(magicNumber);
        fatigue(10);
        finisher();
    }

    public void upp() {
        upgradeDamage(5);
        upgradeMagicNumber(5);
    }
}