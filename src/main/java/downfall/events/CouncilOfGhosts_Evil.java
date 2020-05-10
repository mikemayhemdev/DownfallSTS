package downfall.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.List;

public class CouncilOfGhosts_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:Ghosts";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;
    private static final String INTRO_BODY_M;
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final float HP_DRAIN = 0.5F;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Ghosts");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
        INTRO_BODY_M = DESCRIPTIONS[0];
        ACCEPT_BODY = DESCRIPTIONS[2];
        EXIT_BODY = DESCRIPTIONS[3];
    }

    private int screenNum = 0;
    private int hpLoss = 0;
    private int goldCost = 200;

    public CouncilOfGhosts_Evil() {
        super(NAME, DESCRIPTIONSALT[0], "images/events/ghost.jpg");
        this.hpLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.5F);
        if (this.hpLoss >= AbstractDungeon.player.maxHealth) {
            this.hpLoss = AbstractDungeon.player.maxHealth - 1;
        }


        if (AbstractDungeon.player.gold >= goldCost) {
            this.imageEventText.setDialogOption(OPTIONSALT[0] + this.goldCost + OPTIONSALT[1], new Apparition());
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[2] + this.goldCost + OPTIONSALT[3], true);

        }

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.imageEventText.setDialogOption(OPTIONS[3] + this.hpLoss + OPTIONS[1], new Apparition());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.hpLoss + OPTIONS[1], new Apparition());
        }


        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GHOSTS");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Apparition(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        this.screenNum = 1;
                        AbstractDungeon.player.loseGold(150);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(ACCEPT_BODY);
                        AbstractDungeon.player.decreaseMaxHealth(this.hpLoss);
                        this.becomeGhost();
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        logMetricIgnored("Ghosts");
                        this.imageEventText.updateBodyText(EXIT_BODY);
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            default:
                this.openMap();
        }

    }

    private void becomeGhost() {
        List<String> cards = new ArrayList();
        int amount = 5;
        if (AbstractDungeon.ascensionLevel >= 15) {
            amount -= 2;
        }

        for (int i = 0; i < amount; ++i) {
            AbstractCard c = new Apparition();
            cards.add(c.cardID);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        }

        logMetricObtainCardsLoseMapHP("Ghosts", "Became a Ghost", cards, this.hpLoss);
    }
}
