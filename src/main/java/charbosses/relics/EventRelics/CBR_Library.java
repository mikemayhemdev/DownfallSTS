package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnDoubt;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_Library extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("Library");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;

    public CBR_Library() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/library.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        addedName = AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Library");

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.addedName + ".";
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Library();
    }
}
