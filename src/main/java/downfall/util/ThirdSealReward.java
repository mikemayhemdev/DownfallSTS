package downfall.util;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.patches.RewardItemTypeEnumPatch;


public class ThirdSealReward extends CustomReward{

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("RewardItem").TEXT ;

    public ThirdSealReward() {
        super(ImageMaster.REWARD_CARD_NORMAL, TEXT[2], RewardItemTypeEnumPatch.THIRDSEALCARDREWARD);
        this.hb = new Hitbox(460.0F * Settings.xScale, 90.0F * Settings.yScale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;
        ReflectionHacks.setPrivate(this,RewardItem.class,"reticleColor",new Color(1.0F, 1.0F, 1.0F, 0.0F));
        this.type = RewardItemTypeEnumPatch.THIRDSEALCARDREWARD;
        ReflectionHacks.setPrivate(this,RewardItem.class,"isBoss",false);
//        ReflectionHacks.setPrivate(this,RewardItem.class,"isBoss",AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss);
//        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) this.icon = ImageMaster.REWARD_CARD_BOSS;
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[4]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }

    public void generate_reward_cards(){
        this.cards.clear();
        this.cards.addAll(AbstractDungeon.getRewardCards());
    }

}