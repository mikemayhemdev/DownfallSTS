package sneckomod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class DefensiveFlair extends AbstractSneckoCard {

    public final static String ID = makeID("DefensiveFlair");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public DefensiveFlair() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "DefensiveFlair.png");
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
    protected void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q.color != AbstractDungeon.player.getCardColor())
                baseBlock += magicNumber;
        }
        super.applyPowersToBlock();
        this.baseBlock = realBaseBlock;// 75
        this.isBlockModified = block != baseBlock;
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.UNCOMMON);
            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}