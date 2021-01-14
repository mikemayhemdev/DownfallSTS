package downfall.powers.neowpowers;

import charbosses.cards.status.EnBurn;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;import downfall.downfallMod; import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

public class Syncronize extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowSyncronize");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowIronclad184.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowIronclad132.png"));

    private boolean firstTurn;

    public Syncronize(final AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;

        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();

        firstTurn = true;
        this.canGoNegative = false;
    }

    @Override
    public void atStartOfTurn() {
        this.addToBot(new MakeTempCardInDiscardAction(SneckoMod.getRandomStatus().makeStatEquivalentCopy(), 1));

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}