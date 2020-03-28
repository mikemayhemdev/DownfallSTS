package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.curses.EnShame;
import charbosses.cards.curses.EnWrithe;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_Mausoleum extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("Mausoleum");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    public String addedName = "";

    public CBR_Mausoleum() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/mausoleum.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        this.addedName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(AbstractDungeon.actNum, AbstractCharBoss.boss, "Mausoleum", list);

        AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Mausoleum", new EnWrithe());
        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Mausoleum();
    }
}
