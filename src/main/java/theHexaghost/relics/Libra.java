package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.util.TextureLoader;
import hermit.relics.Memento;
import theHexaghost.HexaMod;
import theHexaghost.cards.Defend;
import theHexaghost.cards.Strike;

import java.util.ArrayList;
import java.util.Iterator;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;


public class Libra extends CustomRelic {

    public static final String ID = HexaMod.makeID("Libra");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Libra.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Libra.png"));

    public Libra() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public int number_of_cards_to_transform;
    private boolean no_cards_to_select = false;
    private boolean calledTransform = true;
    private boolean hasbasics = false;

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        super.updateDescription(c);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.calledTransform = false;

        CardGroup starters = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                starters.addToBottom(c);
            }
        }

        this.number_of_cards_to_transform = starters.size();

        if (starters.group.isEmpty()) {
            this.no_cards_to_select = true;
        } else {
            giveCards(starters.group);
            //if (AbstractDungeon.isScreenUp) {
             //   AbstractDungeon.dynamicBanner.hide();
             //   AbstractDungeon.previousScreen = AbstractDungeon.screen;
          //  }
          //  AbstractDungeon.gridSelectScreen.open(starters, starters.group.size(), true, this.DESCRIPTIONS[2]);
        }

    }

//    public void update() {
//        super.update(); // Todo, verify the trigger condition is fine for controller input too
//        if (!this.calledTransform && (no_cards_to_select || (AbstractDungeon.gridSelectScreen.confirmButton.hb.hovered && InputHelper.justClickedLeft) || CInputActionSet.proceed.isJustPressed())) {
//            AbstractDungeon.gridSelectScreen.confirmButton.hb.hovered = false;
//            this.calledTransform = true;
//            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
//            this.giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
//        }
//    }

    public void giveCards(ArrayList<AbstractCard> group) {

        Iterator<AbstractCard> i = group.iterator();

        CardGroup new_cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        while (i.hasNext()) {
            AbstractCard card = i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractCard c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
            if (card.type == AbstractCard.CardType.SKILL) {
                while(c.type != AbstractCard.CardType.ATTACK){
                    c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                }
                UnlockTracker.markCardAsSeen(c.cardID);
                c.isSeen = true;

                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(c);
                }
            }else{
                while(c.type != AbstractCard.CardType.SKILL){
                    c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                }
                UnlockTracker.markCardAsSeen(c.cardID);
                c.isSeen = true;

                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(c);
                }
            }
            new_cards.addToBottom(c);
            AbstractDungeon.player.masterDeck.removeCard(card);
        }

        AbstractDungeon.gridSelectScreen.openConfirmationGrid(new_cards, this.DESCRIPTIONS[1]);
        AbstractDungeon.gridSelectScreen.selectedCards.clear();

    }


    public boolean canSpawn() {
        hasbasics = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                hasbasics = true;
            }
            if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                hasbasics = true;
            }
        }
        return hasbasics;
    }
}