package champ.cards;

import champ.util.OnOpenerSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MurderStrike extends AbstractChampCard {

    public final static String ID = makeID("MurderStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 10;

    public MurderStrike() {
        super(ID, 10, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        selfRetain = true;
        exhaust = true;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    public void upp() {
        upgradeBaseCost(8);
    }
}