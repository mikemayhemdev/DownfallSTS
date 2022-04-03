package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.EasyXCostAction;
import timeeater.powers.HastePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.applyToSelfTop;
import static timeeater.util.Wiz.atb;

public class Acceleration extends AbstractTimeEaterCard {
    public final static String ID = makeID("Acceleration");
    // intellij stuff attack, self_and_enemy, uncommon, 5, 3, , , , 

    public Acceleration() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            applyToSelfTop(new HastePower(effect));
            for (int i = 0; i < effect; i++) {
                dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            }
            return true;
        }));
    }

    public void upp() {
        upgradeDamage(3);
    }
}