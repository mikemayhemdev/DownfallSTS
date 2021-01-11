//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
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
import downfall.downfallMod;
import downfall.monsters.LadyInBlue;

import java.util.ArrayList;
import java.util.Collections;

public class WomanInBlue_Evil extends AbstractImageEvent {
    public static final String ID = downfallMod.makeID("WomanInBlue");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
    }

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
                        AbstractDungeon.getCurrRoom().monsters =  MonsterHelper.getEncounter(LadyInBlue.ID);
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
                            AbstractRelic r = possRelics.get(0);
                            switch (r.relicId) {
                                case WhiteBeast.ID:
                                    AbstractDungeon.uncommonRelicPool.remove(WhiteBeast.ID);
                                    break;
                                case PotionBelt.ID:
                                    AbstractDungeon.commonRelicPool.remove(PotionBelt.ID);
                                    break;
                                case ToyOrnithopter.ID:
                                    AbstractDungeon.shopRelicPool.remove(ToyOrnithopter.ID);
                                    break;
                            }
                            AbstractDungeon.getCurrRoom().addRelicToRewards(possRelics.get(0));
                            downfallMod.removeAnyRelicFromPools(possRelics.get(0).relicId);
                        }

                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);

                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.enterCombatFromImage();
                        AbstractDungeon.lastCombatMetricKey = LadyInBlue.ID;
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[0]);
                        this.screen = WomanInBlue_Evil.CurScreen.RESULT;
                        if (AbstractDungeon.ascensionLevel >= 15) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                            CardCrawlGame.sound.play("BLUNT_FAST");
                            AbstractDungeon.player.damage(new DamageInfo(null, MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.05F), DamageType.HP_LOSS));
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

    private enum CurScreen {
        INTRO,
        RESULT,
        FIGHT;


        CurScreen() {
        }
    }
}
