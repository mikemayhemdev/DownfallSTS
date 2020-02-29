package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_AncientWriting extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("AncientWriting");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private int count;

    public CBR_AncientWriting() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/ancientwriting.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        for (AbstractBossCard c : list){
            if (!c.upgraded && (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)));
            c.upgrade();
            count++;
        }


        this.description = getUpdatedDescription();
        refreshDescription();

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.count + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_AncientWriting();
    }
}
