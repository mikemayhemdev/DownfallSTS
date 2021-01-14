package downfall.powers.neowpowers;

import charbosses.powers.general.EnemyPoisonPower;
import charbosses.powers.general.PoisonProtectionPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;import downfall.downfallMod; import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import downfall.util.TextureLoader;

public class HighlyToxic extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowHighlyToxic");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent384.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowSilent332.png"));

    private boolean firstTurn;

    public HighlyToxic(final AbstractCreature owner, final int amount) {
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


    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 27
            this.flash();// 28
            addToBot(new ApplyPowerAction(AbstractDungeon.player, owner, new EnemyPoisonPower(AbstractDungeon.player, owner, amount), amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}