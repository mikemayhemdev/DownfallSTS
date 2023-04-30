package collector.cards;

import collector.CollectorMod;
import collector.actions.HealTorchheadAction;
import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import collector.util.Wiz.*;
import com.megacrit.cardcrawl.powers.BlurPower;

import static collector.CollectorMod.makeID;

public class Vanguard extends AbstractCollectorCard {
    public final static String ID = makeID(Vanguard.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Vanguard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBlock = 9;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new BlurPower(p,1));


    }

    public void upp() {
        upgradeBlock(3);
    }
}