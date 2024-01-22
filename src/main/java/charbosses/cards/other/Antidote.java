package charbosses.cards.other;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import expansioncontent.cards.AbstractDownfallCard;
import expansioncontent.expansionContentMod;
import theHexaghost.powers.EnhancePower;

import static expansioncontent.cards.AbstractExpansionCard.makeID;

public class Antidote extends AbstractDownfallCard {
    public static final String ID = makeID("Antidote");
    public static final String IMG_PATH = expansionContentMod.makeCardPath("Antidote.png");
    private static final CardStrings cardStrings;

    public Antidote() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.getPower(PoisonPower.POWER_ID).amount <= this.magicNumber){
            atb(new RemoveSpecificPowerAction(p, p, PoisonPower.POWER_ID));
        }else{
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PoisonPower(AbstractDungeon.player, AbstractDungeon.player,-this.magicNumber), -this.magicNumber));
        }

    }

    public void upp () {
        upgradeMagicNumber(3);
//        upgradeBaseCost(0);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



