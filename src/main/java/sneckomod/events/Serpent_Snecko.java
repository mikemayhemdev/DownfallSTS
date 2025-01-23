package sneckomod.events;


import champ.relics.BerserkersGuideToSlaughter;
import champ.relics.DefensiveTrainingManual;
import champ.relics.FightingForDummies;
import champ.relics.GladiatorsBookOfMartialProwess;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.cards.curses.Bewildered;
import downfall.downfallMod;
import sneckomod.relics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Serpent_Snecko extends AbstractImageEvent {
    public static final String ID = "sneckomod:Serpent";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;
    private static final int GOLD_REWARD = 175;
    private static final int A_2_GOLD_REWARD = 150;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];
    }

    private CUR_SCREEN screen;
    private int goldReward;
    private AbstractCard curse;

    public Serpent_Snecko() {
        super(NAME, DIALOG_1, "images/events/liarsGame.jpg");
        this.screen = CUR_SCREEN.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldReward = 150;
        } else {
            this.goldReward = 175;
        }

        this.curse = new Bewildered();

        if (AbstractDungeon.player.hasRelic(ConfusingCodex.ID) &&
                AbstractDungeon.player.hasRelic(LoadedDie.ID) &&
                AbstractDungeon.player.hasRelic(RareBoosterPack.ID) &&
                AbstractDungeon.player.hasRelic(SleevedAce.ID) &&
                AbstractDungeon.player.hasRelic(UnknownEgg.ID) &&
                AbstractDungeon.player.hasRelic(SneckoCommon.ID)){

            this.imageEventText.setDialogOption(OPTIONS[0], true, CardLibrary.getCopy(this.curse.cardID));
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0], CardLibrary.getCopy(this.curse.cardID));
        }

        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SERPENT");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(AGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.curse, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    AbstractRelic r = this.getRandomFace();
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, r);
                    downfallMod.removeAnyRelicFromPools(r.relicId);
                   logMetricObtainCardAndRelic(ID, "Agree", curse, r);
                    this.screen = CUR_SCREEN.AGREE;
                } else {
                    this.imageEventText.updateBodyText(DISAGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    this.screen = CUR_SCREEN.DISAGREE;
                    logMetricIgnored(ID);
                }
                break;
            default:
                this.openMap();
        }

    }

    private AbstractRelic getRandomFace() {
        ArrayList<String> ids = new ArrayList();


        //        BaseMod.addRelicToCustomPool(new ConfusingCodex(), TheSnecko.Enums.SNECKO_CYAN);
        if (!AbstractDungeon.player.hasRelic(ConfusingCodex.ID)) {
            ids.add(ConfusingCodex.ID);
        }
        //        BaseMod.addRelicToCustomPool(new LoadedDie(), TheSnecko.Enums.SNECKO_CYAN);
        if (!AbstractDungeon.player.hasRelic(LoadedDie.ID)) {
            ids.add(LoadedDie.ID);
        }
        //        BaseMod.addRelicToCustomPool(new RareBoosterPack(), TheSnecko.Enums.SNECKO_CYAN);
        if (!AbstractDungeon.player.hasRelic(RareBoosterPack.ID)) {
            ids.add(RareBoosterPack.ID);
        }
        //        BaseMod.addRelicToCustomPool(new SleevedAce(), TheSnecko.Enums.SNECKO_CYAN);
        if (!AbstractDungeon.player.hasRelic(SleevedAce.ID)) {
            ids.add(SleevedAce.ID);
        }
        //        BaseMod.addRelicToCustomPool(new UnknownEgg(), TheSnecko.Enums.SNECKO_CYAN);
        if (!AbstractDungeon.player.hasRelic(UnknownEgg.ID)) {
            ids.add(UnknownEgg.ID);
        }
        //        BaseMod.addRelicToCustomPool(new SneckoCommon(), TheSnecko.Enums.SNECKO_CYAN);
        if (!AbstractDungeon.player.hasRelic(SneckoCommon.ID)) {
            ids.add(SneckoCommon.ID);
        }
        Collections.shuffle(ids, new Random(AbstractDungeon.miscRng.randomLong()));
        return RelicLibrary.getRelic(ids.get(0)).makeCopy();
    }


    private enum CUR_SCREEN {
        INTRO,
        AGREE,
        DISAGREE,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
