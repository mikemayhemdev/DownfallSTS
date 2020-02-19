package evilWithin.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class ForgottenAltar_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:ForgottenAltar";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private int hpLoss;
    private int goldCost = 50;
    private CurScreen screen;

    public ForgottenAltar_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/forgottenAltar.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLoss = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.35F);
        } else {
            this.hpLoss = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.25F);
        }

        if (AbstractDungeon.player.gold >= goldCost){
            this.imageEventText.setDialogOption(OPTIONSALT[0] + this.goldCost + OPTIONSALT[1] + this.hpLoss + OPTIONSALT[2], new Apparition());
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[3] + this.goldCost + OPTIONSALT[4],true);

        }

        this.imageEventText.setDialogOption(OPTIONS[2] + 5 + OPTIONS[3] + this.hpLoss + OPTIONS[4]);
        this.imageEventText.setDialogOption(OPTIONSALT[5]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        AbstractDungeon.player.loseGold(this.goldCost);
                        AbstractDungeon.player.heal(5, true);
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        CardCrawlGame.sound.play("HEAL_1");
                        this.screenNum = 1;
                        return;
                    case 1:

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        AbstractDungeon.player.increaseMaxHp(5, false);
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.hpLoss));
                        CardCrawlGame.sound.play("HEAL_3");
                        this.screenNum = 1;

                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        this.screenNum = 1;
                        return;
                    default:
                        return;
                }
            default:
                this.openMap();
        }
    }


    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Forgotten Altar");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private static enum CurScreen {
        INTRO,
        END;

        private CurScreen() {
        }
    }
}
