//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package automaton.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;
import guardian.cards.*;

import java.util.ArrayList;

public class CrystalForgeAutomaton extends AbstractImageEvent {
    public static final String ID = "bronze:CrystalForge";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String INTRO;
    private static final String TRANSMUTE;
    private static final String REFORGE;
    private static final String COMBINE;
    private static final String LEAVE;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("bronze:CrystalForge");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO = DESCRIPTIONS[0];
        REFORGE = DESCRIPTIONS[2];
        COMBINE = DESCRIPTIONS[1];
        TRANSMUTE = DESCRIPTIONS[3];
        LEAVE = DESCRIPTIONS[4];
    }

    private int screenNum = 0;
    private boolean pickCardForGemRemoval = false;
    private boolean pickCardForSalvageGems = false;
    private boolean pickCardForTransmute = false;
    private AbstractGuardianCard cardChosen = null;
    private AbstractGuardianCard gemChosen = null;

    public CrystalForgeAutomaton() {
        super(NAME, INTRO, GuardianMod.getResourcePath("/events/grimForge.jpg"));

        //TODO - Has two cards with Encode
        if (true) {
            this.imageEventText.setDialogOption(OPTIONS[5], true);

        } else {
            this.imageEventText.setDialogOption(OPTIONS[0]);

        }

        //TODO - Has a Rare Card
        if (true) {
            this.imageEventText.setDialogOption(OPTIONS[6], true);

        } else {
            this.imageEventText.setDialogOption(OPTIONS[2]);

        }

        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[4]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_FORGE");
        }

    }

    public void update() {
        super.update();
        if (this.pickCardForGemRemoval && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            ((AbstractGuardianCard) c).sockets.clear();
            ((AbstractGuardianCard) c).updateDescription();
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCardForGemRemoval = false;
            updateEnhance();
        } else if (this.pickCardForSalvageGems && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractGuardianCard cg = (AbstractGuardianCard) c;
            ArrayList<AbstractCard> rewardGemCards = new ArrayList<>();

            for (int i = 0; i < cg.sockets.size(); i++) {
                switch (cg.sockets.get(i).toString()) {
                    case "RED":
                        rewardGemCards.add(new Gem_Red());
                        break;
                    case "GREEN":
                        rewardGemCards.add(new Gem_Green());
                        break;
                    case "ORANGE":
                        rewardGemCards.add(new Gem_Orange());
                        break;
                    case "CYAN":
                        rewardGemCards.add(new Gem_Cyan());
                        break;
                    case "WHITE":
                        rewardGemCards.add(new Gem_White());
                        break;
                    case "BLUE":
                        rewardGemCards.add(new Gem_Blue());
                        break;
                    case "CRIMSON":
                        rewardGemCards.add(new Gem_Crimson());
                        break;
                    case "FRAGMENTED":
                        rewardGemCards.add(new Gem_Fragmented());
                        break;
                    case "PURPLE":
                        rewardGemCards.add(new Gem_Purple());
                        break;
                    case "SYNTHETIC":
                        rewardGemCards.add(new Gem_Synthetic());
                        break;
                    case "YELLOW":
                        rewardGemCards.add(new Gem_Yellow());
                        break;
                }
            }

            ((AbstractGuardianCard) c).sockets.clear();
            ((AbstractGuardianCard) c).updateDescription();

            for (int i = 0; i < rewardGemCards.size(); i++) {
                AbstractCard c2 = rewardGemCards.get(i);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c2, (float) (Settings.WIDTH * (0.2 * (i + 1))), (float) (Settings.HEIGHT / 2)));

            }


            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH * 0.2), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(c);

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCardForSalvageGems = false;
            updateEnhance();
        } else if (this.pickCardForTransmute && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 1);

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(gems.get(0), (float) (Settings.WIDTH * .3), (float) (Settings.HEIGHT / 2)));

            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) (Settings.WIDTH * .7), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.gridSelectScreen.selectedCards.get(0));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCardForTransmute = false;
            updateEnhance(1);
        } else if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && GuardianMod.gridScreenForGems) {

            this.gemChosen = (AbstractGuardianCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            GuardianMod.gridScreenForGems = false;
            GuardianMod.gridScreenForSockets = true;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.gridSelectScreen.open(GuardianMod.getSocketableCards(), 1, DESCRIPTIONS[9], false, false, true, false);


        } else if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && GuardianMod.gridScreenForSockets) {

            this.cardChosen = (AbstractGuardianCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            cardChosen.addGemToSocket(gemChosen);
            AbstractDungeon.effectList.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(cardChosen.makeStatEquivalentCopy()));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            GuardianMod.gridScreenForSockets = false;
            updateEnhance();
        }


    }

    private void updateEnhance() {
        this.updateEnhance(0);
    }

    private void updateEnhance(int inflateGemCount) {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            c.update();
        }

        if (GuardianMod.getSocketableCards().size() == 0 || GuardianMod.getGemCards().size() + inflateGemCount == 0) {
            this.imageEventText.updateDialogOption(3, OPTIONS[7], true);

        } else {
            this.imageEventText.updateDialogOption(3, OPTIONS[6], false);

        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        //this.pickCardForSalvageGems = true;
                        this.imageEventText.updateBodyText(COMBINE);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3], true);
                        //AbstractDungeon.gridSelectScreen.open(GuardianMod.getCardsWithFilledSockets(), 1, false, DESCRIPTIONS[5]);

                        break;
                    case 1:
                     //   this.pickCardForGemRemoval = true;
                      //  AbstractDungeon.gridSelectScreen.open(GuardianMod.getCardsWithFilledSockets(), 1, DESCRIPTIONS[6], false, false, false, false);
                        this.imageEventText.updateBodyText(REFORGE);
                        this.imageEventText.updateDialogOption(1, OPTIONS[3], true);

                        break;
                    case 2:
                       // this.pickCardForTransmute = true;
                        this.imageEventText.updateBodyText(TRANSMUTE);
                       // AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, DESCRIPTIONS[7], false, false, false, true);

                        this.imageEventText.updateDialogOption(2, OPTIONS[3], true);

                        break;
                    case 4:
                        this.screenNum = 2;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(LEAVE);
                        this.imageEventText.setDialogOption(OPTIONS[4]);

                        break;
                }


                break;
            default:
                this.openMap();
        }

    }
}
