package sneckomod.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.TyphoonFang;
import sneckomod.patches.BottledD8Patch;
import downfall.util.TextureLoader;
import sneckomod.powers.CheatPower;

import java.util.function.Predicate;

public class D8 extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {

    public static final String ID = SneckoMod.makeID("D8");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("D8.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("D8.png"));
    public AbstractCard card = null;
    private boolean cardSelected = true;

    public D8() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    //mystical octahedron
    //todo: set variable for block gain
    //oh god this will be annoying, do later

    @Override
    public Integer onSave() {
        return AbstractDungeon.player.masterDeck.group.indexOf(card);
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottledD8Patch.inD8.set(card, true);
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
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            if (c.hasTag(SneckoMod.OVERFLOW)) {
                tmp.addToTop(c);
            }
        }
        AbstractDungeon.gridSelectScreen.open(tmp,
                1, DESCRIPTIONS[1] + name + ".",
                false, false, false, false);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.card != null && card.uuid.equals(this.card.uuid)) {
            if (!(card instanceof TyphoonFang && card.purgeOnUse)) {
                addToBot(new GainBlockAction(AbstractDungeon.player, 4));
                this.flash(); //block tracking
            }
        }
        if (this.card != null && card.uuid.equals(this.card.uuid) && AbstractDungeon.player.hand.size() <= 5 && !(AbstractDungeon.player.hasPower(CheatPower.POWER_ID))) {
            if (!(card instanceof TyphoonFang && card.purgeOnUse)) {
                this.flash(); //overflow tracking
            }
        }
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottledD8Patch.inD8.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledD8Patch.inD8.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));

            setDescriptionAfterLoading();
        }
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledD8Patch.inD8::get;
    }

    public void setDescriptionAfterLoading() {
        boolean cardExists = false;

        if (card != null) {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.uuid == card.uuid) {
                    cardExists = true;
                    break;
                }
            }
        }

        if (!cardExists) {
            tips.clear();
            this.description = this.DESCRIPTIONS[4];
            this.grayscale = true;
            initializeTips();
        }

        if (cardExists) {
            this.description = FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[2];
            tips.clear();
            tips.add(new PowerTip(name, description));
            initializeTips();
            this.grayscale = false;
        }
    }

    public boolean canSpawn() {

        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            if (c.hasTag(SneckoMod.OVERFLOW)) {
                return true;
            }
        }

        return false;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
