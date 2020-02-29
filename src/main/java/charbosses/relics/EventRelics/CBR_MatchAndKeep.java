package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnRegret;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_MatchAndKeep extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("MatchAndKeep");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String cardName = "";

    public CBR_MatchAndKeep() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/matchandkeep.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        int choice = AbstractDungeon.cardRng.random(0,2);
        switch (choice){
            case 0:
                cardName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Match and Keep");
                this.description = DESCRIPTIONS[0] + this.cardName + ".";
            case 1:
                cardName = AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Match and Keep");
                this.description = DESCRIPTIONS[0] + this.cardName + ".";
            case 2:
                cardName = AbstractCharBoss.boss.chosenArchetype.addRandomCurse(AbstractCharBoss.boss, "Match and Keep");
                this.description = DESCRIPTIONS[0] + this.cardName + ".";
          }

        refreshDescription();

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MatchAndKeep();
    }
}
