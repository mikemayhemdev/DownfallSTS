package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MummifiedHand;

import java.util.ArrayList;
import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class NerfedMummifiedHand extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("NerfedMummifiedHand");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("NerfedMummifiedHand.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("NerfedMummifiedHand.png"));


    public NerfedMummifiedHand() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && (card.cardID != Ceremony.ID)) {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            ArrayList<AbstractCard> groupCopy = new ArrayList();
            Iterator var4 = AbstractDungeon.player.hand.group.iterator();

            while(true) {
                while(var4.hasNext()) {
                    AbstractCard c = (AbstractCard)var4.next();
                    if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                        groupCopy.add(c);
                    }
                }

                var4 = AbstractDungeon.actionManager.cardQueue.iterator();

                while(var4.hasNext()) {
                    CardQueueItem i = (CardQueueItem)var4.next();
                    if (i.card != null) {
                        groupCopy.remove(i.card);
                    }
                }

                AbstractCard c = null;
                if (groupCopy.isEmpty()) {
                } else {
                    Iterator var9 = groupCopy.iterator();

                    while(var9.hasNext()) {
                        AbstractCard cc = (AbstractCard)var9.next();
                    }

                    c = (AbstractCard)groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
                }

                if (c != null) {
                    c.setCostForTurn(0);
                }
                break;
            }
        }

    }

    public AbstractRelic makeCopy() {
        return new NerfedMummifiedHand();
    }
}