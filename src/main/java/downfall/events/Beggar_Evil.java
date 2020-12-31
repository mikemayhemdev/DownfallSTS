package downfall.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.Centurion;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
import slimebound.SlimeboundMod;

public class Beggar_Evil extends AbstractImageEvent {
    public static final String ID = downfallMod.makeID("Beggar");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    public static final String[] DESCRIPTIONSOG;
    public static final String[] OPTIONSOG;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Beggar");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
        DESCRIPTIONSOG = CardCrawlGame.languagePack.getEventString(Cleric_Evil.ID).DESCRIPTIONS;
        OPTIONSOG = CardCrawlGame.languagePack.getEventString(Cleric_Evil.ID).OPTIONS;
    }

    private int finalDmg;
    private int gold;
    private CurScreen screen;
    private int cardsToRemove;

    public Beggar_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/beggar.jpg");
        this.noCardsInRewards = true;

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.gold = 50;
        } else {
            this.gold = 100;
        }

        this.imageEventText.setDialogOption(OPTIONS[4]);
        this.screen = CurScreen.INTRO;
        this.cardsToRemove = 1;
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            if (cardsToRemove == 1) {
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
            } else {
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (Settings.WIDTH * 0.3F), (float) (Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
                c = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (Settings.WIDTH * 0.7F), (float) (Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
            }
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                //SlimeboundMod.logger.info("Cleric Encountered: " + Cleric_Evil.encountered + ". Cleric Killed: " + Cleric_Evil.heDead + ".");
                if (!Cleric_Evil.encountered) {
                    this.imageEventText.loadImage("images/events/cleric.jpg");
                    this.screen = CurScreen.CLERICFRESHINTRO;
                    this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);

                    this.imageEventText.setDialogOption(OPTIONSOG[0] + this.gold + OPTIONSOG[4]);
                    this.imageEventText.setDialogOption(OPTIONSOG[1]);

                } else if (Cleric_Evil.heDead) {
                    this.screen = CurScreen.CLERICDEADINTRO;
                    this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);

                    this.imageEventText.setDialogOption(OPTIONSOG[0] + this.gold + OPTIONSOG[4] + OPTIONSALT[0], new Pride());
                    this.imageEventText.setDialogOption(OPTIONS[5]);
                } else {
                    this.imageEventText.loadImage("images/events/cleric.jpg");
                    this.screen = CurScreen.CLERICALIVEINTRO;
                    this.imageEventText.updateBodyText(DESCRIPTIONSALT[4]);
                    this.imageEventText.setDialogOption(OPTIONSALT[1]);

                }
                return;
            case CLERICFRESHINTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONSOG[1]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONSOG[2]);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONSOG[3], false, false, false, true);
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        break;
                }
                return;
            case CLERICDEADINTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[2]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pride(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[3]);
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        break;
                }
                return;
            case CLERICALIVEINTRO:
                switch (buttonPressed) {
                    case 0:

                        this.screen = CurScreen.POSTFIGHT;
                        MonsterGroup monsters = new MonsterGroup(new Centurion(-400F, 0F));
                        monsters.add(new Centurion(0F, 0F));
                        AbstractDungeon.getCurrRoom().monsters = monsters;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().rewardAllowed = false;
                        this.enterCombatFromImage();
                        break;
                }
                return;
            case POSTFIGHT:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[6]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold * 3));
                        AbstractDungeon.player.gainGold(this.gold * 2);
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        break;
                    case 1:
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().rewardAllowed = false;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[7]);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 2, OPTIONSOG[3], false, false, false, true);
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        break;
                }
                return;
            case END:
                this.openMap();
        }

    }

    public void reopen() {
        if (this.screen == CurScreen.POSTFIGHT) {
            AbstractDungeon.getCurrRoom().rewards.clear();
            AbstractDungeon.getCurrRoom().rewardAllowed = false;
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = (float) Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            this.imageEventText.clearAllDialogs();
            this.imageEventText.updateBodyText(DESCRIPTIONSALT[5]);
            this.imageEventText.setDialogOption(OPTIONSOG[0] + (this.gold * 3) + OPTIONSOG[4]);
            this.imageEventText.setDialogOption(OPTIONSALT[2]);
            this.enterImageFromCombat();
        }

    }

    private enum CurScreen {
        INTRO,
        CLERICFRESHINTRO,
        CLERICALIVEINTRO,
        CLERICDEADINTRO,
        POSTFIGHT,
        END;

        CurScreen() {
        }
    }
}