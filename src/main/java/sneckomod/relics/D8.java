package sneckomod.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import sneckomod.SneckoMod;
import sneckomod.patches.BottledD8Patch;
import downfall.util.TextureLoader;

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
            if (c.hasTag(SneckoMod.RNG)) {
                tmp.addToTop(c);
            }
        }
        AbstractDungeon.gridSelectScreen.open(tmp,
                1, DESCRIPTIONS[1] + name + ".",
                false, false, false, false);
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
            setDescriptionAfterLoading();
        }
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {

        return BottledD8Patch.inD8::get;
    }

    private void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
