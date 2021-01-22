package theHexaghost.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
import downfall.relics.ExtraCursedBell;
import downfall.relics.ExtraCursedKey;
import theHexaghost.HexaMod;
import downfall.cards.curses.Haunted;

import java.util.ArrayList;

public class WanderingSpecter extends AbstractImageEvent {
    public static final String ID = HexaMod.makeID("WanderingSpecter");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;
    private AbstractRelic rtog;
    private AbstractRelic rtog2;

    private boolean shopForMore;
    private boolean shopForMore2;


    private int curseCount = 2;

    public WanderingSpecter() {
        super(NAME, DESCRIPTIONS[0], "hexamodResources/images/events/specter.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;


        ArrayList<String> possRelicsList = new ArrayList<>();
        ArrayList<String> possRelicsList2 = new ArrayList<>();
        if (!AbstractDungeon.player.hasRelic(DarkstonePeriapt.ID)) possRelicsList.add(DarkstonePeriapt.ID);
        if (!AbstractDungeon.player.hasRelic(DuVuDoll.ID)) possRelicsList.add(DuVuDoll.ID);
        if (!AbstractDungeon.player.hasRelic(CursedKey.ID) && !AbstractDungeon.player.hasRelic(ExtraCursedKey.ID)) possRelicsList2.add(ExtraCursedKey.ID);
        if (!AbstractDungeon.player.hasRelic(CallingBell.ID) && !AbstractDungeon.player.hasRelic(ExtraCursedBell.ID)) possRelicsList2.add(ExtraCursedBell.ID);


        if (possRelicsList.size() > 0) {
            rtog = RelicLibrary.getRelic(possRelicsList.get(AbstractDungeon.cardRandomRng.random(possRelicsList.size() - 1))).makeCopy();
            this.imageEventText.setDialogOption(OPTIONS[0], new Haunted());
        } else {
            imageEventText.setDialogOption(OPTIONS[1]);
            shopForMore = true;
        }
        if (possRelicsList2.size() > 0) {
            rtog2 = RelicLibrary.getRelic(possRelicsList2.get(AbstractDungeon.cardRandomRng.random(possRelicsList.size() - 1))).makeCopy();
            this.imageEventText.setDialogOption(OPTIONS[2]);
        } else {
            imageEventText.setDialogOption(OPTIONS[1]);
            shopForMore2 = true;
        }
        if (!AbstractDungeon.player.hasRelic(BlueCandle.ID)){
            this.imageEventText.setDialogOption(OPTIONS[3], new BlueCandle());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4]);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        if (!shopForMore) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Haunted(), (float) (Settings.WIDTH * .35F), (float) (Settings.HEIGHT / 2)));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Haunted(), (float) (Settings.WIDTH * .65F), (float) (Settings.HEIGHT / 2)));

                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rtog);// 83
                            downfallMod.removeAnyRelicFromPools(rtog.relicId);
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        this.imageEventText.setDialogOption(OPTIONS[6]);
                        this.imageEventText.setDialogOption(OPTIONS[7]);
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.screen = CurScreen.TRADE;
                        return;
                    case 1:
                        if (!shopForMore2) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rtog2);// 83
                            if (rtog2 instanceof ExtraCursedBell) {
                                downfallMod.removeAnyRelicFromPools(CallingBell.ID);
                            } else {
                                downfallMod.removeAnyRelicFromPools(CursedKey.ID);
                            }
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        this.imageEventText.setDialogOption(OPTIONS[6]);
                        this.imageEventText.setDialogOption(OPTIONS[7]);
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.screen = CurScreen.TRADE;
                        return;
                    case 2:
                        if (AbstractDungeon.player.hasRelic(BlueCandle.ID)){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new BlueCandle());// 83
                            AbstractDungeon.player.damage(new DamageInfo(null, 5, DamageInfo.DamageType.HP_LOSS));
                            downfallMod.removeAnyRelicFromPools(BlueCandle.ID);
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.screen = CurScreen.END;
                        return;
                }
                return;
            case TRADE:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .3F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE,AbstractDungeon.cardRng).makeStatEquivalentCopy(), (float) (Settings.WIDTH * .7F), (float) (Settings.HEIGHT / 2)));// 66

                        this.imageEventText.updateDialogOption(0, OPTIONS[8], true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);

                        return;
                    case 1:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.player.increaseMaxHp(5, true);
                        this.imageEventText.updateDialogOption(1, OPTIONS[8], true);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);

                        return;
                    case 2:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.effectList.add(new RainingGoldEffect(100));
                        AbstractDungeon.player.gainGold(100);
                        this.imageEventText.updateDialogOption(2, OPTIONS[8], true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);

                        return;
                    case 3:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        return;
                }
                return;
            case END:
                this.openMap();
        }

    }

    private enum CurScreen {
        INTRO,
        TRADE,
        END;

        CurScreen() {
        }
    }
}
