package charbosses.cards.hermit;

import charbosses.powers.cardpowers.EnemyBrawlPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.Brawl;
import hermit.cards.HoleUp;
import hermit.characters.hermit;
import hermit.powers.BrawlPower;

public class EnBrawl extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Brawl";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Brawl.ID);

    public EnBrawl() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/hole_up.png", 2, cardStrings.DESCRIPTION, CardType.POWER, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, m, new EnemyBrawlPower(m, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnBrawl();
    }
}
