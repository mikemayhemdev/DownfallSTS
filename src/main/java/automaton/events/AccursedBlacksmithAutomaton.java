//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package automaton.events;

import automaton.AutomatonMod;
import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.WarpedTongs;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

import java.util.ArrayList;
import java.util.Collections;

public class AccursedBlacksmithAutomaton extends AbstractImageEvent {
    public static final String ID = "bronze:AccursedBlacksmith";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSAUTOMATON;
    public static final String[] OPTIONSAUTOMATON;
    private static final EventStrings eventStrings;
    private static final EventStrings eventStringsAUTOMATON;
    private static final String DIALOG_1;
    private static final String FORGE_RESULT;
    private static final String RUMMAGE_RESULT;
    private static final String CURSE_RESULT2;
    private static final String LEAVE_RESULT;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Accursed Blacksmith");
        eventStringsAUTOMATON = CardCrawlGame.languagePack.getEventString("bronze:AccursedBlacksmith");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSAUTOMATON = eventStringsAUTOMATON.DESCRIPTIONS;
        OPTIONSAUTOMATON = eventStringsAUTOMATON.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        FORGE_RESULT = DESCRIPTIONS[1];
        RUMMAGE_RESULT = DESCRIPTIONS[2];
        CURSE_RESULT2 = DESCRIPTIONS[4];
        LEAVE_RESULT = DESCRIPTIONS[5];
    }

    private int screenNum = 0;
    private boolean pickCard = false;
    private boolean pickCardForSocket = false;

    private ArrayList<AbstractCard> validCards;

    public AccursedBlacksmithAutomaton() {
        super(NAME, DIALOG_1, "images/events/blacksmith.jpg");

        validCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.upgraded) {
                validCards.add(c);
            }
        }
        if (validCards.size() == 0) {
            this.imageEventText.setDialogOption(OPTIONSAUTOMATON[1], true);

        } else {
            this.imageEventText.setDialogOption(OPTIONSAUTOMATON[0]);

        }

        if (AbstractDungeon.player.masterDeck.hasUpgradableCards()) {
            this.imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[1], CardLibrary.getCopy("Pain"));
        this.imageEventText.setDialogOption(OPTIONS[2]);


    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_FORGE");
        }

    }


    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.pickCardForSocket = true;
                        this.imageEventText.updateBodyText(DESCRIPTIONSAUTOMATON[0]);
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        Collections.shuffle(validCards);
                        for (int i = 0; i < 3; i++) {
                            if (validCards.size() - 1 >= i){
                                validCards.get(i).upgrade();
                                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(validCards.get(i).makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT));

                            }
                        }

                        break;
                    case 1:
                        this.pickCard = true;
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[3], true, false, false, false);
                        this.imageEventText.updateBodyText(FORGE_RESULT);
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        break;
                    case 2:
                        this.screenNum = 2;
                        this.imageEventText.updateBodyText(RUMMAGE_RESULT + CURSE_RESULT2);
                        AbstractCard curse = new Pain();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new WarpedTongs());
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        break;
                    case 3:
                        this.screenNum = 2;
                        this.imageEventText.updateBodyText(LEAVE_RESULT);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                }

                this.imageEventText.clearRemainingOptions();
                break;
            default:

                this.openMap();
        }

    }
}
