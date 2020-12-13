package champ.cards;

import champ.ChampMod;
import champ.stances.GladiatorStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Devastate extends AbstractChampCard {

    public final static String ID = makeID("Devastate");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public Devastate() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = ChampMod.finishersThisCombat;
        //finisher();
        for (int i = 0; i < x; i++) dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        finisher();
    }

    //TODO: add the damage predictor display like Finisher (silent card) etc to make effects clear

    public void upp() {
        upgradeBaseCost(1);
    }
}