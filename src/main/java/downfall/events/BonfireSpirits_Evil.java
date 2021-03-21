package downfall.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.shrines.Bonfire;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.SpiritPoop;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import gremlin.characters.GremlinCharacter;

public class BonfireSpirits_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:BonfireSpirits";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Bonfire Elementals");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private CUR_SCREEN screen;
    private AbstractCard offeredCard;
    private boolean cardSelect;

    public BonfireSpirits_Evil() {
        super(NAME, DIALOG_1, "images/events/bonfire.jpg");
        this.screen = CUR_SCREEN.INTRO;
        this.offeredCard = null;
        this.cardSelect = false;
        this.imageEventText.setDialogOption(OPTIONS[0]);

    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GOOP");
        }

    }

    public void update() {
        super.update();
        if (this.cardSelect && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.offeredCard = AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
            switch (this.offeredCard.rarity) {
                case CURSE:
                    logMetricRemoveCardAndObtainRelic("Bonfire Elementals", "Offered Curse", this.offeredCard, new SpiritPoop());
                    break;
                case BASIC:
                    logMetricCardRemoval("Bonfire Elementals", "Offered Basic", this.offeredCard);
                    break;
                case COMMON:
                    logMetricCardRemovalAndHeal("Bonfire Elementals", "Offered Common", this.offeredCard, 5);
                case SPECIAL:
                    logMetricCardRemovalAndHeal("Bonfire Elementals", "Offered Special", this.offeredCard, 5);
                    break;
                case UNCOMMON:
                    int heal = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
                    logMetricCardRemovalAndHeal("Bonfire Elementals", "Offered Uncommon", this.offeredCard, heal);
                    break;
                case RARE:
                    int heal2 = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
                    logMetricCardRemovalHealMaxHPUp("Bonfire Elementals", "Offered Rare", this.offeredCard, heal2, 10);
            }

            this.setReward(this.offeredCard.rarity);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(this.offeredCard, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(this.offeredCard);
            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption(OPTIONS[1]);
            this.screen = CUR_SCREEN.COMPLETE;
            this.cardSelect = false;
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DIALOG_2);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.removeDialogOption(1);
                        if (AbstractDungeon.player.gold >= 150) {
                            if (AbstractDungeon.player instanceof GremlinCharacter) {
                                this.imageEventText.setDialogOption(OPTIONSALT[2]);
                            } else {
                                this.imageEventText.setDialogOption(OPTIONSALT[0]);
                            }
                        } else {
                            this.imageEventText.setDialogOption(OPTIONSALT[1], true);
                        }
                        this.screen = CUR_SCREEN.CHOOSE;
                        break;
                }
                break;
            case CHOOSE:
                switch (buttonPressed){
                    case 0: {
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, false, false, true);
                            this.cardSelect = true;
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[1]);
                        }
                        break;
                    }
                    case 1: {
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        AbstractDungeon.player.loseGold(150);
                        AbstractDungeon.player.increaseMaxHp(10, false);
                        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                        this.screen = CUR_SCREEN.COMPLETE;
                        break;
                    }
                }
                break;




            case COMPLETE:
                this.openMap();
        }

    }

    private void setReward(AbstractCard.CardRarity rarity) {
        String dialog = DIALOG_3;
        switch (rarity) {
            case CURSE:
                dialog = dialog + DESCRIPTIONS[3];
                if (!AbstractDungeon.player.hasRelic("Spirit Poop")) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, RelicLibrary.getRelic("Spirit Poop").makeCopy());
                } else {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
                }
                break;
            case BASIC:
                dialog = dialog + DESCRIPTIONS[4];
                break;
            case COMMON:
            case SPECIAL:
                dialog = dialog + DESCRIPTIONS[5];
                AbstractDungeon.player.heal(5);
                break;
            case UNCOMMON:
                dialog = dialog + DESCRIPTIONS[6];
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                break;
            case RARE:
                dialog = dialog + DESCRIPTIONS[7];
                AbstractDungeon.player.increaseMaxHp(10, false);
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        }

        this.imageEventText.updateBodyText(dialog);
    }

    private enum CUR_SCREEN {
        INTRO,
        CHOOSE,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
