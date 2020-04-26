package evilWithin.powers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.colorless.EnShiv;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import evilWithin.EvilWithinMod;
import evilWithin.monsters.NeowBoss;
import theHexaghost.util.TextureLoader;

public class NeowInvulnerablePower extends AbstractPower {
    public static final String POWER_ID = "EvilWithin:NeowInvulnerable";

    private static final Texture tex84 = TextureLoader.getTexture(EvilWithinMod.assetPath("images/powers/NeowRez84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(EvilWithinMod.assetPath("images/powers/NeowRez32.png"));


    public NeowInvulnerablePower(final AbstractCreature owner, final int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        this.updateDescription();

        this.canGoNegative = false;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        int currentHP = this.owner.currentHealth;

        if (currentHP == 1 || damageAmount > currentHP) {
            damageAmount = currentHP - 1;
            this.flash();
            if (AbstractCharBoss.boss == null) {
                NeowBoss nb = (NeowBoss) this.owner;
                nb.switchToRez();
            }
        }

        return damageAmount;
    }



    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}