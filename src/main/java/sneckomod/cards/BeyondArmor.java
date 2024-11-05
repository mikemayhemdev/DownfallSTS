package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import hermit.util.Wiz;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static sneckomod.SneckoMod.makeID;

public class BeyondArmor extends AbstractSneckoCard {

    public final static String ID = makeID("BeyondArmor");

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 2;

    public BeyondArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "BeyondArmor.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p, p, this.block));

        int count = 0;

        // fully loaded code
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        tmp.group.addAll(AbstractDungeon.player.drawPile.group.stream()
                .filter(c -> c.color != this.color)
                .limit(magicNumber)
                .collect(Collectors.toList()));

        for (AbstractCard c : tmp.group) {
            if (Wiz.hand().size() < BaseMod.MAX_HAND_SIZE) {
                addToBot(new FetchAction(Wiz.p().drawPile, card -> card == c));
                count++;
                if (count >= magicNumber) break;
            }
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.COMMON);
            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy()); // Use makeCopy() to ensure a new instance
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
            upgradeBlock(UPG_BLOCK);
        }
    }
}
