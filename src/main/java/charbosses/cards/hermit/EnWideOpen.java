package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hermit.cards.HoleUp;
import hermit.cards.Strike_Hermit;
import hermit.cards.WideOpen;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnWideOpen extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:WideOpen";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(WideOpen.ID);

    public EnWideOpen() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/wide_open.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 6;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN3));
        addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, magicNumber, true), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnWideOpen();
    }
}
