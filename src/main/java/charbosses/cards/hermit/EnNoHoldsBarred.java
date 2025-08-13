package charbosses.cards.hermit;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import hermit.actions.GoldenBulletAction;
import hermit.cards.GoldenBullet;
import hermit.cards.NoHoldsBarred;
import hermit.characters.hermit;
import hermit.powers.Bruise;

public class EnNoHoldsBarred extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:NoHoldsBarred";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(NoHoldsBarred.ID);

    public EnNoHoldsBarred(int damage) {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/golden_bullet.png", 2, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        baseDamage=19;
        magicNumber = baseMagicNumber = 5;
    }

    public EnNoHoldsBarred() {
        this(10);
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        this.addToBot(new VFXAction(new ViolentAttackEffect(p.hb.cX, p.hb.cY, Color.YELLOW)));
        addToBot(new DamageAction(m, new DamageInfo(m, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        this.addToBot(new ApplyPowerAction(p, m, new Bruise(p, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnNoHoldsBarred();
    }
}
