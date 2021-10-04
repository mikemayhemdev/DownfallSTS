package collector.cards.ChoiceCards;

import basemod.AutoAdd;
import collector.actions.HighStakesAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
@AutoAdd.Ignore
public class Fold extends AbstractCollectorCard {
    public final static String ID = makeID("Fold");
    public AbstractCard sourceCard;
    public Fold() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }
    public Fold(AbstractCard Source) {
        this();
        sourceCard = Source;
    }
    @Override
    public void upp() {

    }
    public void onChoseThisOption() {
        HighStakesAction.TotalCost = 0;
        AbstractDungeon.player.limbo.removeCard(sourceCard);
        if (!AbstractDungeon.player.exhaustPile.contains(sourceCard)) {
            AbstractDungeon.player.discardPile.addToTop(sourceCard);
        }
        HighStakesAction.CardsDrawn.clear();
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
