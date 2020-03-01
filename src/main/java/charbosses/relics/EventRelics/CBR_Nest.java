package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnRitualDagger;
import charbosses.cards.curses.EnWrithe;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_Nest extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("Nest");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public CBR_Nest() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/nest.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Mausoleum", new EnRitualDagger());

    }

    @Override
    public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Nest();
    }
}
