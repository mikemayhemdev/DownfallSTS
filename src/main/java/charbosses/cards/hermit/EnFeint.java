package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.Feint;
import hermit.cards.HoleUp;
import hermit.characters.hermit;
import hermit.powers.Bruise;

public class EnFeint extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Feint";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Feint.ID);

    public EnFeint() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/feint.png", 0, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_DEBUFF);

        baseMagicNumber = magicNumber = 2;
        baseBlock = block = 3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, m, new Bruise(p, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));


        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, m, block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            upgradeBlock(2);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnFeint();
    }
}
