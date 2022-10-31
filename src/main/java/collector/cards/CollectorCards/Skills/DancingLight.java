package collector.cards.CollectorCards.Skills;

import collector.CollectorChar;
import collector.CollectorMod;
import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.AggroNegated;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DancingLight extends AbstractCollectorCard {
    public final static String ID = makeID("DancingLight");

    public DancingLight() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(CollectorChar.torch,p,new AggroNegated(CollectorChar.torch,CollectorMod.TorchAggro)));
        CollectorMod.TorchAggro = 0;
        addToBot(new DrawCardAction(p,1));
    }

    @Override
    public void upp() {
        initializeDescription();
    }
}