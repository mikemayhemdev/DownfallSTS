package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.powers.cardpowers.EnemyInfiniteBladesPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.InfiniteBladesPower;

import java.util.ArrayList;

public class EnInfiniteBlades extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Infinite Blades";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Infinite Blades");
    }

    public EnInfiniteBlades() {
        super(ID, EnInfiniteBlades.cardStrings.NAME, "green/power/infinite_blades", 1, EnInfiniteBlades.cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.cardsToPreview = new EnShiv();
        this.limit = 2;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 15;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyInfiniteBladesPower(m, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = EnInfiniteBlades.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public int getValue() {
        return (new EnShiv()).getValue() * 3 + 3;
    }

    @Override
    public int getUpgradeValue() {
        return (new EnShiv()).getValue() * 2;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnInfiniteBlades();
    }
}
