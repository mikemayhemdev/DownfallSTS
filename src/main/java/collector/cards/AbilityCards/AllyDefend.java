package collector.cards.AbilityCards;

import basemod.AutoAdd;
import collector.CollectorChar;
import collector.TorchChar;
import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class AllyDefend extends AbstractCollectorCard {
    public final static String ID = makeID("AllyDefend");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public AllyDefend() {
        super(ID, -2, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);
       douBlock = douBaseBlock = 5;
        tags.add(AbstractCard.CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        TorchChar dragon = CollectorChar.getLivingDragon();
        addToBot(new GainBlockAction(dragon, douBlock));
    }
    public void onChoseThisOption() {
        TorchChar dragon = CollectorChar.getLivingDragon();
        this.applyPowers();
        addToBot(new GainBlockAction(dragon, douBlock));
    }
    public void upp() {
        upgradeBlock(3);
    }
}