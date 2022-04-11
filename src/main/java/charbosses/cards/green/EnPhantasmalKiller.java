package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyNoxiousFumesPower;
import charbosses.powers.cardpowers.EnemyPhantasmalPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.NoxiousFumes;
import com.megacrit.cardcrawl.cards.green.PhantasmalKiller;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnPhantasmalKiller extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Phantasmal Killer";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(PhantasmalKiller.ID);
    }

    public EnPhantasmalKiller() {
        super(ID, EnPhantasmalKiller.cardStrings.NAME, "green/power/phantasmal_killer", 1, EnPhantasmalKiller.cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyPhantasmalPower(m, magicNumber), magicNumber));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 15;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnPhantasmalKiller();
    }
}
