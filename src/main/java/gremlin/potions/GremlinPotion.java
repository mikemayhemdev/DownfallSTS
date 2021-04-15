package gremlin.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import gremlin.orbs.MadGremlin;
import gremlin.orbs.ShieldGremlin;
import gremlin.orbs.SneakyGremlin;
import gremlin.powers.ModifiedLoseStrengthPower;
import gremlin.powers.WizPower;

public class GremlinPotion extends AbstractPotion {
    public static final String POTION_ID = "gremlin:GremlinPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public GremlinPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionEffect.RAINBOW, Color.WHITE, (Color)null, (Color)null);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        this.potency = getPotency();
        AbstractPlayer p = AbstractDungeon.player;
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                        new WeakPower(mo, this.potency, false), this.potency));
            }
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new StrengthPower(p, MadGremlin.STRENGTH * this.potency)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new ModifiedLoseStrengthPower(p, MadGremlin.STRENGTH * this.potency)));

        for (int i = 0; i < this.potency; i++) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, ShieldGremlin.BLOCK));
        }

        for (int i = 0; i < this.potency; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, SneakyGremlin.DAMAGE, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new WizPower(p, this.potency), this.potency));
    }

    public AbstractPotion makeCopy() {
        return new GremlinPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}

