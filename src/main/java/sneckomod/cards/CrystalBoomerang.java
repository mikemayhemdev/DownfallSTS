package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import sneckomod.SneckoMod;

import java.util.function.Consumer;

public class CrystalBoomerang extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("CrystalBoomerang");
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

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
                    "Choose a card to add to your hand.",

                    (selectedCards) -> {

                        AbstractCard selected = selectedCards.get(0);
                        p.discardPile.removeCard(selected);
                        p.hand.addToHand(selected);
                        selected.lighten(false);
                        selected.unhover();
                        selected.applyPowers();

                        if (selected.color != p.getCardColor()) {
                            this.addToTop(new GainBlockAction(p, p, this.block));
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
}
