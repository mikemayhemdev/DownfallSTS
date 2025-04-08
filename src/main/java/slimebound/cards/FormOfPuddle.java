package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;
import slimebound.patches.AbstractCardEnum;


public class FormOfPuddle extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:FormOfPuddle";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/formofpuddle.png");
    private static final CardStrings cardStrings;

    public FormOfPuddle() {
        super(ID, cardStrings.NAME, IMG_PATH, 2, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        isEthereal = true;
        this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "FormOfPuddle.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
        if (p instanceof SlimeboundCharacter) {
            ((SlimeboundCharacter) p).puddleForm();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    public AbstractCard makeCopy() {
        return new FormOfPuddle();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


