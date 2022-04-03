package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import timeeater.actions.AllEnemyLoseHPAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Invert extends AbstractTimeEaterCard {
    public final static String ID = makeID("Invert");
    // intellij stuff skill, all_enemy, rare, , , , , 20, 10

    public Invert() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 20;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AllEnemyLoseHPAction(magicNumber));
        forAllMonstersLiving(q -> applyToEnemy(q, new RegenerateMonsterPower(q, 4)));
    }

    public void upp() {
        upgradeMagicNumber(10);
    }
}