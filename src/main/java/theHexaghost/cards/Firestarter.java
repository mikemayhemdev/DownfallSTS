package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class Firestarter extends AbstractHexaCard {
    public final static String ID = makeID("Firestarter");

    public Firestarter() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 5;
        baseBurn = burn = 5;
        isMultiDamage = true;
        HexaMod.loadJokeCardImage(this, "Firestarter.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            burn(q, burn);
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBurn(2);
        }
    }
}