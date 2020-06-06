package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_LivingWall extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("LivingWall");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String cardName;
    private String cardName2;

    public CBR_LivingWall() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/livingwall.png")));
        this.largeImg = null;
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        int choice = AbstractDungeon.cardRng.random(0,2);
        switch (choice){
            case 0:
                cardName = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Living Wall");
                this.description = DESCRIPTIONS[0] + this.cardName + ".";
            case 1:
                cardName = AbstractCharBoss.boss.chosenArchetype.upgradeRandomCard("Living Wall");
                this.description = DESCRIPTIONS[1] + this.cardName + ".";
            case 2:
                cardName = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Living Wall");
                cardName2 = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Living Wall");
                this.description = DESCRIPTIONS[2] + this.cardName + DESCRIPTIONS[3] + this.cardName2 + ".";
        }

        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_LivingWall();
    }
}
