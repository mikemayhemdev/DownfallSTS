package evilWithin.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import evilWithin.EvilWithinMod;

import java.io.IOException;

public class GoldenIdol_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:GoldenIdol";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    public static boolean trapAlreadySet = false;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;
    private int gold = 100;
    private AbstractCard strike = null;

    public GoldenIdol_Evil() {
        super(NAME, "", "images/events/goldenIdol.jpg");
        this.screen = CurScreen.INTRO;

        if (!trapAlreadySet) {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                    this.strike = c;
                    this.imageEventText.setDialogOption(OPTIONS[0]);
                    break;
                }
            }
            if (this.strike == null) {
                this.imageEventText.setDialogOption(OPTIONS[4], true);
            }
            this.body = DESCRIPTIONS[0];

        } else {
            this.body = DESCRIPTIONS[1];

            this.imageEventText.setDialogOption(OPTIONS[1] + this.gold + OPTIONS[2]);
        }
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    public static void save() {
        if (AbstractDungeon.player != null && EvilWithinMod.bruhData != null) {
            try {
                EvilWithinMod.bruhData.setBool("trapEvent", trapAlreadySet);
                EvilWithinMod.bruhData.save();
            } catch (IOException ignored) {
            }
        }
    }

    public static void load() {
        if (EvilWithinMod.bruhData != null && EvilWithinMod.bruhData.has("trapEvent")) {
            trapAlreadySet = EvilWithinMod.bruhData.getBool("trapEvent");
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        if (!trapAlreadySet) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(this.strike, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                            AbstractDungeon.player.masterDeck.removeCard(strike);
                            trapAlreadySet = true;
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                            AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                            AbstractDungeon.player.gainGold(this.gold);
                            trapAlreadySet = false;
                        }

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
                        return;
                    case 1:

                        if (!trapAlreadySet) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        }

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
                        return;
                    default:
                        return;
                }
            default:
                this.openMap();
        }
    }

    private enum CurScreen {
        INTRO,
        RESULT;

        CurScreen() {
        }
    }
}
