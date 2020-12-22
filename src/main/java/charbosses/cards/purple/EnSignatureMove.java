package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class EnSignatureMove extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:SignatureMove";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SignatureMove");
    }

    public EnSignatureMove() {
        super(ID, cardStrings.NAME, "purple/attack/signature_move", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 30;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (p != null) {
            this.addToBot(new VFXAction(new ClashEffect(p.hb.cX, p.hb.cY), 0.1F));
        }
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(10);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSignatureMove();
    }
}
