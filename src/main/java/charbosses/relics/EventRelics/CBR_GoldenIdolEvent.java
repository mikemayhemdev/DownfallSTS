package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.curses.EnInjury;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_GoldenIdol;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_GoldenIdolEvent extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("GoldenIdolEvent");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;

    public CBR_GoldenIdolEvent() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/goldenidol.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Golden Idol Event", new EnInjury());
        AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_GoldenIdol(),AbstractCharBoss.boss,"Golden Idol Event", list);
        addedName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex, AbstractCharBoss.boss, list);

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.addedName + ".";
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_GoldenIdolEvent();
    }
}
