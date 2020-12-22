package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import downfall.downfallMod;

public class EnHemokinesis extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Hemokinesis";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hemokinesis");
    }

    public EnHemokinesis() {
        super(ID, cardStrings.NAME, "red/attack/hemokinesis", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 15;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        if (m != null) {
            this.addToBot(new VFXAction(new HemokinesisEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.5F));
        }

        this.addToBot(new LoseHPAction(m, m, this.magicNumber));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHemokinesis();
    }
}
