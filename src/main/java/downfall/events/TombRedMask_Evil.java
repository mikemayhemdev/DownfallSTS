package downfall.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RedMask;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import downfall.relics.RedIOU;
import downfall.relics.RedIOUUpgrade;

import java.util.ArrayList;
import java.util.Iterator;

public class TombRedMask_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:TombRedMask";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final int GOLD_AMT = 222;
    private CurScreen screen;
    private AbstractCard attack = null;
    private boolean MaskTaken = false;

    public TombRedMask_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/redMaskTomb.jpg");
        this.screen = CurScreen.INTRO;
        if (AbstractDungeon.player.hasRelic("Red Mask") && AbstractDungeon.player.hasRelic(RedIOU.ID)) {
            this.imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[1], true);
        }
        ArrayList<AbstractCard> cards = new ArrayList();
        Iterator var3 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();

        sortListToRarity(var3, cards, AbstractCard.CardRarity.UNCOMMON);
        if (cards.size() == 0){
            var3 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
            sortListToRarity(var3, cards, AbstractCard.CardRarity.COMMON);
        }
        if (cards.size() == 0){
            var3 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
            sortListToRarity(var3, cards, AbstractCard.CardRarity.BASIC);
        }
        if (cards.size() == 0){
            var3 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
            sortListToRarity(var3, cards, AbstractCard.CardRarity.RARE);
        }

        if (cards.size() > 0){
            this.attack = cards.get(AbstractDungeon.cardRng.random(cards.size() - 1));
            this.imageEventText.setDialogOption(OPTIONS[2] +  FontHelper.colorString(this.attack.name, "r") + OPTIONS[3]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[5]);
    }

    private void sortListToRarity(Iterator var3, ArrayList<AbstractCard> cards, AbstractCard.CardRarity rarity){
        while(var3.hasNext()) {
            AbstractCard c = (AbstractCard)var3.next();
            if (c.type == AbstractCard.CardType.ATTACK && c.rarity == rarity) {
                cards.add(c);
            }
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[5]);
                    this.screen = CurScreen.RESULT;
                    AbstractDungeon.player.loseRelic(RedIOU.ID);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new RedIOUUpgrade());

                    return;
                } else if (buttonPressed == 1 && !MaskTaken) {
                    AbstractDungeon.effectList.add(new PurgeCardEffect(this.attack));
                    AbstractDungeon.player.masterDeck.removeCard(this.attack);
                    AbstractRelic r = new RedMask();
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.clearAllDialogs();
                    if (AbstractDungeon.player.hasRelic("Red Mask") && AbstractDungeon.player.hasRelic(RedIOU.ID)) {
                        this.imageEventText.setDialogOption(OPTIONS[0]);
                    } else {
                        this.imageEventText.setDialogOption(OPTIONS[1], true);
                        this.imageEventText.setDialogOption(OPTIONS[5]);  //Shouldn't really ever happen unless mod things have removed the Bandit Contract
                    }
                    this.screen = CurScreen.INTRO;
                    this.MaskTaken = true;
                    return;
                } else {
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[5]);
                    if (!this.MaskTaken) {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    } else {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                    }
                    this.screen = CurScreen.RESULT;
                    return;
                }

            default:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private static enum CurScreen {
        INTRO,
        RESULT;

        private CurScreen() {
        }
    }
}
