package sneckomod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import java.util.ArrayList;
import java.util.Collections;

public class D8 extends AbstractImageEvent {
    public static final String ID = "sneckomod:D8";
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

    private int finalDmg;
    private CurScreen screen;

    public D8() {
        super(NAME, DESCRIPTIONS[0], "sneckomodResources/images/events/D8.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.finalDmg = 15;
        } else {
            this.finalDmg = 10;
        }

        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            if (c.hasTag(SneckoMod.RNG)) {
                tmp.addToTop(c);
            }
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + this.finalDmg + OPTIONS[1]);
        if (tmp.size() > 0) {
            this.imageEventText.setDialogOption(OPTIONS[2], new Pain(), new sneckomod.relics.D8());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {
                    case 0:
                        ArrayList<AbstractCard> list = new ArrayList<>();// 1201
                        for (AbstractCard c : CardLibrary.getAllCards()) {
                            if (c instanceof AbstractUnknownCard)
                                list.add(c);
                        }
                        Collections.shuffle(list);

                        int times = 0;
                        for (AbstractCard c : list) {

                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH * (0.1 + (0.2 * times))), (float) (Settings.HEIGHT / 2)));
                            times++;
                            if (times == 5) break;
                        }

                        CardCrawlGame.sound.play("EVENT_TOME");
                        this.imageEventText.clearAllDialogs();
                        AbstractDungeon.player.damage(new DamageInfo(null, this.finalDmg, DamageType.HP_LOSS));
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        list.clear();
                        break;
                    case 1:

                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pain(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new sneckomod.relics.D8());

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.screen = CurScreen.END;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.screen = CurScreen.END;
                        return;
                }


            case END:
                this.openMap();
        }

    }

    private enum CurScreen {
        INTRO,
        END;

        CurScreen() {
        }
    }
}
