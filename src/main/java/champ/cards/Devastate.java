package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Devastate extends AbstractChampCard {

    public final static String ID = makeID("Devastate");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public Devastate() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = ChampMod.finishersThisCombat;
        //finisher();
        for (int i = 0; i < x; ) dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        finisher();
    }

    //TODO: add the damage predictor display like Finisher (silent card) etc to make effects clear

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}