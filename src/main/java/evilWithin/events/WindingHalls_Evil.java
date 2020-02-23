package evilWithin.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.List;

public class WindingHalls_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:WindingHalls";
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
        switch(this.screenNum) {
            case 0:
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        AbstractDungeon.getCurrRoom().onPlayerEntry();
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
