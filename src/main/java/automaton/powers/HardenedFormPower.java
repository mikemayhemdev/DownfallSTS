package automaton.powers;

import automaton.cards.FunctionCard;
import automaton.vfx.FloatingBronzeOrbEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import basemod.BaseMod;

import java.util.ArrayList;
import java.util.List;

public class HardenedFormPower extends AbstractAutomatonPower {
    public static final String NAME = "HardenedForm";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;
    private FloatingBronzeOrbEffect orbVFX;

    public HardenedFormPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard function) {
        if (function instanceof FunctionCard) {
            onSpecificTrigger();
        }
    }

    @Override
    public void onSpecificTrigger() {
        flash();


        CardGroup combinedPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        combinedPile.group.addAll(AbstractDungeon.player.drawPile.group);
        combinedPile.group.addAll(AbstractDungeon.player.discardPile.group);

        List<AbstractCard> rareCards = new ArrayList<>();
        List<AbstractCard> uncommonCards = new ArrayList<>();
        List<AbstractCard> commonCards = new ArrayList<>();
        List<AbstractCard> basicCards = new ArrayList<>();
        List<AbstractCard> specialCards = new ArrayList<>();
        List<AbstractCard> statusCards = new ArrayList<>();
        List<AbstractCard> curseCards = new ArrayList<>();

        for (AbstractCard card : new ArrayList<>(combinedPile.group)) {
            if (card.type == CardType.STATUS) {
                statusCards.add(card);
                combinedPile.removeCard(card);
            } else if (card.rarity == CardRarity.CURSE) {
                curseCards.add(card);
                combinedPile.removeCard(card);
            } else if (card.rarity == CardRarity.RARE) {
                rareCards.add(card);
                combinedPile.removeCard(card);
            } else if (card.rarity == CardRarity.UNCOMMON) {
                uncommonCards.add(card);
                combinedPile.removeCard(card);
            } else if (card.rarity == CardRarity.COMMON) {
                commonCards.add(card);
                combinedPile.removeCard(card);
            } else if (card.rarity == CardRarity.BASIC) {
                basicCards.add(card);
                combinedPile.removeCard(card);
            } else if (card.rarity == CardRarity.SPECIAL) {
                specialCards.add(card);
                combinedPile.removeCard(card);
            }
        }

        //RARE -> UNCOMMON -> COMMON -> BASIC -> SPECIAL -> STATUS -> CURSE
        List<AbstractCard> orderedCards = new ArrayList<>();
        orderedCards.addAll(rareCards);     // RARE cards first
        orderedCards.addAll(uncommonCards); // UNCOMMON cards second
        orderedCards.addAll(commonCards);   // COMMON cards third
        orderedCards.addAll(basicCards);    // BASIC cards fourth
        orderedCards.addAll(specialCards);  // SPECIAL cards fifth
        orderedCards.addAll(statusCards);   // STATUS cards sixth
        orderedCards.addAll(curseCards);    // CURSE cards last

        List<AbstractCard> selectedCards = new ArrayList<>();
        int remainingCards = amount;

        for (AbstractCard card : orderedCards) {
            if (remainingCards > 0) {
                selectedCards.add(card);
                remainingCards--;
            } else {
                break;
            }
        }

        for (AbstractCard card : selectedCards) {
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 1));

                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    if (AbstractDungeon.player.drawPile.contains(card)) {
                        AbstractDungeon.player.drawPile.removeCard(card);
                    } else if (AbstractDungeon.player.discardPile.contains(card)) {
                        AbstractDungeon.player.discardPile.removeCard(card);
                    }
                }

            }
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        orbVFX = new FloatingBronzeOrbEffect(AbstractDungeon.player);
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.effectList.add(orbVFX);
                isDone = true;
            }
        });
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (orbVFX != null) orbVFX.dispose();
    }

    @Override
    public void onDeath() {
        super.onDeath();
        if (orbVFX != null) orbVFX.dispose();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
