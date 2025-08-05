package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import sneckomod.SneckoMod;

public class CrystalBoomerang extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("CrystalBoomerang");
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    public static final String[] TEXT;

    public CrystalBoomerang() {
        super(ID, COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "CrystalBoomerang.png");
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.discardPile.isEmpty()) {
            this.addToBot(new SelectCardsCenteredAction(

                    p.discardPile.group,
                    1,
                    TEXT[0],

                    (selectedCards) -> {

                        AbstractCard selecteda = selectedCards.get(0);
                        p.discardPile.removeCard(selecteda);
                        p.hand.addToHand(selecteda);
                        selecteda.lighten(false);
                        selecteda.unhover();
                        selecteda.applyPowers();

                        if (selecteda.color != p.getCardColor()) {
                            blck();
                        }
                    }
            ));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }

}
