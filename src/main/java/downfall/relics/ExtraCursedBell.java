package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;

public class ExtraCursedBell extends CustomRelic {

    private boolean cardsReceived = true;

    public static final String ID = downfallMod.makeID("ExtraCursedBell");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/ExtraCursedBell.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/ExtraCursedBell.png"));

    public ExtraCursedBell() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.cardsReceived = false;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard bellCurse = new CurseOfTheBell();
        UnlockTracker.markCardAsSeen(bellCurse.cardID);
        for (int i = 0; i < 2; i++)
            group.addToBottom(bellCurse.makeCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
    }

    public void update() {
        super.update();
        if (!this.cardsReceived && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.combatRewardScreen.open();
            AbstractDungeon.combatRewardScreen.rewards.clear();
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE)));
            AbstractDungeon.combatRewardScreen.positionRewards();
            AbstractDungeon.overlayMenu.proceedButton.setLabel(this.DESCRIPTIONS[2]);
            this.cardsReceived = true;
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }

        if (this.hb.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
            CardCrawlGame.sound.playA("souls1", -0.1F);
            this.flash();
        }

    }
}
