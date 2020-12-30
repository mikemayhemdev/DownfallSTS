package collector.cards;

import collector.CollectorChar;
import collector.CollectorCollection;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Contemplate extends AbstractCollectorCard {
    public final static String ID = makeID("Contemplate");
    public Contemplate() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(CollectorCollection.Collection.group, 3,CollectorChar.characterStrings.TEXT[3],false,null,
                c -> {
                    for (AbstractCard card : c) {
                        AbstractDungeon.player.hand.addToBottom(card);
                        CollectorCollection.Collection.removeCard(card);
                    }
                }));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}