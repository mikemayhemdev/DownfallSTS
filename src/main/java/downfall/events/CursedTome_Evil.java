package downfall.events;


import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import java.util.ArrayList;

public class CursedTome_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:CursedTome";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = CardCrawlGame.languagePack.getEventString("Cursed Tome").OPTIONS;
    }

    private int finalDmg;
    private CurScreen screen;

    public CursedTome_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/cursedTome.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.finalDmg = 21;
        } else {
            this.finalDmg = 16;
        }

        this.imageEventText.setDialogOption(OPTIONS[5] + this.finalDmg + OPTIONS[6]);
        this.imageEventText.setDialogOption(OPTIONS[7]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                if (buttonPressed == 0) {
                    CardCrawlGame.sound.play("EVENT_TOME");
                    this.imageEventText.clearAllDialogs();
                    AbstractDungeon.player.damage(new DamageInfo(null, this.finalDmg, DamageType.HP_LOSS));
                    this.imageEventText.setDialogOption(OPTIONS[7]);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.screen = CurScreen.END;
                    this.randomBook();
                    return;
                } else {
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[7]);
                    this.imageEventText.updateBodyText(CardCrawlGame.languagePack.getEventString("Cursed Tome").DESCRIPTIONS[6]);
                    this.screen = CurScreen.END;
                    return;
                }

            case END:
                this.openMap();
        }

    }

    private void randomBook() {
        ArrayList<AbstractRelic> possibleBooks = new ArrayList();
        if (!AbstractDungeon.player.hasRelic("Necronomicon")) {
            possibleBooks.add(RelicLibrary.getRelic("Necronomicon").makeCopy());
        }

        if (!AbstractDungeon.player.hasRelic("Enchiridion")) {
            possibleBooks.add(RelicLibrary.getRelic("Enchiridion").makeCopy());
        }

        if (!AbstractDungeon.player.hasRelic("Nilry's Codex")) {
            possibleBooks.add(RelicLibrary.getRelic("Nilry's Codex").makeCopy());
        }

        if (possibleBooks.size() == 0) {
            possibleBooks.add(RelicLibrary.getRelic("Circlet").makeCopy());
        }

        AbstractRelic r = possibleBooks.get(AbstractDungeon.miscRng.random(possibleBooks.size() - 1));
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().addRelicToRewards(r);
        AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
        this.screen = CurScreen.END;
    }

    private enum CurScreen {
        INTRO,
        END;

        CurScreen() {
        }
    }
}
