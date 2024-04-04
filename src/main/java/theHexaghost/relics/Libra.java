package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;

import java.util.ArrayList;
import java.util.Collections;
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
    private int count = 0;

    private boolean calledTransform = true;

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        super.updateDescription(c);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.calledTransform = false;
        int count_attack = 0;
        int count_skill = 0;
        int count_strike = 0;
        int count_defend = 0;
        ArrayList<AbstractCard> starters = new ArrayList<>();
        boolean more_attacks_than_skills = false;
        boolean equal = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.ATTACK) count_attack++;
            if (c.type == AbstractCard.CardType.SKILL) count_skill++;
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)){
                count_strike++;
                starters.add(c);
            }
            if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
                count_defend++;
                starters.add(c);
            }
        }
        if (count_attack > count_skill) {
            more_attacks_than_skills = true;
        } else if (count_attack == count_skill) {
            equal = true;
        }

        if (equal) {
            if(starters.size() > 0) {
                Collections.shuffle(starters, AbstractDungeon.cardRng.random);
                for (int i = 0; i < starters.size() / 2; i++) {
                    AbstractCard card_to_remove = starters.get(i);
                    AbstractDungeon.player.masterDeck.removeCard(card_to_remove);
                }

                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                for (int j = 0; j < starters.size() / 2; ++j) {
                    AbstractCard card = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                    UnlockTracker.markCardAsSeen(card.cardID);
                    card.isSeen = true;
                    Iterator var4 = AbstractDungeon.player.relics.iterator();

                    while (var4.hasNext()) {
                        AbstractRelic r = (AbstractRelic) var4.next();
                        r.onPreviewObtainCard(card);
                    }
                    group.addToBottom(card);
                }
                AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, " ");
            }
            return;

        } else if (more_attacks_than_skills) {
            Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

            while (true) {
                AbstractCard e;
                do {
                    if (!i.hasNext()) {
                        if (this.count > 0) {
                            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                            for (int j = 0; j < this.count; ++j) {
                                AbstractCard card = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                                UnlockTracker.markCardAsSeen(card.cardID);
                                card.isSeen = true;
                                Iterator var4 = AbstractDungeon.player.relics.iterator();

                                while (var4.hasNext()) {
                                    AbstractRelic r = (AbstractRelic) var4.next();
                                    r.onPreviewObtainCard(card);
                                }
                                group.addToBottom(card);
                            }
                            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, " ");
                        }
                        return;
                    }
                    e = (AbstractCard) i.next();
                } while (!e.hasTag(AbstractCard.CardTags.STARTER_STRIKE));

                i.remove();
                ++this.count;
            }
        } else {
            Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

            while (true) {
                AbstractCard e;
                do {
                    if (!i.hasNext()) {
                        if (this.count > 0) {
                            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                            for (int j = 0; j < this.count; ++j) {
                                AbstractCard card = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                                UnlockTracker.markCardAsSeen(card.cardID);
                                card.isSeen = true;
                                Iterator var4 = AbstractDungeon.player.relics.iterator();

                                while (var4.hasNext()) {
                                    AbstractRelic r = (AbstractRelic) var4.next();
                                    r.onPreviewObtainCard(card);
                                }
                                group.addToBottom(card);
                            }
                            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, " ");
                        }
                        return;
                    }
                    e = (AbstractCard) i.next();
                } while (!e.hasTag(AbstractCard.CardTags.STARTER_DEFEND));

                i.remove();
                ++this.count;
            }
        }
    }

    public void update() {
        super.update();
        if (!this.calledTransform && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID) {
            this.calledTransform = true;
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }
    }
}
