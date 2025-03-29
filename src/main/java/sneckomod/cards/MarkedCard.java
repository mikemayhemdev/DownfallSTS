package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleMarkedAction;

import static sneckomod.cards.SnekBite.muddle_name;

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
        addToBot(new SelectCardsInHandAction(magicNumber, muddle_name,
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
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}