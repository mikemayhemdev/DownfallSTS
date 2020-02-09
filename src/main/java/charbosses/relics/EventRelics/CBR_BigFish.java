package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnCurseOfTheBell;
import charbosses.cards.curses.EnRegret;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_SpiritPoop;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;
import java.util.Collections;


public class CBR_BigFish extends AbstractCharbossRelic
{
    public static String ID = EvilWithinMod.makeID("BigFish");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    private int descInt = 0;
    public String relicName = "";

    public CBR_BigFish() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/bigfish.png")));
    }


    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list) {
        if (AbstractDungeon.cardRng.randomBoolean()) {
            donut(list);
        } else {
            box(list);
        }
    }

    public void donut(ArrayList<AbstractBossCard> list) {
        AbstractBossDeckArchetype.logger.info("Big Fish event added Max HP.");
        AbstractCharBoss.boss.increaseMaxHp(5, false);
        this.descInt = 0;
        this.updateDescription(AbstractCharBoss.boss.chosenClass);
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
        switch (descInt) {
            case 0:
                return this.DESCRIPTIONS[0];
            case 1:
                return this.DESCRIPTIONS[1] + this.relicName + this.DESCRIPTIONS[2];
            default:
                return this.DESCRIPTIONS[0];
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BigFish();
    }
}
