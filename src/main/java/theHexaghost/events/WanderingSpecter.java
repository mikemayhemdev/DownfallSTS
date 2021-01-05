package theHexaghost.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
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

    private int curseCount = 0;

    public WanderingSpecter() {
        super(NAME, DESCRIPTIONS[0], "hexamodResources/images/events/specter.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;


        ArrayList<String> possRelicsList = new ArrayList<>();
        possRelicsList.add(BlueCandle.ID);
        possRelicsList.add(DarkstonePeriapt.ID);
        possRelicsList.add(DuVuDoll.ID);
        possRelicsList.add(CursedKey.ID);
        possRelicsList.add(CallingBell.ID);

        for (AbstractRelic q : AbstractDungeon.player.relics) {
            possRelicsList.removeIf(q.relicId::equals);
        }

        if (possRelicsList.size() > 0) {
            rtog = RelicLibrary.getRelic(possRelicsList.get(AbstractDungeon.cardRandomRng.random(possRelicsList.size() - 1))).makeCopy();
            switch (rtog.relicId){
                case BlueCandle.ID:{
                    curseCount = 1;
                    break;
                }
                case CallingBell.ID:{
                    curseCount = 1;
                    break;
                }
                case Omamori.ID:{
                    curseCount = 1;
                    break;
                }
                case DuVuDoll.ID:{
                    curseCount = 2;
                    break;
                }
                case DarkstonePeriapt.ID:{
                    curseCount = 3;
                    break;
                }
                case CursedKey.ID:{
                    curseCount = 4;
                    break;
                }
            }
            this.imageEventText.setDialogOption(OPTIONS[0], new Haunted());
        } else {
            imageEventText.setDialogOption(OPTIONS[3], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        for (int i = 0; i < curseCount; i++) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((CardLibrary.getCurse().makeStatEquivalentCopy()), (float) (Settings.WIDTH * .75F * ((i / curseCount))), (float) (Settings.HEIGHT / 2)));// 73
                        }
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rtog);// 83
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        this.imageEventText.setDialogOption(OPTIONS[6]);
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.screen = CurScreen.TRADE;
                        return;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        return;
                }
                return;
            case TRADE:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .3F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE,AbstractDungeon.cardRng).makeStatEquivalentCopy(), (float) (Settings.WIDTH * .7F), (float) (Settings.HEIGHT / 2)));// 66

                        this.imageEventText.updateDialogOption(0, OPTIONS[7], true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);

                        return;
                    case 1:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.player.increaseMaxHp(5, true);
                        this.imageEventText.updateDialogOption(1, OPTIONS[7], true);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);

                        return;
                    case 2:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
                        AbstractDungeon.effectList.add(new RainingGoldEffect(100));
                        AbstractDungeon.player.gainGold(100);
                        this.imageEventText.updateDialogOption(2, OPTIONS[7], true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);

                        return;
                    case 3:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
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
