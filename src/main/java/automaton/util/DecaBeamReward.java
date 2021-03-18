package automaton.util;

import automaton.AutomatonMod;
import automaton.cards.DazingPulse;
import automaton.cards.DecaBeam;
import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;

public class DecaBeamReward extends CustomReward {
    public static final String ID = AutomatonMod.makeID("DecaBeamReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("bronze:SpecificCardReward").TEXT;

    public DecaBeamReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), "ERROR", RewardItemTypeEnumPatch.DECABEAM);
        cards.clear();
        cards.add(new DecaBeam());
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