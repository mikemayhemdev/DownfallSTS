package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.util.TextureLoader;
import guardian.cards.ExploitGems;
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
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || card.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractCard c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                if (card.type == AbstractCard.CardType.SKILL) {
                    while(c.type != AbstractCard.CardType.ATTACK){
                        c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                    }
                    UnlockTracker.markCardAsSeen(c.cardID);
                    c.isSeen = true;
                }else{
                    while(c.type != AbstractCard.CardType.SKILL){
                        c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                    }
                    UnlockTracker.markCardAsSeen(c.cardID);
                    c.isSeen = true;
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
        }
    }

    public boolean canSpawn() {
    //requires at least 1 strike or defend to spawn
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