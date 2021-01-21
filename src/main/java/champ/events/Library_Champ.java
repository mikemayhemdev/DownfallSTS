
package champ.events;

import champ.relics.BerserkersGuideToSlaughter;
import champ.relics.DefensiveTrainingManual;
import champ.relics.FightingForDummies;
import champ.relics.GladiatorsBookOfMartialProwess;
import champ.stances.DefensiveStance;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
import downfall.events.FaceTrader_Evil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Library_Champ extends AbstractImageEvent {
    public static final String ID = "champ:Library";
    private static final EventStrings eventStrings;
    private static final EventStrings eventStrings2;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final String DIALOG_1;
    private static final String SLEEP_RESULT;
    private int screenNum = 0;
    private boolean pickCard = false;
    private static final float HP_HEAL_PERCENT = 0.33F;
    private static final float A_2_HP_HEAL_PERCENT = 0.2F;
    private int healAmt;

    public Library_Champ() {
        super(NAME, DIALOG_1, "images/events/library.jpg");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.2F);
        } else {
            this.healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.33F);
        }

        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1] + this.healAmt + OPTIONS[2]);

        if (AbstractDungeon.player.hasRelic(DefensiveTrainingManual.ID) &&
                AbstractDungeon.player.hasRelic(FightingForDummies.ID) &&
                AbstractDungeon.player.hasRelic(GladiatorsBookOfMartialProwess.ID) &&
                AbstractDungeon.player.hasRelic(BerserkersGuideToSlaughter.ID)){

            this.imageEventText.setDialogOption(OPTIONSALT[1], true);
        } else {

            this.imageEventText.setDialogOption(OPTIONSALT[0]);
        }
    }

    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeCopy();
            logMetricObtainCard("The Library", "Read", c);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(this.getBook());
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        this.pickCard = true;
                        CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);

                        for(int i = 0; i < 20; ++i) {
                            AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                            boolean containsDupe = true;

                            while(true) {
                                Iterator var6;
                                while(containsDupe) {
                                    containsDupe = false;
                                    var6 = group.group.iterator();

                                    while(var6.hasNext()) {
                                        AbstractCard c = (AbstractCard)var6.next();
                                        if (c.cardID.equals(card.cardID)) {
                                            containsDupe = true;
                                            card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                                            break;
                                        }
                                    }
                                }

                                if (group.contains(card)) {
                                    --i;
                                } else {
                                    var6 = AbstractDungeon.player.relics.iterator();

                                    while(var6.hasNext()) {
                                        AbstractRelic r = (AbstractRelic)var6.next();
                                        r.onPreviewObtainCard(card);
                                    }

                                    group.addToBottom(card);
                                }
                                break;
                            }
                        }

                        Iterator var8 = group.group.iterator();

                        while(var8.hasNext()) {
                            AbstractCard c = (AbstractCard)var8.next();
                            UnlockTracker.markCardAsSeen(c.cardID);
                        }

                        AbstractDungeon.gridSelectScreen.open(group, 1, OPTIONS[4], false);
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(SLEEP_RESULT);
                        AbstractDungeon.player.heal(this.healAmt, true);
                        logMetricHeal("The Library", "Heal", this.healAmt);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        AbstractRelic r = this.getRandomFace();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, r);
                        downfallMod.removeAnyRelicFromPools(r.relicId);
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();

                        return;
                }
            default:
                this.openMap();
        }
    }

    private String getBook() {
        ArrayList<String> list = new ArrayList();
        list.add(DESCRIPTIONS[2]);
        list.add(DESCRIPTIONS[3]);
        list.add(DESCRIPTIONS[4]);
        return (String)list.get(MathUtils.random(2));
    }

    private AbstractRelic getRandomFace() {
        ArrayList<String> ids = new ArrayList();
        if (!AbstractDungeon.player.hasRelic(BerserkersGuideToSlaughter.ID)) {
            ids.add(BerserkersGuideToSlaughter.ID);
        }

        if (!AbstractDungeon.player.hasRelic(DefensiveTrainingManual.ID)) {
            ids.add(DefensiveTrainingManual.ID);
        }

        if (!AbstractDungeon.player.hasRelic(FightingForDummies.ID)) {
            ids.add(FightingForDummies.ID);
        }

        if (!AbstractDungeon.player.hasRelic(GladiatorsBookOfMartialProwess.ID)) {
            ids.add(GladiatorsBookOfMartialProwess.ID);
        }

        Collections.shuffle(ids, new Random(AbstractDungeon.miscRng.randomLong()));
        return RelicLibrary.getRelic(ids.get(0)).makeCopy();
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Library");
        eventStrings2 = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = eventStrings2.DESCRIPTIONS;
        OPTIONSALT = eventStrings2.OPTIONS;
        DIALOG_1 = DESCRIPTIONSALT[0];
        SLEEP_RESULT = DESCRIPTIONS[1];
    }
}
