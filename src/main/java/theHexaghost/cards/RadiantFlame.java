package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.powers.gauntletpowers.MonsterVigor;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import sneckomod.SneckoMod;
import theHexaghost.powers.BurnPower;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.HexalevelPower;

public class RadiantFlame extends AbstractHexaCard {

    public final static String ID = makeID("RadiantFlame");

    public RadiantFlame() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 10;
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "RadiantFlame.png");
    }

//    @Override
//    public void calculateCardDamage(AbstractMonster mo) {
//        int realBaseDamage = this.baseDamage;
//        this.baseDamage += this.magicNumber * HexaMod.soulburn_explosion_this_combat;
//        super.calculateCardDamage(mo);
//        this.baseDamage = realBaseDamage;
//        this.isDamageModified = this.damage != this.baseDamage;
//    }
//
//    @Override
//    public void applyPowers() {
//        int realBaseDamage = this.baseDamage;
//        this.baseDamage += this.magicNumber * HexaMod.soulburn_explosion_this_combat;
//        super.applyPowers();
//        this.baseDamage = realBaseDamage;
//        this.isDamageModified = this.damage != this.baseDamage;
//    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;

        if (AbstractDungeon.getCurrRoom().monsters != null)
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && m.hasPower(BurnPower.POWER_ID)) {
                    this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                    break;
                }
            }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                if(m.hasPower(BurnPower.POWER_ID)){
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnhancePower(magicNumber), magicNumber));
                    addToTop(new RemoveSpecificPowerAction(m, p, BurnPower.POWER_ID));
                }
                this.isDone = true;
            }
        });
//        applyToSelf(new RadiantPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }
}