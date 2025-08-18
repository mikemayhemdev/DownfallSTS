package charbosses.cards.other;

import charbosses.powers.general.EnemyPoisonPower;
import charbosses.powers.general.PoisonProtectionPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cards.AbstractDownfallCard;
import expansioncontent.expansionContentMod;
import hermit.util.Wiz;

import static expansioncontent.cards.AbstractExpansionCard.makeID;

public class AntidoteAsPower extends AbstractDownfallCard {
    public static final String ID = makeID("AntidoteAsPower");
    public static final String IMG_PATH = expansionContentMod.makeCardPath("Antidote.png");
    private static final CardStrings cardStrings;

    public AntidoteAsPower() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new PoisonProtectionPower(p, magicNumber));

    }

    public void upp () {
        upgradeMagicNumber(1);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


