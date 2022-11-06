package collector.util;

import collector.CollectorMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;

public class CollectionReward extends RewardItem {
    public static final String ID = CollectorMod.makeID("CollectionReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public CollectionReward(ArrayList<String> collected) {
        this.type = RewardType.CARD;

        this.cards.clear();
        collected.forEach(s -> cards.add(CardLibrary.getCard(s).makeCopy()));

        this.text = TEXT[0];

        AbstractDungeon.player.relics.forEach(q -> cards.forEach(c -> q.onPreviewObtainCard(c)));
    }

}