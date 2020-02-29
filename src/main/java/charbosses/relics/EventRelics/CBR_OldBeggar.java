package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_OldBeggar extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("OldBeggar");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String cardName;

    public CBR_OldBeggar() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/oldbeggar.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        cardName = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Old Beggar");

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.cardName + this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_OldBeggar();
    }
}
