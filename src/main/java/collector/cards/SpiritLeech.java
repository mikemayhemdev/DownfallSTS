package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.isAfflicted;

public class SpiritLeech extends AbstractCollectorCard {
    public final static String ID = makeID(SpiritLeech.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public SpiritLeech() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (isAfflicted(m)) {
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
        }
    }

    public void upp() {
        upgradeDamage(3);
    }
}