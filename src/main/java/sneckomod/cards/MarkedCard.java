package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.MuddleHandAction;
import sneckomod.actions.MuddleMarkedAction;

public class MarkedCard extends AbstractSneckoCard {

    public final static String ID = makeID("MarkedCard");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 1;

    public MarkedCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "MarkedCard.png");
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber, "Muddle",
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
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}