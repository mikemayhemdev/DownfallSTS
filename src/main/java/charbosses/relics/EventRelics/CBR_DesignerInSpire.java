package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_DesignerInSpire extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("DesignerInSpire");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String upgradedName;
    private String removedName;

    public CBR_DesignerInSpire() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/designerinspire.png")));
        this.largeImg = null;
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        removedName = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Designer in Spire");
        upgradedName = AbstractCharBoss.boss.chosenArchetype.upgradeRandomCard("Designer in Spire");

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DesignerInSpire();
    }
}
