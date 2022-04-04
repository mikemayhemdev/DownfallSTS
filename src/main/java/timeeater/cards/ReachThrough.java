package timeeater.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import timeeater.actions.SuspendAction;
import timeeater.patches.ReachThroughPatch;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class ReachThrough extends AbstractTimeEaterCard {
    public final static String ID = makeID("ReachThrough");
    // intellij stuff skill, self, uncommon, , , , , , 

    public ReachThrough() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    private static ArrayList<AbstractCard> getThisRoomsCardRewards() {
        for (RewardItem r : AbstractDungeon.getCurrRoom().rewards) {
            if (r.type == RewardItem.RewardType.CARD && r.cards.size() > 0) {
                return r.cards;
            }
        }
        RewardItem r = new RewardItem();
        AbstractDungeon.getCurrRoom().addCardReward(r);
        ReachThroughPatch.ignoreNextReward = true;
        return r.cards;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> options = new ArrayList<>();
        options.addAll(getThisRoomsCardRewards());
        atb(new SelectCardsCenteredAction(options, AbstractMemoryCard.uiStrings.TEXT[0], (cards) -> {
            AbstractCard q = cards.get(0);
            if (upgraded) q.updateCost(-99);
            atb(new SuspendAction(q));
        }));
    }

    public void upp() {
        uDesc();
    }
}