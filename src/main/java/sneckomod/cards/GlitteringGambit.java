package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class GlitteringGambit extends AbstractSneckoCard {
    public final static String ID = makeID("GlitteringGambit");

    private static final int MAGIC = 75;

    public GlitteringGambit() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = false;
        tags.add(CardTags.HEALING);
        tags.add(expansionContentMod.UNPLAYABLE);
        SneckoMod.loadJokeCardImage(this, "GlitteringGambit.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onObtainCard() {
        this.addToBot(new GainGoldAction(this.magicNumber));
        displayCardRewards(AbstractCard.CardRarity.RARE, "Special Bonus Card!");
    }

    private void displayCardRewards(AbstractCard.CardRarity rarity, String rewardText) {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();

        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == rarity);
            if (newCard != null && !cardListDuplicate(cardsToReward, newCard)) {
                AbstractCard cardCopy = newCard.makeCopy();
                cardCopy.upgrade();  // Upgrade each card copy
                cardsToReward.add(cardCopy);
            }
        }

        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, rewardText);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard existingCard : cardsList) {
            if (existingCard.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
