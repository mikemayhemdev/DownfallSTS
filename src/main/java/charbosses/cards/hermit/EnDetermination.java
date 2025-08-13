package charbosses.cards.hermit;

import charbosses.powers.cardpowers.EnemyShadowCloakPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Determination;
import hermit.cards.ShadowCloak;
import hermit.characters.hermit;
import hermit.powers.DeterminationPower;

public class EnDetermination extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Determination";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Determination.ID);

    public EnDetermination() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/determination.png", 1, cardStrings.DESCRIPTION, CardType.POWER, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, m, new DeterminationPower(m, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDetermination();
    }
}
