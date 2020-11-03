package charbosses.cards.blue;

import charbosses.actions.unique.EnemyBarrageAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyFrost;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Blizzard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class EnBlizzard extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Blizzard";
    private static final CardStrings cardStrings;

    public EnBlizzard() {
        super(ID, cardStrings.NAME, "blue/attack/blizzard", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 0;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int frostCount = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof EnemyFrost) {
                ++frostCount;
            }
        }

        this.baseDamage = frostCount * this.magicNumber;
        this.calculateCardDamage((AbstractMonster)null);
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new BlizzardEffect(frostCount, true), 0.25F));
        } else {
            this.addToBot(new VFXAction(new BlizzardEffect(frostCount, true), 1.0F));
        }
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

      }

    public void applyPowers() {
        int frostCount = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof EnemyFrost) {
                ++frostCount;
            }
        }

        if (frostCount > 0) {
            intentMultiAmt = frostCount;
            this.baseDamage = frostCount * this.magicNumber;
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int frostCount = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof EnemyFrost) {
                ++frostCount;
            }
        }

        if (frostCount > 0) {
            intentMultiAmt = frostCount;
            this.baseDamage = frostCount * this.magicNumber;
            super.calculateCardDamage(mo);
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EnBlizzard();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blizzard");
    }
}
