package champ.cards;

import champ.util.OnTechniqueSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MurderStrike extends AbstractChampCard implements OnTechniqueSubscriber {

    public final static String ID = makeID("MurderStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 10;

    public MurderStrike() {
        super(ID, 10, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        selfRetain = true;
        exhaust = true;
        tags.add(CardTags.STRIKE);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void onTechnique() {
        if (cost > 0) {
            updateCost(-1);
            baseDamage += magicNumber;
            applyPowers();
            superFlash(Color.RED.cpy());
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    public void upp() {
        if (this.cost < 10) {
            this.upgradeBaseCost(this.cost - 2);
            if (this.cost < 0) {
                this.cost = 0;
            }
        } else {
            this.upgradeBaseCost(7);
        }
        this.upgradeDamage(9);
    }
}