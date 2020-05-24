package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.FlyingSleeves;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

import java.util.ArrayList;

public class EnFlyingSleeves extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:FlyingSleeves";
    private static final CardStrings cardStrings;

    public EnFlyingSleeves() {
        super(ID, cardStrings.NAME, "purple/attack/flying_sleeves", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 4;
        this.selfRetain = true;

        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new SFXAction("ATTACK_WHIFF_2", 0.3F));
            this.addToBot(new SFXAction("ATTACK_FAST", 0.2F));
            this.addToBot(new VFXAction(new AnimatedSlashEffect(p.hb.cX, p.hb.cY - 30.0F * Settings.scale, 500.0F, 200.0F, 290.0F, 3.0F, Color.VIOLET, Color.PINK)));
        }

        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        if (m != null) {
            this.addToBot(new SFXAction("ATTACK_WHIFF_1", 0.2F));
            this.addToBot(new SFXAction("ATTACK_FAST", 0.2F));
            this.addToBot(new VFXAction(new AnimatedSlashEffect(p.hb.cX, p.hb.cY - 30.0F * Settings.scale, 500.0F, 200.0F, 290.0F, 3.0F, Color.VIOLET, Color.PINK)));
        }

        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    public AbstractCard makeCopy() {
        return new EnFlyingSleeves();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FlyingSleeves");
    }
}
