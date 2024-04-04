package theHexaghost.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import downfall.patches.EnemyOnExhaustPatch;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;

public class SoulburnOnExhaustPower extends AbstractPower implements EnemyOnExhaustPatch.EnemyOnExhaustPower {
    public static final String POWER_ID = HexaMod.makeID("SoulburnOnExhaustPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/GhostFlameBarrier84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/GhostFlameBarrier32.png");

    public SoulburnOnExhaustPower(AbstractCreature m, int amount) {

        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();

    }

    @Override
    public void enemyOnExhaust(AbstractCard c) {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToBot(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, owner.hb.cX, owner.hb.cY), 0.3F));
        this.addToBot(new ApplyPowerAction(owner, AbstractDungeon.player, new BurnPower(owner, this.amount), this.amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfRound() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, SoulburnOnExhaustPower.POWER_ID));
        super.atEndOfRound();
    }

    public AbstractPower makeCopy() {
        return new SoulburnOnExhaustPower(owner,amount);

    }



}
