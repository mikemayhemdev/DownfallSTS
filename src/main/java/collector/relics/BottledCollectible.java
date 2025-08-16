package collector.relics;

import automaton.AutomatonMod;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import collector.CollectorCollection;
import collector.CollectorMod;
import collector.actions.DrawCardFromCollectionAction;
import collector.patches.CollectorBottleField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.util.TextureLoader;

import java.util.function.Predicate;

import static collector.util.Wiz.atb;

public class BottledCollectible extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {
    public static final String ID = CollectorMod.makeID(BottledCollectible.class.getSimpleName());
    private static final String IMG_PATH = BottledCollectible.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = BottledCollectible.class.getSimpleName() + ".png";

    public AbstractCard card;
    private boolean cardSelected = true;

    private boolean cardRemoved = false;

    private int idxToLoad = -1;

    public BottledCollectible() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onTrigger() {
        this.flash();
        this.counter = -1;
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return CollectorBottleField.inCollectionBottle::get;
    }

    @Override
    public Integer onSave() {
        if (card != null) {
            return CollectorCollection.collection.group.indexOf(card);
        } else {
            return -1;
        }
    }

    @Override
    public void onLoad(Integer cardIndex) {
        idxToLoad = cardIndex;
    }

    public void onLoadCollection() {
        if (idxToLoad == -1) {
            return;
        }
        if (idxToLoad >= 0 && idxToLoad < AbstractDungeon.player.masterDeck.group.size()) {
            card = CollectorCollection.collection.group.get(idxToLoad);
            if (card != null) {
                CollectorBottleField.inCollectionBottle.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup group = CardGroup.getGroupWithoutBottledCards(CollectorCollection.collection);

        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3] + name + DESCRIPTIONS[2], false, false, false, false);

    }

    @Override
    public boolean canSpawn() {
        return !CollectorCollection.collection.isEmpty();
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                CollectorBottleField.inCollectionBottle.set(cardInDeck, false);
            }
        }
    }


    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CollectorBottleField.inCollectionBottle.set(card, true);
            card.upgrade();
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.INCOMPLETE) {
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));

            setDescriptionAfterLoading();
        }
    }

    public void setDescriptionAfterLoading() {
        //todo: better card removal detection code!!!!
        if (cardSelected) {
            boolean cardExists = false;

            CardGroup tmp = CollectorCollection.collection;
            for (AbstractCard c : tmp.group) {
                if (c.uuid == card.uuid) {
                    cardExists = true;
                    break;
                }
            }


            if (!cardExists) {
                tips.clear();
                this.description = this.DESCRIPTIONS[4];
                initializeTips();
                this.grayscale = true;
            } else {
                this.description = DESCRIPTIONS[1] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[2];
                this.tips.clear();
                this.tips.add(new PowerTip(this.name, this.description));
                this.initializeTips();
            }

        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

