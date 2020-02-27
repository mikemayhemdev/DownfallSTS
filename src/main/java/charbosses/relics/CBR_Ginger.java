package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Girya;

import java.util.ArrayList;

public class CBR_Ginger extends AbstractCharbossRelic {

    public CBR_Ginger() {
        super(new Girya());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.counter = AbstractDungeon.actNum;
        if (this.counter != 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, this.counter), this.counter));
            this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        }
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify) {
        for (int i = AbstractDungeon.actNum; i < 3; i++) {
            this.owner.chosenArchetype.cardUpgradesPerAct[i] -= 1;
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Ginger();
    }
}
