package charbosses.cards.colorless;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.ArrayList;

public class EnApparition extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Ghostly";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghostly");
    }

    public EnApparition() {
        super(ID, EnApparition.cardStrings.NAME, "colorless/skill/apparition", 1, EnApparition.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new IntangiblePlayerPower(m, 1), 1));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 25;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = EnApparition.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.isEthereal = false;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnApparition();
    }
}
