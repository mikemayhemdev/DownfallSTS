package collector.cards;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.applySuffering;
import static collector.CollectorMod.makeID;

public class SpiritLeech extends AbstractCollectorCard {
    public final static String ID = makeID("SpiritLeech");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public SpiritLeech() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (CollectorMod.isAfflicted(m)){
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
        }

    }

    public void upp() {
        upgradeDamage(3);
    }
}