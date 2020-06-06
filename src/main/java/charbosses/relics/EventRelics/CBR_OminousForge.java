package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnPain;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_WarpedTongs;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_OminousForge extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("OminousForge");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String cardName = "";

    public CBR_OminousForge() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/ominousforge.png")));
        this.largeImg = null;
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {

     AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_WarpedTongs(), AbstractCharBoss.boss,"Ominous Forge", list);
     AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Ominous Forge", new EnPain());

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_OminousForge();
    }
}
