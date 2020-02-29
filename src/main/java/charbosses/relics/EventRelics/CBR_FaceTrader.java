package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.*;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_FaceTrader extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("FaceTrader");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String cardName = "";

    public CBR_FaceTrader() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/facetrader.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        int choice = AbstractDungeon.cardRng.random(0,4);
        switch (choice){
            case 0:
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_MaskCleric(), AbstractCharBoss.boss,"Face Trader", list);
            case 1:
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_MaskNloth(), AbstractCharBoss.boss,"Face Trader", list);
            case 2:
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_MaskSerpent(), AbstractCharBoss.boss,"Face Trader", list);
            case 3:
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_MaskCultist(), AbstractCharBoss.boss,"Face Trader", list);
            case 4:
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_MaskGremlin(), AbstractCharBoss.boss,"Face Trader", list);
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FaceTrader();
    }
}
