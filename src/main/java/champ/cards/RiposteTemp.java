package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RiposteTemp extends AbstractChampCard {

    public final static String ID = makeID("Riposte");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public RiposteTemp() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}