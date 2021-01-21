package charbosses.cards.crowbot;

import basemod.abstracts.CustomCard;
import charbosses.bosses.Crowbot.CharBossCrowbot;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Boom extends CustomCard {
    public static final String ID = "downfall_Charboss:Boom";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Boom() {
        super(ID, Boom.cardStrings.NAME, "downfallResources/images/cards/crowbot/boom.png", 1, Boom.cardStrings.DESCRIPTION, CardType.ATTACK,CharBossCrowbot.Enums.Crowbot_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
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
        return new Boom();
    }
}
