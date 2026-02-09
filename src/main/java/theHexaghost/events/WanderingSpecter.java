package theHexaghost.events;


import collector.CollectorChar;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;
import downfall.relics.BlackCandle;
import downfall.relics.ExtraCursedBell;
import downfall.relics.ExtraCursedKey;
import hermit.characters.hermit;
import theHexaghost.HexaMod;
import downfall.cards.curses.Haunted;

import java.util.ArrayList;
import java.util.List;

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
        if ((!AbstractDungeon.player.hasRelic(BlueCandle.ID)) || (!AbstractDungeon.player.hasRelic(BlackCandle.ID))) {
            if (EvilModeCharacterSelect.evilMode || (AbstractDungeon.player instanceof hermit) && !downfallMod.disableBaseGameAdjustments) {
                this.imageEventText.setDialogOption(OPTIONS[3], new BlackCandle());
            } else {
                this.imageEventText.setDialogOption(OPTIONS[3], new BlueCandle());
            }
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4]);
        }
    }
    private List<String> relicsAdded = new ArrayList<>();
    private List<String> cardsAdded = new ArrayList<>();
    private int maxHpAdded = 0;
    private int goldAdded = 0;


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
                            relicsAdded.add(rtog.relicId);
                            cardsAdded.add(Haunted.ID);
                            cardsAdded.add(Haunted.ID);
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
                            relicsAdded.add(rtog2.relicId);

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
                        if ((AbstractDungeon.player.hasRelic(BlueCandle.ID)) || (AbstractDungeon.player.hasRelic(BlackCandle.ID))){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
//                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new BlueCandle());// 83
                            AbstractDungeon.player.damage(new DamageInfo(null, 5, DamageInfo.DamageType.HP_LOSS));
                            AbstractDungeon.getCurrRoom().rewards.clear();
                            if (!EvilModeCharacterSelect.evilMode && !(AbstractDungeon.player instanceof hermit) && !downfallMod.disableBaseGameAdjustments) {
                                AbstractDungeon.getCurrRoom().addRelicToRewards(new BlueCandle());
                                downfallMod.removeAnyRelicFromPools(BlueCandle.ID);
                            } else {
                                AbstractDungeon.getCurrRoom().addRelicToRewards(new BlackCandle());
                                downfallMod.removeAnyRelicFromPools(BlackCandle.ID);
                            }
                            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                            AbstractDungeon.combatRewardScreen.open();
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.screen = CurScreen.END;
                        if (!EvilModeCharacterSelect.evilMode && !(AbstractDungeon.player instanceof hermit)) {
                            logMetricObtainRelicAndDamage(ID, "Chased Away", new BlueCandle(), 5);
                        }
                        if (EvilModeCharacterSelect.evilMode || (AbstractDungeon.player instanceof hermit)) {
                            logMetricObtainRelicAndDamage(ID, "Chased Away", new BlackCandle(), 5);
                        }
                        return;
                }
                return;
            case TRADE:
                AbstractCard curse;
                switch (buttonPressed) {
                    case 0:
                        curse = CardLibrary.getCurse().makeStatEquivalentCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .3F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractCard rareCard = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, AbstractDungeon.cardRng).makeStatEquivalentCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rareCard, (float) (Settings.WIDTH * .7F), (float) (Settings.HEIGHT / 2)));// 66

                        this.imageEventText.updateDialogOption(0, OPTIONS[8], true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);

                        cardsAdded.add(curse.cardID);
                        cardsAdded.add(rareCard.cardID);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.screen = CurScreen.END;

                        return;
                    case 1:
                        curse = CardLibrary.getCurse().makeStatEquivalentCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.player.increaseMaxHp(12, true);
                        this.imageEventText.updateDialogOption(1, OPTIONS[8], true);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        cardsAdded.add(curse.cardID);
                        maxHpAdded = 12;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.screen = CurScreen.END;

                        return;
                    case 2:
                        curse = CardLibrary.getCurse().makeStatEquivalentCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.effectList.add(new RainingGoldEffect(180));
                        AbstractDungeon.player.gainGold(180);
                        this.imageEventText.updateDialogOption(2, OPTIONS[8], true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);

                        cardsAdded.add(curse.cardID);
                        goldAdded = 180;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.screen = CurScreen.END;

                        return;
                    case 3:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        logMetric(ID, "Took Box", cardsAdded, null, null, null,
                                relicsAdded, null, null,
                                0, 0, 0, maxHpAdded, goldAdded, 0);
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
