package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleMarkedAction;

public class MarkedCard extends AbstractSneckoCard {

    public final static String ID = makeID("MarkedCard");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 1;

    public MarkedCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "MarkedCard.png");
        baseMagicNumber = magicNumber = MAGIC;
        this.selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber, BaseMod.getKeywordProper("sneckomod:muddle"),
                (AbstractCard c) -> true,
                (cards) -> {
                    for (AbstractCard card : cards) {
                        addToBot(new MuddleMarkedAction(card));
                    }
                }
        ));
    }

    //   public void upgradeAction(AbstractPlayer p, AbstractMonster m){
    //       AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    //   }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
           // upgradeMagicNumber(1);
                        rawDescription = UPGRADE_DESCRIPTION;
            this.exhaust = false;
            initializeDescription();
        }
    }
}