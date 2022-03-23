package downfall.powers.neowpowers;

import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import expansioncontent.powers.DeEnergizedPower;

public class Distracting extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowDistracting");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowHermit184.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowHermit132.png"));

    public Distracting(final AbstractCreature owner, final int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();

        this.canGoNegative = false;
    }

    private boolean appliedThisTurn = false;

    @Override
    public void atStartOfTurn() {
        appliedThisTurn = false;
    }

    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS && !appliedThisTurn) {
            this.addToBot(new ApplyPowerAction(target, owner, new DeEnergizedPower(1), 1));
            appliedThisTurn = true;
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}