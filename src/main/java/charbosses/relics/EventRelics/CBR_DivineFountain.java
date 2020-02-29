package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_DivineFountain extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("DivineFountain");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public CBR_DivineFountain() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/divinefountain.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        ArrayList<AbstractBossCard> cardsToRemove = new ArrayList<>();
        for (AbstractBossCard c : AbstractCharBoss.boss.chosenArchetype.cards){
            if (c.type == AbstractCard.CardType.CURSE){
                cardsToRemove.add(c);
            }
        }
        for (AbstractBossCard c : cardsToRemove){
            AbstractCharBoss.boss.chosenArchetype.cards.remove(c);
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DivineFountain();
    }
}
