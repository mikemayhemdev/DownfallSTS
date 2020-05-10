package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_Transmogrifier extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Transmogrifier");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String removedName;
    private String addedName;


    public CBR_Transmogrifier() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/shrine1.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        removedName = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Transmogrifier");
        addedName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Transmogrifier");

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Transmogrifier();
    }
}
