package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnRegret;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_GoldenShrine extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("GoldenShrine");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String cardName = "";

    public CBR_GoldenShrine() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/goldenshrine.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex,this.owner,"Golden Shrine", list);
        AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Golden Shrine",new EnRegret());

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_GoldenShrine();
    }
}
