package automaton.util;

import automaton.AutomatonMod;
import automaton.cards.DonuBeam;
import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;

public class DonuBeamReward extends CustomReward {
    public static final String ID = AutomatonMod.makeID("DonuBeamReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("bronze:SpecificCardReward").TEXT;

    public DonuBeamReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), "ERROR", RewardItemTypeEnumPatch.DONUBEAM);
        cards.clear();
        cards.add(new DonuBeam());
        this.text = TEXT[0] + cards.get(0).name + TEXT[1];
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, "Pick a Card.");
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}