package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class EnBeamCell extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:BeamCell";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(BeamCell.ID);
    }

    public EnBeamCell() {
        super(ID, cardStrings.NAME, "blue/attack/beam_cell", 0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 3;
        artifactConsumedIfPlayed = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));// 44
        this.addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, this.magicNumber + 1, true), this.magicNumber + 1, true, AbstractGameAction.AttackEffect.NONE));// 38
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }// 51

    public AbstractCard makeCopy() {
        return new EnBeamCell();
    }
}