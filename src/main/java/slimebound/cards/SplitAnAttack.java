package slimebound.cards;


import basemod.helpers.CardModifierManager;
import collector.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cardmods.EtherealMod;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;


public class SplitAnAttack extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SplitAnAttack";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/split.png");
    private static final CardStrings cardStrings;

    public SplitAnAttack() {
        super(ID, cardStrings.NAME, IMG_PATH, 0, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.UNCOMMON, CardTarget.SELF);
        SlimeboundMod.loadJokeCardImage(this, "split.png");

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], c -> c.type == CardType.ATTACK, (cards) -> {
            AbstractCard c = cards.get(0).makeStatEquivalentCopy();
            c.upgrade();
            if (!c.isEthereal) CardModifierManager.addModifier(c, new EtherealMod());
            Wiz.makeInHand(c);
        }));


    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            selfRetain = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new SplitAnAttack();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


