package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_Duplicator extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("Duplicator");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String cardName = "";

    public CBR_Duplicator() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/shrine4.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        if (AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex] != null) {
            list.add(AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex]);
            AbstractCharBoss.boss.chosenArchetype.logger.info("Duplicator duplicated " + AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex].name);
        }

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Duplicator();
    }
}
