package champ.cards;

import champ.util.OnReducedByOpenerSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MurderStrike extends AbstractChampCard implements OnReducedByOpenerSubscriber {

    public final static String ID = makeID("MurderStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 10;

    public MurderStrike() {
        super(ID, 10, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    @Override
    public Color onReducedByOpener(Color c) {
        baseDamage += 5;
        return Color.RED.cpy();
    }

    public void upp() {
        upgradeBaseCost(8);
    }
}