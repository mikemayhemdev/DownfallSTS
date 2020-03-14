package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.ThroughViolence;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

import java.util.ArrayList;

public class EnThroughViolence extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:ThroughViolence";
    private static final CardStrings cardStrings;

    public EnThroughViolence() {
        super(ID, cardStrings.NAME, "colorless/attack/through_violence", 0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 20;
        this.selfRetain = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(new ViolentAttackEffect(p.hb.cX, p.hb.cY, Color.VIOLET)));
            } else {
                this.addToBot(new VFXAction(new ViolentAttackEffect(p.hb.cX, p.hb.cY, Color.VIOLET), 0.4F));
            }
        }

        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(10);
        }

    }

    public AbstractCard makeCopy() {
        return new EnThroughViolence();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ThroughViolence");
    }
}

