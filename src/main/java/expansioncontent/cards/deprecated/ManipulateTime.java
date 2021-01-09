package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import slimebound.actions.TriggerStartOfTurnEffectsAction;

@CardIgnore
public class ManipulateTime extends AbstractExpansionCard {
    public final static String ID = makeID("ManipulateTime");

    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 4;

    public ManipulateTime() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_TIMEEATER);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
        AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.BorderFlashEffect(com.badlogic.gdx.graphics.Color.GOLD, true));
        AbstractDungeon.topLevelEffectsQueue.add(new com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect());
        atb(new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, this.block));

        atb(new TriggerStartOfTurnEffectsAction(p));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}

