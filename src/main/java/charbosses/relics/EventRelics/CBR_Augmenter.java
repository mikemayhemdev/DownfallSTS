package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnJAX;
import charbosses.cards.curses.EnDecay;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_GoldenIdol;
import charbosses.relics.CBR_Mutagenic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_Augmenter extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("Augmenter");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;
    private String removedName;
    private String addedName2;
    private int descInt = 0;
    private String removedName2;

    public CBR_Augmenter(int choiceIndex) {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/augmenter.png")));
        this.descInt = choiceIndex;

    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        int choice = AbstractDungeon.cardRng.random(0,2);
        switch (choice){
            case 0:
                removedName = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Augmenter");
                addedName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Augmenter");
                removedName2 = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Augmenter");
                addedName2 = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Augmenter");
                this.description = DESCRIPTIONS[0] + this.removedName + " & " + this.removedName2 + DESCRIPTIONS[1] + this.addedName + " & " + this.removedName + ".";
            case 1:
                AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Augmenter", new EnJAX());
                this.description = DESCRIPTIONS[3] ;
            case 2:
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_Mutagenic(),AbstractCharBoss.boss,"Augmenter", list);
                this.description = DESCRIPTIONS[4] ;

        }

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[descInt];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Augmenter(0);
    }
}
