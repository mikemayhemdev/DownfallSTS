package downfall.powers.neowpowers;

import basemod.helpers.CardModifierManager;
import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import basemod.cardmods.EtherealMod;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import hermit.cards.ImpendingDoom;

public class Eclipse extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowEclipse");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowHermit384.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowHermit332.png"));

    public Eclipse(final AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();

        this.canGoNegative = false;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        AbstractCard q = new ImpendingDoom();
        CardModifierManager.addModifier(q, new EtherealMod());
        addToBot(new MakeTempCardInHandAction(q));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}