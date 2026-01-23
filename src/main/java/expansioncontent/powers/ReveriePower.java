package expansioncontent.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import hermit.util.Wiz;
import sneckomod.SneckoMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class ReveriePower extends AbstractPower {
    public static final String POWER_ID = "expansioncontent:ReveriePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReveriePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.updateDescription();
        this.loadRegion("time");
        priority = 999;
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            if (Wiz.hand().size() < BaseMod.MAX_HAND_SIZE) {
                this.flash();
                if (!AbstractDungeon.player.discardPile.isEmpty()) {
                    ArrayList<AbstractCard> cardsToChooseFrom = new ArrayList<>();

                    for (int i = 0; i < this.amount && i < AbstractDungeon.player.discardPile.size(); ++i) {
                        cardsToChooseFrom.add(AbstractDungeon.player.discardPile.group.get(i));
                    }

                    if (!cardsToChooseFrom.isEmpty()) {
                        this.addToBot(new SelectCardsCenteredAction(
                                cardsToChooseFrom,
                                1,
                                DESCRIPTIONS[3],
                                (selectedCards) -> {
                                    AbstractCard chosenCard = selectedCards.get(0);
                                    AbstractDungeon.player.discardPile.removeCard(chosenCard);
                                    AbstractDungeon.player.hand.addToHand(chosenCard);

                                    chosenCard.lighten(false);
                                    chosenCard.unhover();
                                    chosenCard.applyPowers();
                                }
                        ));
                    }
                }
            }
        }
    }
}
