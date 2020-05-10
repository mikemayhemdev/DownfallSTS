package downfall.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.FadeWipeParticle;
import downfall.relics.TeleportStone;

public class Portal_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:SecretPortal";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    public static final String EVENT_CHOICE_TOOK_PORTAL = "Took Portal";
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private CurScreen screen;

    public Portal_Evil() {
        super(NAME, DESCRIPTIONSALT[0], "images/events/secretPortal.jpg");
        this.screen = CurScreen.INTRO;
        this.imageEventText.setDialogOption(OPTIONSALT[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_PORTAL");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch(buttonPressed) {
                    case 0:

                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        this.screen = CurScreen.LEAVE;
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new TeleportStone());

                        CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_1");
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_3);
                        this.screen = CurScreen.LEAVE;
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        break;
                }

                this.imageEventText.clearRemainingOptions();
                break;
            case ACCEPT:
                AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                MapRoomNode node = new MapRoomNode(-1, 15);
                node.room = new MonsterRoomBoss();
                AbstractDungeon.nextRoom = node;
                CardCrawlGame.music.fadeOutTempBGM();
                AbstractDungeon.pathX.add(1);
                AbstractDungeon.pathY.add(15);
                AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
                AbstractDungeon.nextRoomTransitionStart();
                break;
            default:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("SecretPortal");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }

    private static enum CurScreen {
        INTRO,
        ACCEPT,
        LEAVE;

        private CurScreen() {
        }
    }
}
