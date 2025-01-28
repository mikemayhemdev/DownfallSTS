/*    */
package downfall.events.shrines_evil;
/*    */
/*    */

import com.megacrit.cardcrawl.cards.curses.Pain;
import downfall.cards.curses.Aged;
import downfall.cards.curses.Sapped;
import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.events.AbstractImageEvent;
/*    */
/*    */
import com.megacrit.cardcrawl.localization.EventStrings;
/*    */
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
/*    */ import java.util.ArrayList;

/*    */
/*    */ public class DuplicatorEvil extends AbstractImageEvent {
    public static final String ID = downfallMod.makeID("Duplicator");

    private static final EventStrings eventStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getEventString("Duplicator");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String[] DESCRIPTIONSALT;
    public static String[] OPTIONSALT;


    private int screenNum = 0;
    private static final String DIALOG_1 = DESCRIPTIONS[0];
    private static final String DIALOG_2 = DESCRIPTIONS[1];
    private static final String IGNORE = DESCRIPTIONS[2];

    public DuplicatorEvil() {
        super(NAME, DIALOG_1, "images/events/shrine4.jpg");
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").OPTIONS;

        this.imageEventText.setDialogOption(OPTIONSALT[0], new Aged());
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        com.megacrit.cardcrawl.core.CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            ArrayList<String> cards = new ArrayList<>();

            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeStatEquivalentCopy();
            c.inBottleFlame = false;
            c.inBottleLightning = false;
            c.inBottleTornado = false;
            AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect(c, com.megacrit.cardcrawl.core.Settings.WIDTH * 0.25F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2.0F));
            cards.add(c.cardID);

            if (AbstractDungeon.gridSelectScreen.selectedCards.size() > 1){
                c = AbstractDungeon.gridSelectScreen.selectedCards.get(1).makeStatEquivalentCopy();
                c.inBottleFlame = false;
                c.inBottleLightning = false;
                c.inBottleTornado = false;
                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect(c, com.megacrit.cardcrawl.core.Settings.WIDTH * 0.75F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2.0F));
                cards.add(c.cardID);

                AbstractCard curse = new Aged();
                cards.add(curse.cardID);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66

                logMetricObtainCards(ID, "Desecrated", cards);
            } else {
                logMetricObtainCard(ID, "Copied", c);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }


    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        use(2);
                        this.screenNum = 2;
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_2);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        use(1);
                        this.screenNum = 2;
                        break;
                    case 2:
                        this.screenNum = 2;
                        this.imageEventText.updateBodyText(IGNORE);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        logMetricIgnored(ID);
                }

                break;
            case 1:
                this.screenNum = 2;
                break;
            case 2:
                openMap();
                break;
        }

    }

    public void use(int count) {
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, count, OPTIONS[2], false, false, false, false);
    }


}


