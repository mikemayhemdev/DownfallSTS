//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.events;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import slimebound.cards.CheckThePlaybook;
import downfall.cards.curses.Icky;
import slimebound.cards.Tackle;

import java.util.Iterator;

public class ArtOfSlimeWar extends AbstractImageEvent {
    public static final String ID = "Slimebound:ArtOfSlimeWar";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_START;
    private static final String DIALOG_TRAP;
    private static final String DIALOG_READ;
    private static final String DIALOG_CHOSE_RUN;
    private static final String DIALOG_CHOSE_FIGHT;
    private static final String DIALOG_CHOSE_FLAT;
    private static final String DIALOG_IGNORE;
    private static final float HP_LOSS_PERCENT = 0.25F;
    private static final float MAX_HP_LOSS_PERCENT = 0.08F;
    private static final float A_2_HP_LOSS_PERCENT = 0.35F;
    private static final float A_2_MAX_HP_LOSS_PERCENT = 0.1F;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:ArtOfSlimeWar");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_START = DESCRIPTIONS[0];
        DIALOG_TRAP = DESCRIPTIONS[1];
        DIALOG_READ = DESCRIPTIONS[6];
        DIALOG_CHOSE_RUN = DESCRIPTIONS[2];
        DIALOG_CHOSE_FIGHT = DESCRIPTIONS[3];
        DIALOG_CHOSE_FLAT = DESCRIPTIONS[4];
        DIALOG_IGNORE = DESCRIPTIONS[5];
    }

    private int screenNum = 0;
    private int damage;
    private int maxHpLoss;
    private AbstractRelic relicMetric = null;

    public ArtOfSlimeWar() {
        super(NAME, DIALOG_START, "slimeboundResources/SlimeboundImages/events/slimeTome.jpg");
        this.imageEventText.setDialogOption(OPTIONS[0], CardLibrary.getCopy("Slimebound:Tackle", 1, 0));
        this.imageEventText.setDialogOption(OPTIONS[1], CardLibrary.getCopy("Slimebound:CheckThePlaybook"));
        this.imageEventText.setDialogOption(OPTIONS[2]);
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.damage = (int) ((float) AbstractDungeon.player.maxHealth * 0.45F);
            this.maxHpLoss = (int) ((float) AbstractDungeon.player.maxHealth * 0.15F);
        } else {
            this.damage = (int) ((float) AbstractDungeon.player.maxHealth * 0.35F);
            this.maxHpLoss = (int) ((float) AbstractDungeon.player.maxHealth * 0.1F);
        }

        if (this.maxHpLoss < 1) {
            this.maxHpLoss = 1;
        }

    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GOLDEN");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_TRAP);

                        AbstractCard card = new CheckThePlaybook();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        CardCrawlGame.screenShake.mildRumble(5.0F);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3], new Icky());
                        this.imageEventText.updateDialogOption(1, OPTIONS[4] + this.damage + OPTIONS[5]);
                        this.imageEventText.removeDialogOption(2);
                        this.imageEventText.setDialogOption(OPTIONS[6] + this.maxHpLoss + OPTIONS[7]);
                        return;
                    case 0:
                        this.imageEventText.updateBodyText(DIALOG_READ);
                        this.replaceAttacks();
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        this.imageEventText.updateBodyText(DIALOG_IGNORE);
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:
                switch (buttonPressed) {
                    case 0:
                        CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                        CardCrawlGame.sound.play("SLIME_SPLIT");
                        this.imageEventText.updateBodyText(DIALOG_CHOSE_RUN);
                        AbstractCard curse = new Icky();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_CHOSE_FIGHT);
                        CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                        CardCrawlGame.sound.play("MONSTER_BOOK_STAB_0");
                        AbstractDungeon.player.damage(new DamageInfo(null, this.damage));
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DIALOG_CHOSE_FLAT);
                        AbstractDungeon.player.decreaseMaxHealth(this.maxHpLoss);
                        CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        this.openMap();
                        return;
                }
            case 2:
                this.openMap();
                break;
            default:
                this.openMap();
        }

    }

    private void replaceAttacks() {
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        while (true) {
            AbstractCard e;
            do {
                if (!i.hasNext()) {
                    for (int i2 = 0; i2 < 3; ++i2) {
                        AbstractCard c = new Tackle();
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    return;
                }

                e = (AbstractCard) i.next();
            } while (!(e.hasTag(BaseModCardTags.BASIC_STRIKE)));

            i.remove();
        }
    }
}
