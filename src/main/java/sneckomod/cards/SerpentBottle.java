package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class SerpentBottle extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("SerpentBottle");

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int BASE_SILLY = 2;

    //snecko adrenaline

    public SerpentBottle() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseSilly = silly = 2;
        SneckoMod.loadJokeCardImage(this, "SerpentBottle.png");
        exhaust = true;
        //tags.add(SneckoMod.SNEKPROOF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DrawCardAction(p, this.silly));

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
