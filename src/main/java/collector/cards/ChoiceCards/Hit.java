package collector.cards.ChoiceCards;

import basemod.AutoAdd;
import collector.actions.HighStakesAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
@AutoAdd.Ignore
public class Hit extends AbstractCollectorCard {
    public final static String ID = makeID("Hit");
    public boolean UpgradeState;
    public AbstractCard sourceCard;
    public Hit() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }
    public Hit(boolean upgradestate, AbstractCard Source) {
        this();
        UpgradeState = upgradestate;
        sourceCard = Source;
    }
    @Override
    public void upp() {
    }
    public void onChoseThisOption() {
        addToBot(new HighStakesAction(UpgradeState,sourceCard));
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
