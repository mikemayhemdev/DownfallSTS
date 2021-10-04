package collector.cards.CollectorCards.Skills;

import collector.actions.HighStakesAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HighStakes extends AbstractCollectorCard {
    public final static String ID = makeID("HighStakes");

    public HighStakes() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HighStakesAction(upgraded,this));
        AbstractDungeon.player.hand.removeCard(this);
        AbstractDungeon.player.limbo.addToTop(this);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}