package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.unique.DoublePoisonAction;
import com.megacrit.cardcrawl.actions.unique.TriplePoisonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Catalyst;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class EnCatalyst extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Catalyst";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Catalyst.ID);
    }

    public EnCatalyst() {
        super(ID, EnCatalyst.cardStrings.NAME, "green/skill/catalyst", 1, EnCatalyst.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.STRONG_DEBUFF);
        exhaust = true;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (AbstractDungeon.player != null){
            if (AbstractDungeon.player.hasPower(PoisonPower.POWER_ID)){
                return 10;
            }
        }
        return -10;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.upgraded) {// 32
            this.addToBot(new DoublePoisonAction(p, m));// 33
        } else {
            this.addToBot(new TriplePoisonAction(p, m));// 35
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            rawDescription = EnCatalyst.cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCatalyst();
    }
}
