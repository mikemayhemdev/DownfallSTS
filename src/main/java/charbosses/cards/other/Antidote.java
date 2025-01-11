package charbosses.cards.other;

import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import expansioncontent.cards.AbstractDownfallCard;
import expansioncontent.expansionContentMod;

import static expansioncontent.cards.AbstractExpansionCard.makeID;

public class Antidote extends AbstractDownfallCard {
    public static final String ID = makeID("Antidote");
    public static final String IMG_PATH = expansionContentMod.makeCardPath("Antidote.png");
    private static final CardStrings cardStrings;

    public Antidote() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 15;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.hasPower(EnemyPoisonPower.POWER_ID) && AbstractDungeon.player.getPower(EnemyPoisonPower.POWER_ID).amount <= this.magicNumber){
            atb(new RemoveSpecificPowerAction(p, p, EnemyPoisonPower.POWER_ID));
        }else{
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, EnemyPoisonPower.POWER_ID, this.magicNumber));
        }

    }

    public void upp () {
        //upgradeMagicNumber(3);
        upgradeBaseCost(0);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


