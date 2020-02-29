package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnParasite;
import charbosses.cards.curses.EnShame;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_OddMushroom;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_PleadingVagrant extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("PleadingVagrant");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;

    public CBR_PleadingVagrant() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/pleadingvagrant.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        addedName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex, AbstractCharBoss.boss, list);
        AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Pleading Vagrant", new EnShame());
        this.description = DESCRIPTIONS[0] + this.addedName + ".";


        refreshDescription();
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PleadingVagrant();
    }
}
