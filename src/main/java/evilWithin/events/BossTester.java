package evilWithin.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import evilWithin.util.BossRelicReward;
import evilWithin.util.RareCardReward;
import evilWithin.util.UpgradeCardReward;
import evilWithin.util.RemoveCardReward;

public class BossTester extends AbstractImageEvent {
    public static final String ID = "evilWithin:BossTester";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    public BossTester() {
        super(NAME, DESCRIPTIONS[0], "images/events/facelessTrader.jpg");
        this.screen = CurScreen.INTRO;

        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {

        AbstractDungeon.getCurrRoom().rewards.clear();
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        simulateRewards(1);
                        break;
                    case 1:
                        simulateRewards(2);
                        break;
                    case 2:
                        simulateRewards(3);
                        break;
                }

                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();

                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                this.screen = CurScreen.MAIN;
                break;
            case MAIN:
                AbstractDungeon.getCurrRoom().clearEvent();
                AbstractRoom sRoom = new ShopRoom();
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                AbstractDungeon.getCurrRoom().smoked = false;
                AbstractDungeon.player.isEscaping = false;
                AbstractRoom.waitTimer = 0.1F;
                this.hasFocus = false;
                GenericEventDialog.hide();
                AbstractDungeon.currMapNode.setRoom(sRoom);
                AbstractDungeon.scene.nextRoom(sRoom);
                CardCrawlGame.fadeIn(1.5F);
                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                sRoom.onPlayerEntry();
                return;

            default:

        }

    }

    private void simulateRewards(int act) {
            AbstractDungeon.getCurrRoom().rewards.clear();
            if (act > 1) addBossRelicReward();
            if (act > 1) addBossCardReward();
            addCardReward();
            addCardReward();
            addCardReward();
            addPotionReward();
            addCardUpgradeReward();
            addStandardRelicReward();
            addCardRemoveReward();
            addStandardRelicReward();
            addCardReward();
            addCardReward();
            addCardUpgradeReward();
            addPotionReward();
            addCardRemoveReward();
            addCardReward();
            addCardUpgradeReward();
            addGoldReward(250);
    }

    private void addCardReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(AbstractDungeon.player.getCardColor()));
    }

    private void addBossCardReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new RareCardReward(AbstractDungeon.player.getCardColor()));
    }

    private void addStandardRelicReward(){
        int roll = AbstractDungeon.treasureRng.random(0, 99);
        AbstractRelic r;
        if (roll <= 35) {
            r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);
        } else if (roll <= 85) {

            r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON);
        } else {

            r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);
        }
        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(r));
    }

    private void addBossRelicReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new BossRelicReward());
    }

    private void addPotionReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
    }

    private void addGoldReward(int gold){
        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(gold));
    }

    private void addCardUpgradeReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new UpgradeCardReward());
    }

    private void addCardRemoveReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new RemoveCardReward());
    }


    private enum CurScreen {
        INTRO,
        MAIN,
        FIGHT,
        RESULT;

        CurScreen() {
        }
    }
}
