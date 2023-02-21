package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.ExhumeLickAction;
import slimebound.patches.AbstractCardEnum;


public class DisruptingSlam extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DisruptingSlam";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/violentstop.png");
    private static final CardStrings cardStrings;


    public DisruptingSlam() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.ATTACK, AbstractCardEnum.SLIMEBOUND, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 2;
        this.isMultiDamage = true;
        SlimeboundMod.loadJokeCardImage(this, "DisruptingSlam.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ExhumeLickAction(magicNumber, false));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy() {
        return new DisruptingSlam();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


