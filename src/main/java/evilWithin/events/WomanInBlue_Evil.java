//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package evilWithin.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PotionBelt;
import com.megacrit.cardcrawl.relics.ToyOrnithopter;
import com.megacrit.cardcrawl.relics.WhiteBeast;
import com.megacrit.cardcrawl.rewards.RewardItem;
import evilWithin.monsters.LadyInBlue;

import java.util.ArrayList;
import java.util.Collections;

public class WomanInBlue_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:WomanInBlue";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private WomanInBlue_Evil.CurScreen screen;

    public WomanInBlue_Evil() {
        super(NAME, DIALOG_1, "images/events/ladyInBlue.jpg");
        this.screen = WomanInBlue_Evil.CurScreen.INTRO;
        this.imageEventText.setDialogOption(OPTIONS[3]);
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.imageEventText.setDialogOption(OPTIONS[1] + MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.05F) + OPTIONS[2]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0]);
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = WomanInBlue_Evil.CurScreen.FIGHT;
                        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new LadyInBlue());
                        AbstractDungeon.getCurrRoom().rewards.clear();

                        for (int i = 0; i < 3; i++) {
                            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                        }

                        ArrayList<AbstractRelic> possRelics = new ArrayList<>();
                        if (!AbstractDungeon.player.hasRelic(WhiteBeast.ID)) possRelics.add(new WhiteBeast());
                        if (!AbstractDungeon.player.hasRelic(PotionBelt.ID)) possRelics.add(new PotionBelt());
                        if (!AbstractDungeon.player.hasRelic(ToyOrnithopter.ID)) possRelics.add(new ToyOrnithopter());

                        if (possRelics.size() == 0) {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                        } else {
                            Collections.shuffle(possRelics);
                            AbstractDungeon.getCurrRoom().addRelicToRewards(possRelics.get(0));
                        }

                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);

                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.enterCombatFromImage();
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[0]);
                        this.screen = WomanInBlue_Evil.CurScreen.RESULT;
                        if (AbstractDungeon.ascensionLevel >= 15) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                            CardCrawlGame.sound.play("BLUNT_FAST");
                            AbstractDungeon.player.damage(new DamageInfo((AbstractCreature) null, MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.05F), DamageType.HP_LOSS));
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        }
                        break;
                    default:
                        this.imageEventText.clearRemainingOptions();
                        this.openMap();

                }

                this.imageEventText.clearRemainingOptions();
                break;
            case RESULT:
                this.openMap();
                break;
            default:
                this.openMap();
        }

    }


    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
    }

    private static enum CurScreen {
        INTRO,
        RESULT,
        FIGHT;


        private CurScreen() {
        }
    }
}
