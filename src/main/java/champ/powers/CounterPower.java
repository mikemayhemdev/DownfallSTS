package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.cards.Riposte;
import champ.cards.SetATrap;
import champ.relics.PowerArmor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;

public class CounterPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("CounterPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Counterstrike84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Counterstrike32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CounterPower(final int amount) {
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

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            if (owner.hasPower(FalseCounterPower.POWER_ID)) {
                owner.getPower(FalseCounterPower.POWER_ID).onSpecificTrigger();
                addToTop(new ReducePowerAction(owner, owner, this, amount / 2));
                if (AbstractDungeon.player.hasPower(ParryPower.POWER_ID)){
                    for (int i = 0; i < AbstractDungeon.player.getPower(ParryPower.POWER_ID).amount; i++) {
                        AbstractCard c = new Riposte();
                        c.baseDamage = amount / 2;
                        addToBot(new MakeTempCardInHandAction(c));
                    }
                    AbstractDungeon.player.getPower(ParryPower.POWER_ID).onSpecificTrigger();
                }
            } else {
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
                if (AbstractDungeon.player.hasPower(ParryPower.POWER_ID)) {
                    for (int i = 0; i < AbstractDungeon.player.getPower(ParryPower.POWER_ID).amount; i++) {
                        AbstractCard c = new Riposte();
                        c.baseDamage = amount;
                        addToBot(new MakeTempCardInHandAction(c));
                    }
                    AbstractDungeon.player.getPower(ParryPower.POWER_ID).onSpecificTrigger();
                }
            }
            this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }

        return damageAmount;
    }

    @Override
    public void stackPower(int stackAmount) {
        if (AbstractDungeon.player.hasRelic(PowerArmor.ID))
            if (amount + stackAmount > PowerArmor.CAP_RESOLVE_ETC)
                stackAmount = (PowerArmor.CAP_RESOLVE_ETC - amount);
        super.stackPower(stackAmount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CounterPower(amount);
    }
}