package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.cards.LastStand;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import downfall.util.TextureLoader;
import slimebound.SlimeboundMod;

public class LastStandPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("LastStandPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/LastStandMod84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/LastStandMod32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LastStandPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        this.Check(damageAmount);
    }

    public void onInitialApplication() {
            this.Check(0);
    }

    public void Check(int damage) {
        SlimeboundMod.logger.info("Last Stand Check.");
        if (owner.currentHealth - damage < owner.maxHealth / 2) {
            flash();
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            addToTop(new SFXAction("MONSTER_CHAMP_CHARGE"));
            LastStand c = new LastStand();
            SlimeboundMod.logger.info(c.getLimitBreak());
            addToTop(new ShoutAction(owner, c.getLimitBreak(), 2.0F, 3.0F));
            addToTop(new VFXAction(owner, new InflameEffect(owner), 0.25F));
            addToTop(new VFXAction(owner, new InflameEffect(owner), 0.25F));
            addToTop(new VFXAction(owner, new InflameEffect(owner), 0.25F));
            addToTop(new RemoveDebuffsAction(owner));
            addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LastStandPower(amount);
    }
}