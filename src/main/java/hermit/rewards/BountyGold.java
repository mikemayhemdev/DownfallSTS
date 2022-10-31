package hermit.rewards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;

import static hermit.HermitMod.makeID;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import hermit.patches.EnumPatch;

public class BountyGold extends CustomReward {
    private static final Texture ICON = ImageMaster.UI_GOLD;
    private static final String ID = makeID("bountygold");
    private static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UIStrings.TEXT;

    public int amount;

    public BountyGold(int amount) {
        super(ICON,  amount + TEXT[0], EnumPatch.HERMIT_BOUNTY);
        this.goldAmt = amount;
    }

    @Override
    public boolean claimReward() {
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(this.goldAmt);
        return true;
    }
}