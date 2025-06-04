package downfall.events;

import automaton.relics.BottledCode;
import awakenedOne.relics.MoonTalisman;
import champ.relics.SignatureFinisher;
import collector.CollectorChar;
import collector.relics.BottledCollectible;
import collector.relics.ForbiddenFruit;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.events.StasisEgg;
import guardian.relics.BottledAnomaly;
import guardian.relics.BottledStasis;
import guardian.relics.PickAxe;
import sneckomod.relics.D8;
import sneckomod.relics.SneckoBoss;
import sneckomod.relics.SneckoCommon;
import theHexaghost.relics.Libra;

import java.util.ArrayList;
import java.util.Collections;

public class WeMeetAgain_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:WeMeetAgain";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
    }

    private CUR_SCREEN screen;
    private ArrayList<AbstractRelic> relicsOffered = new ArrayList<>();
    private int relicOffersAvailable;
    private int relicCOffers = 0;
    private int relicUOffers = 0;
    private int goldAmt;

    public WeMeetAgain_Evil() {
        super(NAME, DIALOG_1, "images/events/weMeetAgain.jpg");
        this.screen = CUR_SCREEN.INTRO;
        this.noCardsInRewards = true;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldAmt = 75;
        } else {
            this.goldAmt = 150;
        }

        ArrayList<AbstractRelic> playerCommonRelics = new ArrayList<>();
        ArrayList<AbstractRelic> playerUncommonRelics = new ArrayList<>();

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if
                //Commons
            ((r.relicId != Strawberry.ID) &&
                    (r.relicId != MawBank.ID) &&
                    (r.relicId != PickAxe.ID) &&
                    (r.relicId != Matryoshka.ID) &&
                    (r.relicId != WarPaint.ID) &&
                    (r.relicId != Whetstone.ID) &&
                    (r.relicId != SneckoCommon.ID) &&
                    (r.relicId != PotionBelt.ID) &&
                    //Uncommons
                    (r.relicId != BottledFlame.ID) &&
                    (r.relicId != BottledLightning.ID) &&
                    (r.relicId != BottledTornado.ID) &&
                    (r.relicId != BottledStasis.ID) &&
                    (r.relicId != Pear.ID) &&
                    //Rares
                    (r.relicId != BottledCollectible.ID) &&
                    (r.relicId != LizardTail.ID) &&
                    (r.relicId != Mango.ID) &&
                    (r.relicId != SignatureFinisher.ID) &&
                    (r.relicId != WingBoots.ID) &&
                    (r.relicId != MoonTalisman.ID) &&

                    (r.relicId != BottledAnomaly.ID) &&
                    (r.relicId != BottledCode.ID) &&
                    (r.relicId != StasisEgg.ID) &&
                    (r.relicId != D8.ID) &&


                    //Boss
                    (r.relicId != Astrolabe.ID) &&
                    (r.relicId != ForbiddenFruit.ID) &&
                    (r.relicId != Libra.ID) &&
                    (r.relicId != SneckoBoss.ID) &&
                    (r.relicId != PandorasBox.ID)) {

                if (r.tier == AbstractRelic.RelicTier.COMMON) {
                    playerCommonRelics.add(r);
                }
                if (r.tier == AbstractRelic.RelicTier.UNCOMMON) {
                    playerUncommonRelics.add(r);
                }
            }
        }

        Collections.shuffle(playerCommonRelics);
        Collections.shuffle(playerUncommonRelics);

        for (int i = 0; i < 3; i++) {
            if (playerCommonRelics.size() > 0) {
                AbstractRelic q = playerCommonRelics.get(0);
                this.relicsOffered.add(q);
                playerCommonRelics.remove(0);
                this.relicOffersAvailable++;
//                this.relicCOffers++;
            } else if (playerUncommonRelics.size() > 0) {
                AbstractRelic p = playerUncommonRelics.get(0);
                this.relicsOffered.add(p);
                playerUncommonRelics.remove(0);
                this.relicOffersAvailable++;
//                this.relicUOffers++;
            }
        }

        if (this.relicOffersAvailable >= 1) {
            if (AbstractDungeon.ascensionLevel >= 15) {
                this.imageEventText.setDialogOption(OPTIONS[0] + this.relicsOffered.get(0).name + OPTIONS[1] + OPTIONS[8]);
            } else {
                this.imageEventText.setDialogOption(OPTIONS[0] + this.relicsOffered.get(0).name + OPTIONS[1] + OPTIONS[2]);
            }
        } else {
            this.imageEventText.setDialogOption(OPTIONS[7], true);
        }

        if (this.relicOffersAvailable >= 2) {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.relicsOffered.get(1).name + OPTIONS[1] + OPTIONS[3] + "#g" + this.goldAmt + OPTIONS[4]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[7], true);
        }

        if (this.relicOffersAvailable >= 3) {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.relicsOffered.get(2).name + OPTIONS[1] + OPTIONS[5]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[7], true);
        }

        //TODO: This will be more annoying than other Collector overrides
        this.imageEventText.setDialogOption(OPTIONS[6]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.screen = CUR_SCREEN.COMPLETE;
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);

                        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                        if (AbstractDungeon.ascensionLevel < 15) {
                            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
                        }
                        AbstractDungeon.player.loseRelic(this.relicsOffered.get(0).relicId);
                        logMetric(ID, "Traded Relic For Potions", null, null, null, null,
                                null, null, Collections.singletonList(this.relicsOffered.get(0).relicId),
                                0, 0, 0, 0, 0, 0);

                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);

                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldAmt));
                        AbstractDungeon.player.gainGold(this.goldAmt);
                        AbstractDungeon.player.loseRelic(this.relicsOffered.get(1).relicId);
                        logMetric(ID, "Traded Relic For Gold", null, null, null, null,
                                null, null, Collections.singletonList(this.relicsOffered.get(1).relicId),
                                0, 0, 0, 0, goldAmt, 0);

                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractCard rewardCard = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE).makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewardCard, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.2F));
                        AbstractDungeon.player.loseRelic(this.relicsOffered.get(2).relicId);
                        logMetric(ID, "Traded Relic For Card", null, null, null, null,
                                null, null, Collections.singletonList(this.relicsOffered.get(2).relicId),
                                0, 0, 0, 0, 0, 0);

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[6]);
                        break;
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        logMetricIgnored(ID);
                }

                this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                this.imageEventText.clearRemainingOptions();
                break;
            case COMPLETE:
                this.openMap();
        }

    }

    private enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
