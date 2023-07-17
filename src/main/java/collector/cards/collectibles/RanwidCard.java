package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class RanwidCard extends AbstractCollectibleCard {
    public final static String ID = makeID(RanwidCard.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public RanwidCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractDungeon.getCurrRoom().addCardToRewards();
                AbstractDungeon.getCurrRoom().addPotionToRewards();
            }
        });

        //TODO: Test to make sure this works, rewards may be cleared after combat.
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}