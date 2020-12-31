package downfall.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.events.beyond.MysteriousSphere;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import downfall.rooms.HeartShopRoom;
import slimebound.SlimeboundMod;
import slimebound.events.DarklingsSlimebound;

public class WindingHalls_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:WindingHalls";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private int screenNum = 0;

    public WindingHalls_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/winding.jpg");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_WINDING");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        boolean hackery = false;
                        boolean hackery2 = false;
                        if (AbstractDungeon.eventList.contains(DarklingsSlimebound.ID)) {
                            AbstractDungeon.eventList.remove(DarklingsSlimebound.ID);
                            hackery = true;
                            //SlimeboundMod.logger.info("Hacked away darklings");
                        }
                        if (AbstractDungeon.eventList.contains(MysteriousSphere.ID)) {
                            AbstractDungeon.eventList.remove(MysteriousSphere.ID);
                            hackery2 = true;
                            //SlimeboundMod.logger.info("Hacked away orb walkers");
                        }
                        AbstractDungeon.getCurrRoom().onPlayerEntry();
                        if (hackery){
                            AbstractDungeon.eventList.add(DarklingsSlimebound.ID);
                        }
                        if (hackery2) {
                            AbstractDungeon.eventList.add(MysteriousSphere.ID);
                        }
                        return;
                    case 1:
                        AbstractDungeon.getCurrRoom().clearEvent();
                        AbstractRoom tRoom = new TreasureRoom();
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                        AbstractDungeon.getCurrRoom().smoked = false;
                        AbstractDungeon.player.isEscaping = false;
                        AbstractRoom.waitTimer = 0.1F;
                        this.hasFocus = false;
                        GenericEventDialog.hide();
                        AbstractDungeon.currMapNode.setRoom(tRoom);
                        AbstractDungeon.scene.nextRoom(tRoom);
                        CardCrawlGame.fadeIn(1.5F);
                        AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                        tRoom.onPlayerEntry();
                        return;
                    case 2:
                        AbstractDungeon.getCurrRoom().clearEvent();
                        AbstractRoom sRoom = new HeartShopRoom();
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
                        return;
                }
            default:
                this.openMap();
        }

    }


    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
