package downfall.powers.neowpowers;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import theHexaghost.util.TextureLoader;

public class BagOfKnives extends AbstractPower {
    public static final String POWER_ID = downfallMod.makeID("NeowBagOfKnives");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent184.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent132.png"));

    private boolean firstTurn;

    public BagOfKnives(final AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();

        firstTurn = true;
        this.canGoNegative = false;
    }


    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!(card instanceof AbstractBossCard) && (card.freeToPlayOnce || card.costForTurn <= 0)) {

                this.flashWithoutSound();
                this.playApplyPowerSfx();
                // CardCrawlGame.sound.playA("POWER_TIME_WARP", 0.25F);
                // AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));


            }

            //this.updateDescription();
        }



    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}