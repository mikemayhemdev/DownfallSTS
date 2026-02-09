package champ.events;

import basemod.helpers.CardModifierManager;
import champ.util.TechniqueMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import downfall.cards.curses.PrideStandard;

import java.util.ArrayList;

public class TechniqueTome extends AbstractImageEvent {
    public static final String ID = "champ:TechniqueTome";
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

    private int screenNum = 0;
    private boolean pickCard = false;
    private int hpCost = 5;
    private int hpSpent = 0;
    private final ArrayList<String> cardsTeched = new ArrayList<>();
    private int prideGained;

    public TechniqueTome() {
        super(NAME, DESCRIPTIONS[0], "champResources/images/events/book.png");

        if (getNonTechniqueCards().size() == 0) {
            this.imageEventText.setDialogOption(OPTIONS[3], true);

        } else {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.hpCost + OPTIONS[1]);

        }

        this.imageEventText.setDialogOption(OPTIONS[2]);


    }

    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardModifierManager.addModifier(c, new TechniqueMod());
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            cardsTeched.add(c.cardID);

            this.pickCard = false;
            if (this.hpCost == 5) {

                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
            } else {

                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
            }
            this.hpCost = this.hpCost * 2;
            if (this.hpCost > 20) {
                if (getNonTechniqueCards().size() == 0) {
                    this.imageEventText.updateDialogOption(0, OPTIONS[5], true);
                } else {
                    this.imageEventText.updateDialogOption(0, OPTIONS[5], new PrideStandard());
                }
            } else {
                if (getNonTechniqueCards().size() == 0) {
                    this.imageEventText.updateDialogOption(0, OPTIONS[0] + this.hpCost + OPTIONS[1], true);
                } else {
                    this.imageEventText.updateDialogOption(0, OPTIONS[0] + this.hpCost + OPTIONS[1]);
                }
            }

        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.pickCard = true;
                        if (this.hpCost > 20){
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new PrideStandard(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                            prideGained++;
                        } else {
                            hpSpent += hpCost;
                            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.hpCost));
                            AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.POISON));
                        }
                        AbstractDungeon.gridSelectScreen.open(getNonTechniqueCards(), 1, OPTIONS[4], false, false, false, false);

                        return;
                    case 1:
                        this.screenNum = 1;
                        if (this.hpCost == 5) {

                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                            logMetricIgnored(ID);
                        } else {

                            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                            ArrayList<String> curses = new ArrayList<>();
                            for (int i = 0; i < prideGained; i++) {
                                curses.add(Pride.ID);
                            }
                            logMetric(ID, "Read", prideGained > 0 ? curses : null, null, null, cardsTeched,
                                    null, null, null,
                                    hpSpent, 0, 0, 0, 0, 0);

                        }

                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);

                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:

                this.openMap();
        }

    }

    public static CardGroup getNonTechniqueCards() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.ATTACK && !CardModifierManager.hasModifier(c, TechniqueMod.ID)) {
                retVal.group.add(c);
            }
        }
        return retVal;
    }
}
