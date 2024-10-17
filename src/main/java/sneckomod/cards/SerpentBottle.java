package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import sneckomod.actions.MuddleAction;
import sneckomod.SneckoMod;

public class SerpentBottle extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("SerpentBottle");

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    //snecko adrenaline

    public SerpentBottle() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "SerpentBottle.png");
        exhaust = true;
        tags.add(SneckoMod.SNEKPROOF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DrawCardAction(p, 2));

        addToBot(new SelectCardsInHandAction(magicNumber, "Muddle",
                (AbstractCard c) -> true,
                (cards) -> {
                    for (AbstractCard card : cards) {
                        addToBot(new MuddleAction(card));
                    }
                }
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
