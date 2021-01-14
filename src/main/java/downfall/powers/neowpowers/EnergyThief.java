package downfall.powers.neowpowers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;import downfall.downfallMod; import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import downfall.util.TextureLoader;

public class EnergyThief extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowEnergyThief");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowDefect184.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowDefect132.png"));
    private boolean firstTurn;

    public EnergyThief(final AbstractCreature owner) {
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
    public void onSpecificTrigger() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard().makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.5F, (float) Settings.HEIGHT / 2.0F));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}