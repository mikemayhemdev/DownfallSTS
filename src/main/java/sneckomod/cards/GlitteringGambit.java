package sneckomod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
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

    public void use(AbstractPlayer p, AbstractMonster m) {
        // No specific action for this card
    }

    @Override
    public void onObtainCard() {
        AbstractDungeon.player.gainGold(magicNumber);
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.RARE);
            if (newCard != null && !cardListDuplicate(cardsToReward, newCard)) {
                AbstractCard cardCopy = newCard.makeCopy();
                cardCopy.upgrade();
                cardsToReward.add(cardCopy);
            }
        }

        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, "Special Bonus Card!");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
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
