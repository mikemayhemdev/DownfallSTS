package downfall.util;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.patches.RewardItemTypeEnumPatch;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.util.UpgradedUnknownReward;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRng;


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
        this.cards.addAll((ThirdSealReward.getCards()));
    }

    public static ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        int numCards = 3;

        for(AbstractRelic r : AbstractDungeon.player.relics) {
            numCards = r.changeNumberOfCardsInReward(numCards);
        }

        if (ModHelper.isModEnabled("Binary")) {
            --numCards;
        }
        while (cardsList.size() < numCards) {
            AbstractCard q = getCommonCard();
            if (!cardListDuplicate(cardsList, q)) {
                AbstractCard r = q.makeCopy();
                cardsList.add(r);
            }
        }
        return cardsList;
    }


    public static AbstractCard getCommonCard() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.commonCardPool.group) {
                AbstractCard q = c.makeCopy();
                q.upgrade();
                list.add(c);
        }
        return list.get(cardRng.random(list.size() - 1));// 1217
    }


    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

}
