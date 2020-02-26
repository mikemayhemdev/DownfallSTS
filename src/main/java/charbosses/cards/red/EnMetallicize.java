package charbosses.cards.red;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class EnMetallicize extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Metallicize";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Metallicize");
    }

    public EnMetallicize() {
        super(ID, EnMetallicize.cardStrings.NAME, "red/power/metallicize", 1, EnMetallicize.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(EvilWithinMod.CHARBOSS_SETUP);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new MetallicizePower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 50;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnMetallicize();
    }
}
