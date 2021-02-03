package downfall.powers.neowpowers;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import downfall.downfallMod;
import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import downfall.util.TextureLoader;

public class BagOfKnives extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowBagOfKnives");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent184.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent132.png"));

    private boolean firstTurn;

    public BagOfKnives(final AbstractCreature owner) {
        ID = POWER_ID;
        this.owner = owner;
        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        name = NAME;

        updateDescription();

        firstTurn = true;
        canGoNegative = false;
    }

    //This is used instead of onAfterUseCard so that cards like Streamline, when used at 1-cost, will not trigger this effect
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (amount > 0) {
            if (!(card instanceof AbstractBossCard) && (card.freeToPlayOnce || card.costForTurn <= 0)) {

                flashWithoutSound();
                amount--;
                // CardCrawlGame.sound.playA("POWER_TIME_WARP", 0.25F);
                // AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
                addToBot(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(owner, 4, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));


            }

            //updateDescription();
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        amount = 2;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}