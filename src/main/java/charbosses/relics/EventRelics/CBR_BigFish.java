package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnRegret;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_BigFish extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("BigFish");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    public String relicName = "";
    private int descInt = 0;

    public CBR_BigFish() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/bigfish.png")));
    }


    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        box(list);
    }


    public void box(ArrayList<AbstractBossCard> list) {
        this.relicName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(AbstractDungeon.actNum, AbstractCharBoss.boss, "Big Fish", list);
        final AbstractCard bellCurse = new EnRegret();
        AbstractCharBoss.boss.masterDeck.addToTop(bellCurse.makeCopy());
        AbstractBossDeckArchetype.logger.info("Big Fish added 1 " + bellCurse.name + ".");
        this.descInt = 1;
        this.updateDescription(AbstractCharBoss.boss.chosenClass);
    }

    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
         return this.DESCRIPTIONS[1] + this.relicName + this.DESCRIPTIONS[2];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BigFish();
    }
}
