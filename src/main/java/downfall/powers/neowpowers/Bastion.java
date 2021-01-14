package downfall.powers.neowpowers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import downfall.downfallMod;
import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import downfall.util.TextureLoader;

public class Bastion extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowBastion");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowIronclad384.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowIronclad332.png"));

    private boolean firstTurn;

    public Bastion(final AbstractCreature owner, final int amount) {
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
        addToBot(new ApplyPowerAction(this.owner, this.owner, new BarricadePower(this.owner)));
    }


    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        addToBot(new com.megacrit.cardcrawl.actions.common.GainBlockAction(this.owner, this.owner, this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}