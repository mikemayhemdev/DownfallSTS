package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_DivineFountain extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("DivineFountain");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private int numCurses;

    public CBR_DivineFountain() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/divinefountain.png")));
        this.largeImg = null;
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        ArrayList<AbstractBossCard> cardsToRemove = new ArrayList<>();
        for (AbstractBossCard c : AbstractCharBoss.boss.chosenArchetype.cards){
            if (c.type == AbstractCard.CardType.CURSE){
                cardsToRemove.add(c);
                this.numCurses++;
            }
        }
        for (AbstractBossCard c : cardsToRemove){
            AbstractCharBoss.boss.chosenArchetype.cards.remove(c);
        }

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.numCurses + this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DivineFountain();
    }
}
