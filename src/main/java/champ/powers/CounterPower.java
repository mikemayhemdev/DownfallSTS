package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.cards.Parry;
import champ.cards.Riposte;
import champ.relics.PowerArmor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;

public class CounterPower extends AbstractPower implements CloneablePowerInterface, OnLoseBlockPower {

    public static final String POWER_ID = ChampMod.makeID("CounterPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Counterstrike84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Counterstrike32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    private int blockedDamage = 0;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CounterPower(int amount) {
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

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        blockedDamage += Math.min(AbstractDungeon.player.currentBlock, i);
        return i;
    }

    @Override
    public void atStartOfTurn() {
        if (blockedDamage > 0){
            flash();
            AbstractCard c = new Riposte();
            c.baseDamage = blockedDamage;
            addToBot(new MakeTempCardInHandAction(c));
            if (owner.hasPower(ParryPower.POWER_ID)){
                c.freeToPlay();
                owner.getPower(ParryPower.POWER_ID).onSpecificTrigger();
            }
        }
        addToBot(new ReducePowerAction(owner,owner,this,1));
        blockedDamage = 0;
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