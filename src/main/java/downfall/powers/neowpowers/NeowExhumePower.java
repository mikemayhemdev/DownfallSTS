package downfall.powers.neowpowers;
import charbosses.vfx.EnemyDivinityParticleEffect;
import charbosses.vfx.EnemyStanceAuraEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.actions.NeowExhumeAction;
import downfall.downfallMod;
import downfall.util.TextureLoader;

@Deprecated
public class NeowExhumePower extends AbstractPower {
    public static final String POWER_ID = downfallMod.makeID("NeowExhumePower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    protected float angle; protected float particleTimer = 0.0F; protected float particleTimer2 = 0.0F;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent384.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent332.png"));

    //    private boolean firstTurn;
    public NeowExhumePower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();

    }

    @Override
    public void atStartOfTurn() {
        addToBot(new NeowExhumeAction());
        //addToBot(new DrawCardAction(1));
        flash();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
