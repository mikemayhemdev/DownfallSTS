package charbosses.cards.hermit;

import charbosses.powers.cardpowers.EnemyBigShotPower;
import charbosses.powers.cardpowers.EnemyShadowCloakPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.cards.BigShot;
import hermit.cards.ShadowCloak;
import hermit.characters.hermit;
import hermit.powers.BigShotPower;

public class EnSmokingBarrel extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:SmokingBarrel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(BigShot.ID);

    public EnSmokingBarrel() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/smoking_barrel.png", 1, cardStrings.DESCRIPTION, CardType.POWER, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 3;
        tags.add(downfallMod.CHARBOSS_DEADON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, m, new EnemyBigShotPower(m, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSmokingBarrel();
    }
}
